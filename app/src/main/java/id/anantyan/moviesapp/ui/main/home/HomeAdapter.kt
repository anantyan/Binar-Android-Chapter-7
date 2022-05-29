package id.anantyan.moviesapp.ui.main.home

import android.util.Log
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
import id.anantyan.moviesapp.databinding.ListItemHomeBinding
import id.anantyan.moviesapp.model.ResultsItem
import id.anantyan.moviesapp.repository.MoviesLocalRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeAdapter @Inject constructor(
    private val localRepository: MoviesLocalRepository
) : ListAdapter<ResultsItem, RecyclerView.ViewHolder>(diffUtilCallback), HomeAdapterHelper {

    private var _onClick: ((Int, Int) -> Unit)? = null

    inner class ViewHolder(val binding: ListItemHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                _onClick?.let {
                    it(adapterPosition, getItem(adapterPosition).id!!)
                }
            }
            binding.btnFavorite.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    if (localRepository.checkMovies(getItem(adapterPosition).id!!)) {
                        localRepository.insertMovies(getItem(adapterPosition).toMoviesLocal())
                        binding.btnFavorite.setImageResource(R.drawable.ic_baseline_star_24)
                    } else {
                        localRepository.deleteMovies(getItem(adapterPosition).toMoviesLocal())
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
            id = this.id
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            ListItemHomeBinding.inflate(
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

    override fun init(): ListAdapter<ResultsItem, RecyclerView.ViewHolder> {
        return this
    }

    override fun differ(list: List<ResultsItem>) {
        submitList(list)
    }

    override fun onClick(listener: (Int, Int) -> Unit) {
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