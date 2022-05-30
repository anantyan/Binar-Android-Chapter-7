package id.anantyan.moviesapp.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.anantyan.moviesapp.R
import id.anantyan.moviesapp.databinding.FragmentLoginBinding
import id.anantyan.moviesapp.data.local.model.UsersLocal
import id.anantyan.utils.Resource
import id.anantyan.utils.emailValid
import id.anantyan.utils.passwordValid
import id.anantyan.utils.sharedPreferences.DataStoreManager
import id.anantyan.utils.validator.Validator
import id.anantyan.utils.validator.constant.Mode
import id.anantyan.utils.validator.validator
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    @Inject lateinit var store: DataStoreManager
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBindView()
        onBindObserver(view)
    }

    private fun onBindView() {
        binding.btnSignIn.setOnClickListener {
            onValidation()
        }
        binding.txtSignUp.setOnClickListener {
            val destination = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            it.findNavController().navigate(destination)
        }
    }

    private fun onBindObserver(view: View) {
        viewModel.login.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    store.setLogIn(true)
                    store.setUserId(it.data?.userId!!)
                    val destination = LoginFragmentDirections.actionLoginFragmentToMainActivity()
                    view.findNavController().navigate(destination)
                    requireActivity().finish()
                }
                is Resource.Error -> {
                    onSnackbar("${it.message}")
                }
                else -> {}
            }
        }
    }

    private fun onValidation() {
        validator(requireContext()) {
            mode = Mode.SINGLE
            listener = onSignIn
            validate(
                emailValid(binding.txtLayoutEmail),
                passwordValid(binding.txtLayoutPassword)
            )
        }
    }

    private fun onSnackbar(message: String) {
        val snackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.error))
        snackbar.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val onSignIn = object : Validator.OnValidateListener {
        override fun onValidateSuccess(values: List<String>) {
            val email = binding.txtInputEmail.text.toString()
            val password = binding.txtInputPassword.text.toString()
            viewModel.login(email, password)
        }

        override fun onValidateFailed(errors: List<String>) {}
    }
}