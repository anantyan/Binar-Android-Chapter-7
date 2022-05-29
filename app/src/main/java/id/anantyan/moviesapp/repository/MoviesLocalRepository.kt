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
            id = item.id
        )
    )
    suspend fun deleteMovies(item: MoviesLocal) = moviesDao.deleteMovies(
        MoviesLocal(
            userId = store.getUserId(),
            overview = item.overview,
            title = item.title,
            posterPath = item.posterPath,
            releaseDate = item.releaseDate,
            voteAverage = item.voteAverage,
            id = item.id
        )
    )
    suspend fun checkMovies(id: Int) = moviesDao.checkMovies(id, store.getUserId()) == null
    fun selectMovies() = moviesDao.selectMovies(store.getUserId())
}