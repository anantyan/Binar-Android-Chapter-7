package id.anantyan.moviesapp.ui.main

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    state: SavedStateHandle
): ViewModel() {

    private val _movieId = state.getLiveData("movieId", -1)
    val movieId: LiveData<Int> = _movieId

    private val _titleActionBar = state.getLiveData("titleActionBar", "")
    val titleActionBar: LiveData<String> = _titleActionBar

    private val _category = state.getLiveData("category", "")
    val category: LiveData<String> = _category

    fun movieId(id: Int) {
        _movieId.postValue(id)
    }

    fun titleActionBar(title: String) {
        _titleActionBar.postValue(title)
    }

    fun category(data: String) {
        _category.postValue(data)
    }

    fun actionThread() = CoroutineScope(Dispatchers.Main).launch {
        for (num in 0..100) {
            delay(1000)
            Log.d("COROUTINE-KOTLIN", num.toString())
        }
    }
}