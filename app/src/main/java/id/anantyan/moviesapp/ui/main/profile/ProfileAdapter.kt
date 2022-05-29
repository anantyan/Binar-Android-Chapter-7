package id.anantyan.moviesapp.ui.main.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.anantyan.moviesapp.databinding.ListItemProfileBinding
import id.anantyan.moviesapp.data.local.model.ProfileLocal
import javax.inject.Inject

class ProfileAdapter @Inject constructor() : ListAdapter<ProfileLocal, RecyclerView.ViewHolder>(diffUtilCallback),
    ProfileAdapterHelper {
    inner class ViewHolder(private val binding: ListItemProfileBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProfileLocal) {
            binding.imageView.setImageResource(item.resId!!)
            binding.txtTitle.text = item.title
            binding.txtField.text = item.field
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            ListItemProfileBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        val item = getItem(position)
        holder.bind(item)
    }

    override fun init(): ListAdapter<ProfileLocal, RecyclerView.ViewHolder> {
        return this
    }

    override fun differ(list: List<ProfileLocal>) {
        submitList(list)
    }
}

val diffUtilCallback = object : DiffUtil.ItemCallback<ProfileLocal>() {
    override fun areItemsTheSame(oldItem: ProfileLocal, newItem: ProfileLocal): Boolean {
        return oldItem.resId == newItem.resId
    }

    override fun areContentsTheSame(oldItem: ProfileLocal, newItem: ProfileLocal): Boolean {
        return oldItem == newItem
    }
}