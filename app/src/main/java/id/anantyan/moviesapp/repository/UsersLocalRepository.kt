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
    suspend fun setPassword(item: UsersLocal) = usersDao.setPassword(store.getUserId(), item.password)
    suspend fun setProfile(item: UsersLocal) = usersDao.setProfile(store.getUserId(), item.fullname, item.username, item.email)
    suspend fun setPhoto(urlPhoto: String) = usersDao.setPhoto(store.getUserId(), urlPhoto)
    suspend fun login(item: UsersLocal) = usersDao.login(item.email, item.password)
    suspend fun register(item: UsersLocal) = usersDao.register(item)
    suspend fun checkAccount(item: UsersLocal) = usersDao.checkAccount(null, item.email)
}