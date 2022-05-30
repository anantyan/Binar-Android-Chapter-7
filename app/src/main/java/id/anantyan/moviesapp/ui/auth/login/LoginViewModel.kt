package id.anantyan.moviesapp.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.anantyan.moviesapp.data.local.model.UsersLocal
import id.anantyan.moviesapp.repository.UsersLocalRepository
import id.anantyan.utils.LiveEvent
import id.anantyan.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val localRepository: UsersLocalRepository
) : ViewModel() {
    private val _login: LiveEvent<Resource<UsersLocal>> = LiveEvent()
    val login: LiveData<Resource<UsersLocal>> = _login

    fun login(email: String, password: String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = localRepository.login(email, password)
            if (response != null) {
                _login.postValue(Resource.Success(response))
            } else {
                throw Exception("Email atau Password tidak sah!")
            }
        } catch (ex: Exception) {
            _login.postValue(
                ex.message?.let { Resource.Error(it) }
            )
        }
    }
}