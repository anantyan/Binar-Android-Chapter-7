package id.anantyan.moviesapp.repository

import androidx.lifecycle.MutableLiveData
import id.anantyan.moviesapp.data.api.MoviesApi
import id.anantyan.moviesapp.model.CastItem
import id.anantyan.moviesapp.model.MoviesDetail
import id.anantyan.moviesapp.model.ResultsItem
import id.anantyan.utils.Constant.CAT_NOW_PLAYING
import id.anantyan.utils.Constant.CAT_POPULAR
import id.anantyan.utils.Constant.CAT_TOP_RATED
import id.anantyan.utils.Constant.CAT_UPCOMING
import id.anantyan.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesRemoteRepository @Inject constructor(
    private val moviesApi: MoviesApi
) {
    suspend fun trendingWeek() = moviesApi.byTrendingWeek()
    suspend fun popular() = moviesApi.byCategory(path = CAT_POPULAR)
    suspend fun topRated() = moviesApi.byCategory(path = CAT_TOP_RATED)
    suspend fun nowPlaying() = moviesApi.byCategory(path = CAT_NOW_PLAYING)
    suspend fun upcoming() = moviesApi.byCategory(path = CAT_UPCOMING)
    suspend fun getMovieById(id: String) = moviesApi.getIdMovie(id = id)
    suspend fun getCreditsById(id: String) = moviesApi.getCredits(id = id)
}