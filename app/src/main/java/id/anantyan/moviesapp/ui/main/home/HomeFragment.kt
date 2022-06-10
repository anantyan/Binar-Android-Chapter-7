package id.anantyan.moviesapp.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.anantyan.moviesapp.R
import id.anantyan.moviesapp.databinding.FragmentHomeBinding
import id.anantyan.moviesapp.ui.main.MainViewModel
import id.anantyan.utils.Constant
import id.anantyan.utils.Resource
import id.anantyan.utils.checkInternet
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: MainViewModel by activityViewModels()
    private val viewModel: HomeViewModel by viewModels()

    @Inject lateinit var adapterTrending: HomeAdapter
    @Inject
    @Named(Constant.CAT_POPULAR)
    lateinit var adapterPopular: HomeAdapter
    @Inject
    @Named(Constant.CAT_TOP_RATED)
    lateinit var adapterTopRated: HomeAdapter
    @Inject
    @Named(Constant.CAT_NOW_PLAYING)
    lateinit var adapterNowPlaying: HomeAdapter
    @Inject
    @Named(Constant.CAT_UPCOMING)
    lateinit var adapterUpComing: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBindView(view)
        onBindObserver()
    }

    private fun onBindView(view: View) {
        binding.btnNowPlayingDetail.setOnClickListener {
            sharedViewModel.titleActionBar("Now Playing")
            sharedViewModel.category(Constant.CAT_NOW_PLAYING)
            val destination = HomeFragmentDirections.actionHomeFragmentToCategoryFragment()
            view.findNavController().navigate(destination)
        }
        binding.btnPopularDetail.setOnClickListener {
            sharedViewModel.titleActionBar("Popular")
            sharedViewModel.category(Constant.CAT_POPULAR)
            val destination = HomeFragmentDirections.actionHomeFragmentToCategoryFragment()
            view.findNavController().navigate(destination)
        }
        binding.btnTopRatedDetail.setOnClickListener {
            sharedViewModel.titleActionBar("Top Rated")
            sharedViewModel.category(Constant.CAT_TOP_RATED)
            val destination = HomeFragmentDirections.actionHomeFragmentToCategoryFragment()
            view.findNavController().navigate(destination)
        }
        binding.btnTrendingDetail.setOnClickListener {
            sharedViewModel.titleActionBar("Trending")
            sharedViewModel.category("trending")
            val destination = HomeFragmentDirections.actionHomeFragmentToCategoryFragment()
            view.findNavController().navigate(destination)
        }
        binding.btnUpcomingDetail.setOnClickListener {
            sharedViewModel.titleActionBar("Upcoming")
            sharedViewModel.category(Constant.CAT_UPCOMING)
            val destination = HomeFragmentDirections.actionHomeFragmentToCategoryFragment()
            view.findNavController().navigate(destination)
        }

        binding.rvTrending.setHasFixedSize(true)
        binding.rvTrending.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.rvTrending.itemAnimator = DefaultItemAnimator()
        binding.rvTrending.isNestedScrollingEnabled = true
        binding.rvTrending.adapter = adapterTrending
        adapterTrending.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        adapterTrending.onClick { _, movies ->
            val destination = HomeFragmentDirections.actionHomeFragmentToHomeDetailFragment()
            view.findNavController().navigate(destination)
            sharedViewModel.movieId(movies.id!!)
        }

        binding.rvPopular.setHasFixedSize(true)
        binding.rvPopular.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.rvPopular.itemAnimator = DefaultItemAnimator()
        binding.rvPopular.isNestedScrollingEnabled = true
        binding.rvPopular.adapter = adapterPopular
        adapterPopular.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        adapterPopular.onClick { _, movies ->
            val destination = HomeFragmentDirections.actionHomeFragmentToHomeDetailFragment()
            view.findNavController().navigate(destination)
            sharedViewModel.movieId(movies.id!!)
        }

        binding.rvTopRated.setHasFixedSize(true)
        binding.rvTopRated.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.rvTopRated.itemAnimator = DefaultItemAnimator()
        binding.rvTopRated.isNestedScrollingEnabled = true
        binding.rvTopRated.adapter = adapterTopRated
        adapterTopRated.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        adapterTopRated.onClick { _, movies ->
            val destination = HomeFragmentDirections.actionHomeFragmentToHomeDetailFragment()
            view.findNavController().navigate(destination)
            sharedViewModel.movieId(movies.id!!)
        }

        binding.rvUpcoming.setHasFixedSize(true)
        binding.rvUpcoming.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.rvUpcoming.itemAnimator = DefaultItemAnimator()
        binding.rvUpcoming.isNestedScrollingEnabled = true
        binding.rvUpcoming.adapter = adapterUpComing
        adapterUpComing.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        adapterUpComing.onClick { _, movies ->
            val destination = HomeFragmentDirections.actionHomeFragmentToHomeDetailFragment()
            view.findNavController().navigate(destination)
            sharedViewModel.movieId(movies.id!!)
        }

        binding.rvNowPlaying.setHasFixedSize(true)
        binding.rvNowPlaying.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.rvNowPlaying.itemAnimator = DefaultItemAnimator()
        binding.rvNowPlaying.isNestedScrollingEnabled = true
        binding.rvNowPlaying.adapter = adapterNowPlaying
        adapterNowPlaying.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        adapterNowPlaying.onClick { _, movies ->
            val destination = HomeFragmentDirections.actionHomeFragmentToHomeDetailFragment()
            view.findNavController().navigate(destination)
            sharedViewModel.movieId(movies.id!!)
        }
    }

    private fun onBindObserver() {
        viewModel.trendingResponse.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    binding.progressTrending.visibility = View.GONE
                    adapterTrending.submitList(it.data?.results)
                }
                is Resource.Loading -> {
                    binding.progressTrending.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    binding.progressTrending.visibility = View.GONE
                    onSnackbar("${it.message}")
                }
            }
        }
        viewModel.popularResponse.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    binding.progressPopular.visibility = View.GONE
                    adapterPopular.submitList(it.data?.results)
                }
                is Resource.Loading -> {
                    binding.progressPopular.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    binding.progressPopular.visibility = View.GONE
                    onSnackbar("${it.message}")
                }
            }
        }
        viewModel.topRatedResponse.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    binding.progressTopRated.visibility = View.GONE
                    adapterTopRated.submitList(it.data?.results)
                }
                is Resource.Loading -> {
                    binding.progressTopRated.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    binding.progressTopRated.visibility = View.GONE
                    onSnackbar("${it.message}")
                }
            }
        }
        viewModel.nowPlayingResponse.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    binding.progressNowPlaying.visibility = View.GONE
                    adapterNowPlaying.submitList(it.data?.results)
                }
                is Resource.Loading -> {
                    binding.progressNowPlaying.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    binding.progressNowPlaying.visibility = View.GONE
                    onSnackbar("${it.message}")
                }
            }
        }
        viewModel.upcomingResponse.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    binding.progressUpcoming.visibility = View.GONE
                    adapterUpComing.submitList(it.data?.results)
                }
                is Resource.Loading -> {
                    binding.progressUpcoming.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    binding.progressUpcoming.visibility = View.GONE
                    onSnackbar("${it.message}")
                }
            }
        }
        requireContext().checkInternet().observe(viewLifecycleOwner) {
            if (it == true) {
                viewModel.getTrendingWeek()
                viewModel.getPopular()
                viewModel.getTopRated()
                viewModel.getNowPlaying()
                viewModel.getUpComing()
            }
        }
    }

    private fun onSnackbar(message: String) {
        val snackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
        snackbar.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.error))
        snackbar.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}