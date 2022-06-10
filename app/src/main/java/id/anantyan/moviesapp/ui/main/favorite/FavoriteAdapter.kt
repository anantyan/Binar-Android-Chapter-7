package id.anantyan.moviesapp.ui.main.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.ViewSizeResolver
import coil.transform.RoundedCornersTransformation
import id.anantyan.moviesapp.R
import id.anantyan.moviesapp.data.local.model.MoviesLocal
import id.anantyan.moviesapp.databinding.ListItemFavoritesBinding
import id.anantyan.moviesapp.repository.MoviesLocalRepository
import id.anantyan.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteAdapter @Inject constructor(
    private val localRepository: MoviesLocalRepository
) : ListAdapter<MoviesLocal, RecyclerView.ViewHolder>(diffUtilCallback) {

    private var _onClick: ((Int, Int) -> Unit)? = null

    inner class ViewHolder(val binding: ListItemFavoritesBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                _onClick?.let {
                    it(bindingAdapterPosition, getItem(bindingAdapterPosition).movieId!!)
                }
            }
            binding.btnFavorite.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    if (localRepository.checkMovies(getItem(bindingAdapterPosition).movieId!!)) {
                        localRepository.insertMovies(getItem(bindingAdapterPosition))
                        binding.btnFavorite.setImageResource(R.drawable.ic_baseline_star_24)
                    } else {
                        localRepository.deleteMovies(getItem(bindingAdapterPosition).movieId!!)
                        binding.btnFavorite.setImageResource(R.drawable.ic_baseline_star_border_24)
                    }
                }
            }
        }

        fun bind(item: MoviesLocal) {
            binding.txtNama.text = item.title
            binding.txtRate.text = item.voteAverage.toString()
            binding.releaseDate.text = item.releaseDate
            binding.description.text = item.overview
            binding.imgPosterPath.load(item.posterPath) {
                crossfade(true)
                placeholder(R.drawable.ic_outline_image_24)
                error(R.drawable.ic_outline_image_not_supported_24)
                transformations(RoundedCornersTransformation(16F))
                size(ViewSizeResolver(binding.imgPosterPath))
            }
            CoroutineScope(Dispatchers.Main).launch {
                if (localRepository.checkMovies(item.movieId!!)) {
                    binding.btnFavorite.setImageResource(R.drawable.ic_baseline_star_border_24)
                } else {
                    binding.btnFavorite.setImageResource(R.drawable.ic_baseline_star_24)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            ListItemFavoritesBinding.inflate(
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

    fun onClick(listener: (Int, Int) -> Unit) {
        _onClick = listener
    }
}

val diffUtilCallback = object : DiffUtil.ItemCallback<MoviesLocal>() {
    override fun areItemsTheSame(oldItem: MoviesLocal, newItem: MoviesLocal): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MoviesLocal, newItem: MoviesLocal): Boolean {
        return oldItem == newItem
    }
}