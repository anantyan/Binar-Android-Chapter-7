package id.anantyan.moviesapp.ui.main.home_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.anantyan.moviesapp.R
import id.anantyan.moviesapp.data.local.model.MoviesLocal
import id.anantyan.moviesapp.model.CastItem
import id.anantyan.moviesapp.model.MoviesDetail
import id.anantyan.moviesapp.repository.MoviesLocalRepository
import id.anantyan.moviesapp.repository.MoviesRemoteRepository
import id.anantyan.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeDetailViewModel @Inject constructor(
    private val remoteRepository: MoviesRemoteRepository,
    private val localRepository: MoviesLocalRepository
) : ViewModel() {
    private val _getCreditsByIdResponse: MutableLiveData<Resource<List<CastItem>>> = MutableLiveData()
    private val _getMovieByIdResponse: MutableLiveData<Resource<MoviesDetail>> = MutableLiveData()
    private val _checkFavoriteResponse: MutableLiveData<Resource<Boolean>> = MutableLiveData()

    val getMovieByIdResponse: LiveData<Resource<MoviesDetail>> = _getMovieByIdResponse
    val getCreditsByIdResponse: LiveData<Resource<List<CastItem>>> = _getCreditsByIdResponse
    val checkFavoriteResponse: LiveData<Resource<Boolean>> = _checkFavoriteResponse

    fun getMovieById(id: String) = CoroutineScope(Dispatchers.IO).launch {
        _getMovieByIdResponse.postValue(Resource.Loading())
        try {
            val response = remoteRepository.getMovieById(id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _getMovieByIdResponse.postValue(Resource.Success(it))
                    }
                } else {
                    response.body()?.let {
                        throw Exception(it.statusMessage)
                    }
                }
            }
        } catch(ex: Exception) {
            ex.message?.let {
                _getMovieByIdResponse.postValue(Resource.Error(it))
            }
        }
    }

    fun getCreditsById(id: String) = CoroutineScope(Dispatchers.IO).launch {
        _getCreditsByIdResponse.postValue(Resource.Loading())
        try {
            val response = remoteRepository.getCreditsById(id = id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _getCreditsByIdResponse.postValue(Resource.Success(it.cast!!))
                    }
                } else {
                    response.body()?.let {
                        throw Exception(it.statusMessage)
                    }
                }
            }
        } catch(ex: Exception) {
            ex.message?.let {
                _getCreditsByIdResponse.postValue(Resource.Error(it))
            }
        }
    }

    fun checkFavorite(item: MoviesLocal) = CoroutineScope(Dispatchers.IO).launch {
        if (localRepository.checkMovies(item.movieId!!)) {
            localRepository.insertMovies(item)
            _checkFavoriteResponse.postValue(Resource.Success(true))
        } else {
            localRepository.deleteMovies(item.movieId!!)
            _checkFavoriteResponse.postValue(Resource.Success(false))
        }
    }

    fun checkIconFavorite(item: MoviesLocal) = CoroutineScope(Dispatchers.IO).launch {
        if (localRepository.checkMovies(item.movieId!!)) {
            _checkFavoriteResponse.postValue(Resource.Success(false))
        } else {
            _checkFavoriteResponse.postValue(Resource.Success(true))
        }
    }
}