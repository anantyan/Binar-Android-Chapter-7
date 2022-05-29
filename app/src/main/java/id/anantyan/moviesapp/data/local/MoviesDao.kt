package id.anantyan.moviesapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import id.anantyan.moviesapp.data.local.model.MoviesLocal

@Dao
interface MoviesDao {
    @Delete
    suspend fun deleteMovies(item: MoviesLocal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(item: MoviesLocal): Long

    @Query("SELECT * FROM tbl_movies WHERE id=:id AND user_id=:usrId")
    suspend fun checkMovies(id: Int?, usrId: Int?): MoviesLocal?

    @Query("SELECT * FROM tbl_movies WHERE user_id=:id")
    fun selectMovies(id: Int?): LiveData<List<MoviesLocal>?>
}