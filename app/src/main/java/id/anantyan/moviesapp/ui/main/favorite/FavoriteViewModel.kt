package id.anantyan.moviesapp.ui.main.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.anantyan.moviesapp.data.local.model.MoviesLocal
import id.anantyan.moviesapp.repository.MoviesLocalRepository
import id.anantyan.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val localRepository: MoviesLocalRepository
) : ViewModel() {
    fun getList() = localRepository.selectMovies()
}