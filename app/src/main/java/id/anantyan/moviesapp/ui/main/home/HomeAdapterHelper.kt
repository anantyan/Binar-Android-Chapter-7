package id.anantyan.moviesapp.ui.main.home

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.anantyan.moviesapp.model.ResultsItem

interface HomeAdapterHelper {
    fun init(): ListAdapter<ResultsItem, RecyclerView.ViewHolder>
    fun differ(list: List<ResultsItem>)
    fun onClick(listener: (Int, Int) -> Unit)
}