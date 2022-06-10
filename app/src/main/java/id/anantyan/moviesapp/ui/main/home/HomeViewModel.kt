package id.anantyan.moviesapp.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.anantyan.moviesapp.model.Movies
import id.anantyan.moviesapp.repository.MoviesRemoteRepository
import id.anantyan.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val remoteRepository: MoviesRemoteRepository
) : ViewModel() {
    private val _trendingResponse: MutableLiveData<Resource<Movies>> = MutableLiveData()
    private val _popularResponse: MutableLiveData<Resource<Movies>> = MutableLiveData()
    private val _topRatedResponse: MutableLiveData<Resource<Movies>> = MutableLiveData()
    private val _nowPlayingResponse: MutableLiveData<Resource<Movies>> = MutableLiveData()
    private val _upcomingResponse: MutableLiveData<Resource<Movies>> = MutableLiveData()

    val trendingResponse: LiveData<Resource<Movies>> = _trendingResponse
    val popularResponse: LiveData<Resource<Movies>> = _popularResponse
    val topRatedResponse: LiveData<Resource<Movies>> = _topRatedResponse
    val nowPlayingResponse: LiveData<Resource<Movies>> = _nowPlayingResponse
    val upcomingResponse: LiveData<Resource<Movies>> = _upcomingResponse

    fun getTrendingWeek() = CoroutineScope(Dispatchers.IO).launch {
        _trendingResponse.postValue(Resource.Loading())
        try {
            val response = remoteRepository.trendingWeek()
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    response.body()?.let {
                        _trendingResponse.postValue(Resource.Success(it))
                    }
                }
            } else {
                response.body()?.let {
                    throw Exception(it.statusMessage)
                }
            }
        } catch(ex: Exception) {
            ex.message?.let {
                _trendingResponse.postValue(Resource.Error(code = null, message = it))
            }
        }
    }
    fun getPopular() = CoroutineScope(Dispatchers.IO).launch {
       _popularResponse.postValue(Resource.Loading())
        try {
            val response = remoteRepository.popular()
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    response.body()?.let {
                        _popularResponse.postValue(Resource.Success(it))
                    }
                }
            } else {
                response.body()?.let {
                    throw Exception(it.statusMessage)
                }
            }
        } catch (ex: Exception) {
            withContext(Dispatchers.Main) {
                ex.message?.let {
                    _popularResponse.postValue(Resource.Error(code = null, message = it))
                }
            }
        }
    }

    fun getTopRated() = CoroutineScope(Dispatchers.IO).launch {
        _topRatedResponse.postValue(Resource.Loading())
        try {
            val response = remoteRepository.topRated()
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    response.body()?.let {
                        _topRatedResponse.postValue(Resource.Success(it))
                    }
                }
            } else {
                response.body()?.let {
                    throw Exception(it.statusMessage)
                }
            }
        } catch (ex: Exception) {
            withContext(Dispatchers.Main) {
                ex.message?.let {
                    _topRatedResponse.postValue(Resource.Error(code = null, message = it))
                }
            }
        }
    }
    fun getNowPlaying() = CoroutineScope(Dispatchers.IO).launch {
        _nowPlayingResponse.postValue(Resource.Loading())
        try {
            val response = remoteRepository.nowPlaying()
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    response.body()?.let {
                        _nowPlayingResponse.postValue(Resource.Success(it))
                    }
                }
            } else {
                response.body()?.let {
                    throw Exception(it.statusMessage)
                }
            }
        } catch (ex: Exception) {
            withContext(Dispatchers.Main) {
                ex.message?.let {
                    _nowPlayingResponse.postValue(Resource.Error(code = null, message = it))
                }
            }
        }
    }
    fun getUpComing() = CoroutineScope(Dispatchers.IO).launch {
        _upcomingResponse.postValue(Resource.Loading())
        try {
            val response = remoteRepository.upcoming()
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    response.body()?.let {
                        _upcomingResponse.postValue(Resource.Success(it))
                    }
                }
            } else {
                response.body()?.let {
                    throw Exception(it.statusMessage)
                }
            }
        } catch (ex: Exception) {
            withContext(Dispatchers.Main) {
                ex.message?.let {
                    _upcomingResponse.postValue(Resource.Error(code = null, message = it))
                }
            }
        }
    }
}