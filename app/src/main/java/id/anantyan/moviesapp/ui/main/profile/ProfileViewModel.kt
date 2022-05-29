package id.anantyan.moviesapp.ui.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.anantyan.moviesapp.R
import id.anantyan.moviesapp.data.local.model.ProfileLocal
import id.anantyan.moviesapp.data.local.model.UsersLocal
import id.anantyan.moviesapp.repository.UsersLocalRepository
import id.anantyan.moviesapp.repository.UsersRemoteRepository
import id.anantyan.utils.LiveEvent
import id.anantyan.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val localRepository: UsersLocalRepository,
    private val remoteRepository: UsersRemoteRepository
) : ViewModel() {
    private val _showAccount: MutableLiveData<Resource<List<ProfileLocal>>> = MutableLiveData()
    private val _showPhoto: MutableLiveData<Resource<String>> = MutableLiveData()
    private val _setProfile: LiveEvent<Resource<List<ProfileLocal>>> = LiveEvent()
    private val _setPassword: LiveEvent<Resource<String>> = LiveEvent()
    private val _setPhoto: LiveEvent<Resource<String>> = LiveEvent()
    private val _getAccount: LiveEvent<Resource<UsersLocal>> = LiveEvent()

    val showAccount: LiveData<Resource<List<ProfileLocal>>> = _showAccount
    val showPhoto: LiveData<Resource<String>> = _showPhoto
    val setProfile: LiveData<Resource<List<ProfileLocal>>> = _setProfile
    val setPassword: LiveData<Resource<String>> = _setPassword
    val setPhoto: LiveData<Resource<String>> = _setPhoto
    val getAccount: LiveData<Resource<UsersLocal>> = _getAccount

    fun showAccount() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = localRepository.getAccount()
            response?.let {
                val list = listOf(
                    ProfileLocal(R.drawable.ic_baseline_person_outline_24, "Nama lengkap", it.fullname),
                    ProfileLocal(R.drawable.ic_outline_person_pin_24, "Username", it.username),
                    ProfileLocal(R.drawable.ic_outline_alternate_email_24, "Email", it.email)
                )
                _showAccount.postValue(Resource.Success(list))
            }
        } catch (ex: Exception) {
            _showAccount.postValue(
                ex.message?.let { Resource.Error(it) }
            )
        }
    }
    fun showPhoto() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = localRepository.getAccount()
            response?.let {
                _showPhoto.postValue(Resource.Success(it.photo.orEmpty()))
            }
        } catch (ex: Exception) {
            _showPhoto.postValue(
                ex.message?.let { Resource.Error(it) }
            )
        }
    }
    fun setProfile(item: UsersLocal) = CoroutineScope(Dispatchers.IO).launch {
        try {
            localRepository.setProfile(item)
            val list = listOf(
                ProfileLocal(R.drawable.ic_baseline_person_outline_24, "Nama lengkap", item.fullname),
                ProfileLocal(R.drawable.ic_outline_person_pin_24, "Username", item.username),
                ProfileLocal(R.drawable.ic_outline_alternate_email_24, "Email", item.email)
            )
            _setProfile.postValue(Resource.Success(list))
        } catch (ex: Exception) {
            _setProfile.postValue(
                ex.message?.let { Resource.Error(it) }
            )
        }
    }
    fun setPassword(item: UsersLocal) = CoroutineScope(Dispatchers.IO).launch {
        try {
            localRepository.setPassword(item)
            _setPassword.postValue(Resource.Success("Berhasil mengubah password!"))
        } catch (ex: Exception) {
            _setPassword.postValue(
                ex.message?.let { Resource.Error(it) }
            )
        }
    }
    fun setPhoto(photo: MultipartBody.Part) = CoroutineScope(Dispatchers.IO).launch {
        _setPhoto.postValue(Resource.Loading())
        try {
            val response = remoteRepository.photoApi(photo)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        localRepository.setPhoto(it.data?.url.orEmpty())
                        _setPhoto.postValue(Resource.Success(it.data?.url.orEmpty(), "Foto berhasil disimpan!"))
                    }
                } else {
                    response.body()?.let {
                        throw Exception("${it.status}")
                    }
                }
            }
        } catch (ex: Exception) {
            _setPhoto.postValue(
                ex.message?.let { Resource.Error(it) }
            )
        }
    }
    fun getAccount() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = localRepository.getAccount()
            response?.let {
                _getAccount.postValue(Resource.Success(it))
            }
        } catch (ex: Exception) {
            _getAccount.postValue(
                ex.message?.let { Resource.Error(it) }
            )
        }
    }
}