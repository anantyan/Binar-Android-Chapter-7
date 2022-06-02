package id.anantyan.moviesapp.ui.main.category

import androidx.lifecycle.ViewModel
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.anantyan.moviesapp.repository.MoviesRemoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    remoteRepository: MoviesRemoteRepository
) : ViewModel() {
    val trendingPage = remoteRepository.trendingPage().cachedIn(CoroutineScope(Dispatchers.IO))
    val popularPage = remoteRepository.popularPage().cachedIn(CoroutineScope(Dispatchers.IO))
    val nowPlayingPage = remoteRepository.nowPlayingPage().cachedIn(CoroutineScope(Dispatchers.IO))
    val upcomingPage = remoteRepository.upcomingPage().cachedIn(CoroutineScope(Dispatchers.IO))
    val topRatedPage = remoteRepository.topRatedPage().cachedIn(CoroutineScope(Dispatchers.IO))
}