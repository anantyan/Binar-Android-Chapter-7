package id.anantyan.moviesapp.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.anantyan.moviesapp.model.ResultsItem
import id.anantyan.moviesapp.repository.MoviesLocalRepository
import id.anantyan.moviesapp.repository.MoviesRemoteRepository
import id.anantyan.utils.Constant
import id.anantyan.utils.Resource
import id.anantyan.utils.sharedPreferences.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val remoteRepository: MoviesRemoteRepository
) : ViewModel() {
    private val _trendingResponse: MutableLiveData<Resource<List<ResultsItem>>> = MutableLiveData()
    private val _popularResponse: MutableLiveData<Resource<List<ResultsItem>>> = MutableLiveData()
    private val _topRatedResponse: MutableLiveData<Resource<List<ResultsItem>>> = MutableLiveData()
    private val _nowPlayingResponse: MutableLiveData<Resource<List<ResultsItem>>> = MutableLiveData()
    private val _upcomingResponse: MutableLiveData<Resource<List<ResultsItem>>> = MutableLiveData()

    val trendingResponse: LiveData<Resource<List<ResultsItem>>> = _trendingResponse
    val popularResponse: LiveData<Resource<List<ResultsItem>>> = _popularResponse
    val topRatedResponse: LiveData<Resource<List<ResultsItem>>> = _topRatedResponse
    val nowPlayingResponse: LiveData<Resource<List<ResultsItem>>> = _nowPlayingResponse
    val upcomingResponse: LiveData<Resource<List<ResultsItem>>> = _upcomingResponse

    fun getTrendingWeek() = CoroutineScope(Dispatchers.IO).launch {
        _trendingResponse.postValue(Resource.Loading())
        try {
            val response = remoteRepository.trendingWeek()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _trendingResponse.postValue(Resource.Success(it.results!!))
                    }
                } else {
                    response.body()?.let {
                        throw Exception(it.statusMessage)
                    }
                }
            }
        } catch(ex: Exception) {
            ex.message?.let {
                _trendingResponse.postValue(Resource.Error(it))
            }
        }
    }
    fun getPopular() = CoroutineScope(Dispatchers.IO).launch {
        _popularResponse.postValue(Resource.Loading())
        try {
            val response = remoteRepository.popular()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _popularResponse.postValue(Resource.Success(it.results!!))
                    }
                } else {
                    response.body()?.let {
                        throw Exception(it.statusMessage)
                    }
                }
            }
        } catch (ex: Exception) {
            ex.message?.let {
                _popularResponse.postValue(Resource.Error(it))
            }
        }
    }
    fun getTopRated() = CoroutineScope(Dispatchers.IO).launch {
        _topRatedResponse.postValue(Resource.Loading())
        try {
            val response = remoteRepository.topRated()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _topRatedResponse.postValue(Resource.Success(it.results!!))
                    }
                } else {
                    response.body()?.let {
                        throw Exception(it.statusMessage)
                    }
                }
            }
        } catch (ex: Exception) {
            ex.message?.let {
                _topRatedResponse.postValue(Resource.Error(it))
            }
        }
    }
    fun getNowPlaying() = CoroutineScope(Dispatchers.IO).launch {
        _nowPlayingResponse.postValue(Resource.Loading())
        try {
            val response = remoteRepository.nowPlaying()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _nowPlayingResponse.postValue(Resource.Success(it.results!!))
                    }
                } else {
                    response.body()?.let {
                        throw Exception(it.statusMessage)
                    }
                }
            }
        } catch (ex: Exception) {
            ex.message?.let {
                _nowPlayingResponse.postValue(Resource.Error(it))
            }
        }
    }
    fun getUpComing() = CoroutineScope(Dispatchers.IO).launch {
        _upcomingResponse.postValue(Resource.Loading())
        try {
            val response = remoteRepository.upcoming()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _upcomingResponse.postValue(Resource.Success(it.results!!))
                    }
                } else {
                    response.body()?.let {
                        throw Exception(it.statusMessage)
                    }
                }
            }
        } catch (ex: Exception) {
            ex.message?.let {
                _upcomingResponse.postValue(Resource.Error(it))
            }
        }
    }
}