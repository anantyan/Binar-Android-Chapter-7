package id.anantyan.moviesapp.repository

import id.anantyan.moviesapp.data.local.UsersDao
import id.anantyan.moviesapp.data.local.model.UsersLocal
import id.anantyan.utils.sharedPreferences.DataStoreManager
import javax.inject.Inject

class UsersLocalRepository @Inject constructor(
    private val usersDao: UsersDao,
    private val store: DataStoreManager
) {
    suspend fun getAccount() = usersDao.showAccount(store.getUserId())
    suspend fun setPassword(password: String) = usersDao.setPassword(store.getUserId(), password)
    suspend fun setProfile(item: UsersLocal) = usersDao.setProfile(store.getUserId(), item.fullname, item.username, item.email)
    suspend fun setPhoto(urlPhoto: String) = usersDao.setPhoto(store.getUserId(), urlPhoto)
    suspend fun login(email: String, password: String) = usersDao.login(email, password)
    suspend fun register(email: String, password: String) = usersDao.register(
        UsersLocal(
            email = email,
            password = password
        )
    )
    suspend fun checkAccountByEmail(email: String) = usersDao.checkAccount(null, email)
}