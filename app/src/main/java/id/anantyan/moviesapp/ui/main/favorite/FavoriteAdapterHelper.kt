package id.anantyan.moviesapp.ui.main.favorite

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.anantyan.moviesapp.data.local.model.MoviesLocal

interface FavoriteAdapterHelper {
    fun init(): ListAdapter<MoviesLocal, RecyclerView.ViewHolder>
    fun differ(list: List<MoviesLocal>)
    fun onClick(listener: (Int, Int) -> Unit)
}