package id.anantyan.moviesapp.ui.main.profile

import android.app.Activity.RESULT_OK
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.size.ViewSizeResolver
import coil.transform.RoundedCornersTransformation
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.anantyan.moviesapp.R
import id.anantyan.moviesapp.databinding.FragmentProfileBinding
import id.anantyan.moviesapp.ui.dialog.dialog
import id.anantyan.utils.Constant.PERMISSION_CAMERA_AND_WRITE_EXTERNAL
import id.anantyan.utils.Resource
import id.anantyan.utils.convertBitmapToFile
import id.anantyan.utils.dividerVertical
import id.anantyan.utils.permission.EasyPermissions
import id.anantyan.utils.permission.EasyPermissions.somePermissionPermanentlyDenied
import id.anantyan.utils.permission.dialogs.SettingsDialog
import id.anantyan.utils.requestCameraAndWriteExternalPermission
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    /*private var _bitmap: Bitmap? = null*/
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()
    @Inject lateinit var adapterProfile: ProfileAdapterHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBindView()
        onBindObserver()
    }

    private fun onBindView() {
        binding.rvProfile.setHasFixedSize(true)
        binding.rvProfile.layoutManager = LinearLayoutManager(requireContext())
        binding.rvProfile.itemAnimator = DefaultItemAnimator()
        binding.rvProfile.addItemDecoration(dividerVertical(requireContext(), 32, 32))
        binding.rvProfile.isNestedScrollingEnabled = false
        binding.btnSetProfile.setOnClickListener {
            viewModel.getAccount()
        }
        binding.fabSetPassword.setOnClickListener {
            requireContext().dialog().dialogSetPassword { item, dialog ->
                item.password?.let {
                    viewModel.setPassword(it)
                }
                dialog.dismiss()
            }
        }
        binding.imgView.setOnClickListener {
            requestCameraAndWriteExternalPermission(this)
        }
    }

    private fun onBindObserver() {
        viewModel.getAccount.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    requireContext().dialog().dialogSetProfile(it.data!!) { item, dialog ->
                        viewModel.setProfile(item)
                        dialog.dismiss()
                    }
                }
                is Resource.Error -> {
                    onSnackbar("${it.message}")
                }
                else -> {}
            }
        }
        viewModel.setPassword.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    onToast("${it.data}")
                }
                is Resource.Error -> {
                    onSnackbar("${it.message}")
                }
                else -> {}
            }
        }
        viewModel.setProfile.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    adapterProfile.differ(it.data!!)
                    binding.rvProfile.adapter = adapterProfile.init()
                }
                is Resource.Error -> {
                    onSnackbar("${it.message}")
                }
                else -> {}
            }
        }
        viewModel.setPhoto.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_LONG).show()
                    viewModel.showPhoto()
                }
                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_LONG).show()
                }
                is Resource.Error -> {
                    onSnackbar("${it.message}")
                }
            }
        }
        viewModel.showAccount.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    adapterProfile.differ(it.data!!)
                    binding.rvProfile.adapter = adapterProfile.init()
                }
                is Resource.Error -> {
                    onSnackbar("${it.message}")
                }
                else -> {}
            }
        }
        viewModel.showPhoto.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    it.data?.let { urlImg ->
                        binding.imgView.load(urlImg) {
                            crossfade(true)
                            placeholder(R.drawable.ic_baseline_person_outline_24)
                            error(R.drawable.ic_baseline_person_outline_24)
                            transformations(RoundedCornersTransformation(16F))
                            size(ViewSizeResolver(binding.imgView))
                        }
                    }
                }
                is Resource.Error -> {
                    onSnackbar("${it.message}")
                }
                else -> {}
            }
        }
        viewModel.showAccount()
        viewModel.showPhoto()
    }

    private fun onToast(message: String) {
        val toast = Toast.makeText(requireContext(), message, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    private fun onSnackbar(message: String) {
        val snackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.error))
        snackbar.show()
    }

    private fun choosePhotoPermission() {
        ImagePicker.with(this)
            .galleryMimeTypes(
                mimeTypes = arrayOf(
                    "image/png",
                    "image/jpg",
                    "image/jpeg"
                )
            )
            .cropSquare()
            .compress(2048)
            .createIntent {
                choosePhoto.launch(it)
            }
    }

    private val choosePhoto = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val resultCode = it.resultCode
        val data = it.data
        try {
            when (resultCode) {
                RESULT_OK -> {
                    data?.let { intent ->
                        binding.imgView.setImageURI(intent.data)
                        val drawable = binding.imgView.drawable as BitmapDrawable
                        val bitmap = drawable.bitmap
                        viewModel.setPhoto(requireContext().buildImageBodyPart("image", bitmap))
                    }
                }
                ImagePicker.RESULT_ERROR -> {
                    throw Exception(ImagePicker.getError(data))
                }
            }
        } catch (ex: Exception) {
            Toast.makeText(
                requireContext(),
                ex.message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        if (requestCode == PERMISSION_CAMERA_AND_WRITE_EXTERNAL) {
            choosePhotoPermission()
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (somePermissionPermanentlyDenied(this, listOf(perms[0]))) {
            SettingsDialog.Builder(requireContext()).build().show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}

private fun Context.buildImageBodyPart(fileName: String, bitmap: Bitmap): MultipartBody.Part {
    val leftImageFile = this.convertBitmapToFile(fileName, bitmap)
    val reqFile = leftImageFile.asRequestBody("image/*".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(fileName, leftImageFile.name, reqFile)
}