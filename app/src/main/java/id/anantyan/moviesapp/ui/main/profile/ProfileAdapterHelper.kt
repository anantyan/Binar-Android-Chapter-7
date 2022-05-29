package id.anantyan.moviesapp.ui.main.profile

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.anantyan.moviesapp.data.local.model.ProfileLocal

interface ProfileAdapterHelper {
    fun init(): ListAdapter<ProfileLocal, RecyclerView.ViewHolder>
    fun differ(list: List<ProfileLocal>)
}