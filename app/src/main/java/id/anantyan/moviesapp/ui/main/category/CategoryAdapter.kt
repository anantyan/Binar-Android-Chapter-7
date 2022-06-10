package id.anantyan.moviesapp.ui.main.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.ViewSizeResolver
import coil.transform.RoundedCornersTransformation
import id.anantyan.moviesapp.R
import id.anantyan.moviesapp.data.local.model.MoviesLocal
import id.anantyan.moviesapp.databinding.ListItemCategoryBinding
import id.anantyan.moviesapp.databinding.ListItemHomeBinding
import id.anantyan.moviesapp.model.ResultsItem
import id.anantyan.moviesapp.repository.MoviesLocalRepository
import id.anantyan.moviesapp.ui.main.home.HomeAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryAdapter @Inject constructor(
    private val localRepository: MoviesLocalRepository
) : PagingDataAdapter<ResultsItem, RecyclerView.ViewHolder>(diffUtilCallback) {
    private var _onClick: ((Int, Int) -> Unit)? = null

    inner class ViewHolder(val binding: ListItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                _onClick?.let {
                    it(bindingAdapterPosition, getItem(bindingAdapterPosition)?.id!!)
                }
            }
            binding.btnFavorite.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    if (localRepository.checkMovies(getItem(bindingAdapterPosition)?.id!!)) {
                        getItem(bindingAdapterPosition)?.toMoviesLocal()?.let { it1 -> localRepository.insertMovies(it1) }
                        binding.btnFavorite.setImageResource(R.drawable.ic_baseline_star_24)
                    } else {
                        localRepository.deleteMovies(getItem(bindingAdapterPosition)?.id!!)
                        binding.btnFavorite.setImageResource(R.drawable.ic_baseline_star_border_24)
                    }
                }
            }
        }

        private fun ResultsItem.toMoviesLocal() = MoviesLocal(
            overview = this.overview,
            title = this.title,
            posterPath = this.posterPath,
            releaseDate = this.releaseDate,
            voteAverage = this.voteAverage,
            movieId = this.id
        )

        fun bind(item: ResultsItem) {
            binding.txtNama.text = item.title
            binding.txtRate.text = item.voteAverage.toString()
            binding.imgPosterPath.load(item.posterPath) {
                crossfade(true)
                placeholder(R.drawable.ic_outline_image_24)
                error(R.drawable.ic_outline_image_not_supported_24)
                transformations(RoundedCornersTransformation(16F))
                size(ViewSizeResolver(binding.imgPosterPath))
            }
            CoroutineScope(Dispatchers.IO).launch {
                if (localRepository.checkMovies(item.id!!)) {
                    binding.btnFavorite.setImageResource(R.drawable.ic_baseline_star_border_24)
                } else {
                    binding.btnFavorite.setImageResource(R.drawable.ic_baseline_star_24)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            ListItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun onClick(listener: (Int, Int) -> Unit) {
        _onClick = listener
    }
}

val diffUtilCallback = object : DiffUtil.ItemCallback<ResultsItem>() {
    override fun areItemsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
        return oldItem == newItem
    }
}