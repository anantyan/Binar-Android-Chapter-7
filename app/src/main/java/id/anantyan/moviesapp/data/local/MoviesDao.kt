package id.anantyan.moviesapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import id.anantyan.moviesapp.data.local.model.MoviesLocal

@Dao
interface MoviesDao {
    @Query("DELETE FROM tbl_movies WHERE movie_id=:movieId AND user_id=:usrId")
    suspend fun deleteMovies(movieId: Int?, usrId: Int?)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertMovies(item: MoviesLocal): Long

    @Query("SELECT * FROM tbl_movies WHERE movie_id=:movieId AND user_id=:usrId")
    suspend fun checkMovies(movieId: Int?, usrId: Int?): MoviesLocal?

    @Query("SELECT * FROM tbl_movies WHERE user_id=:userId")
    fun selectMovies(userId: Int?): LiveData<List<MoviesLocal>>
}