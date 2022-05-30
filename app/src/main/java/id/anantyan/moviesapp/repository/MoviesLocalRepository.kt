package id.anantyan.moviesapp.repository

import id.anantyan.moviesapp.data.local.MoviesDao
import id.anantyan.moviesapp.data.local.model.MoviesLocal
import id.anantyan.utils.sharedPreferences.DataStoreManager
import javax.inject.Inject

class MoviesLocalRepository @Inject constructor(
    private val moviesDao: MoviesDao,
    private val store: DataStoreManager
) {
    suspend fun insertMovies(item: MoviesLocal) = moviesDao.insertMovies(
        MoviesLocal(
            userId = store.getUserId(),
            overview = item.overview,
            title = item.title,
            posterPath = item.posterPath,
            releaseDate = item.releaseDate,
            voteAverage = item.voteAverage,
            movieId = item.movieId
        )
    )
    suspend fun deleteMovies(movieId: Int) = moviesDao.deleteMovies(movieId, store.getUserId())
    suspend fun checkMovies(movieId: Int) = moviesDao.checkMovies(movieId, store.getUserId()) == null
    fun selectMovies() = moviesDao.selectMovies(store.getUserId())
}