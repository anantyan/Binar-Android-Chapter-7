package id.anantyan.moviesapp.ui.auth.register

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
class RegisterViewModel @Inject constructor(
    private val localRepository: UsersLocalRepository
) : ViewModel() {
    private val _register: LiveEvent<Resource<Long>> = LiveEvent()
    val register: LiveData<Resource<Long>> = _register

    fun register(email: String, password: String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val check = localRepository.checkAccountByEmail(email)
            if (check == null) {
                val response = localRepository.register(email, password)
                if (response != 0L) {
                    _register.postValue(Resource.Success(response, "Akun berhasil dibuat!"))
                } else {
                    throw Exception("Gagal membuat akun!")
                }
            } else {
                throw Exception("Akun sudah dibuat!")
            }
        } catch (ex: Exception) {
            _register.postValue(
                ex.message?.let { Resource.Error(it) }
            )
        }
    }
}