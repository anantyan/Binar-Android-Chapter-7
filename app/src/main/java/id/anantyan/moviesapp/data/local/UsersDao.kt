package id.anantyan.moviesapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.anantyan.moviesapp.data.local.model.UsersLocal

@Dao
interface UsersDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun register(item: UsersLocal): Long

    @Query("SELECT * FROM tbl_users WHERE email=:email AND password=:password")
    suspend fun login(email: String?, password: String?): UsersLocal?

    @Query("SELECT * FROM tbl_users WHERE username=:username OR email=:email")
    suspend fun checkAccount(username: String?, email: String?): UsersLocal?

    @Query("SELECT * FROM tbl_users WHERE userId=:userId")
    suspend fun showAccount(userId: Int?): UsersLocal?

    @Query("UPDATE tbl_users SET fullname=:fullname, username=:username, email=:email WHERE userId=:userId")
    suspend fun setProfile(userId: Int?, fullname: String?, username: String?, email: String?)

    @Query("UPDATE tbl_users SET password=:password WHERE userId=:userId")
    suspend fun setPassword(userId: Int?, password: String?)

    @Query("UPDATE tbl_users SET photo=:photo WHERE userId=:userId")
    suspend fun setPhoto(userId: Int?, photo: String?)
}