package id.anantyan.moviesapp.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
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
    fun trendingPage() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100, enablePlaceholders = false),
        pagingSourceFactory = { MoviesPagingSource(moviesApi, "trending") }
    ).liveData
    fun popularPage() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100, enablePlaceholders = false),
        pagingSourceFactory = { MoviesPagingSource(moviesApi, CAT_POPULAR) }
    ).liveData
    fun topRatedPage() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100, enablePlaceholders = false),
        pagingSourceFactory = { MoviesPagingSource(moviesApi, CAT_TOP_RATED) }
    ).liveData
    fun nowPlayingPage() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100, enablePlaceholders = false),
        pagingSourceFactory = { MoviesPagingSource(moviesApi, CAT_NOW_PLAYING) }
    ).liveData
    fun upcomingPage() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100, enablePlaceholders = false),
        pagingSourceFactory = { MoviesPagingSource(moviesApi, CAT_UPCOMING) }
    ).liveData
}