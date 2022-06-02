package id.anantyan.moviesapp.ui.main.home_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.ViewSizeResolver
import coil.transform.RoundedCornersTransformation
import id.anantyan.moviesapp.R
import id.anantyan.moviesapp.databinding.ListItemCreditsBinding
import id.anantyan.moviesapp.model.CastItem
import javax.inject.Inject

class CasterAdapter @Inject constructor() : ListAdapter<CastItem, RecyclerView.ViewHolder>(diffUtilCaster) {
    inner class ViewHolder(private val binding: ListItemCreditsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CastItem) {
            binding.txtName.text = item.originalName
            binding.txtCharacter.text = item.character
            binding.imgProfilePath.load(item.profilePath) {
                transformations(RoundedCornersTransformation(16F))
                size(ViewSizeResolver(binding.imgProfilePath))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            ListItemCreditsBinding.inflate(
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
}

val diffUtilCaster = object : DiffUtil.ItemCallback<CastItem>() {
    override fun areItemsTheSame(oldItem: CastItem, newItem: CastItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CastItem, newItem: CastItem): Boolean {
        return oldItem == newItem
    }
}

interface CasterAdapterHelper {
    fun init(): ListAdapter<CastItem, RecyclerView.ViewHolder>
    fun differ(list: List<CastItem>)
}