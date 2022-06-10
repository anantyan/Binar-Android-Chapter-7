package id.anantyan.moviesapp.ui.main.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.anantyan.moviesapp.databinding.FragmentCategoryBinding
import id.anantyan.moviesapp.ui.main.MainActivity
import id.anantyan.moviesapp.ui.main.MainViewModel
import id.anantyan.utils.Constant
import javax.inject.Inject

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: MainViewModel by activityViewModels()
    private val viewModel: CategoryViewModel by viewModels()

    @Inject lateinit var adapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBindView(view)
        onBindObserver()
    }

    private fun onBindView(view: View) {
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        val loadStateHeader = CategoryLoadStateAdapter { adapter.retry() }
        val loadStateFooter = CategoryLoadStateAdapter { adapter.retry() }
        val concatAdapter = adapter.withLoadStateHeaderAndFooter(
            header = loadStateHeader,
            footer = loadStateFooter
        )

        binding.rvCategory.setHasFixedSize(true)
        binding.rvCategory.layoutManager = gridLayoutManager
        binding.rvCategory.itemAnimator = DefaultItemAnimator()
        binding.rvCategory.isNestedScrollingEnabled = false
        binding.rvCategory.adapter = concatAdapter
        binding.btnRetry.setOnClickListener { adapter.retry() }

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == 0 && loadStateHeader.itemCount > 0) {
                    2
                } else if (position == concatAdapter.itemCount - 1 && loadStateFooter.itemCount > 0) {
                    2
                } else {
                    1
                }
            }
        }

        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        adapter.addLoadStateListener { loadState ->
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.rvCategory.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.imgConnection.isVisible = loadState.source.refresh is LoadState.Error
            binding.btnRetry.isVisible = loadState.source.refresh is LoadState.Error
            /*binding.txtRetry.text = (loadState.source.refresh as? LoadState.Error)?.error?.message.toString()*/
        }
        adapter.onClick { _, movieId ->
            val destination = CategoryFragmentDirections.actionCategoryFragmentToHomeDetailFragment()
            view.findNavController().navigate(destination)
            sharedViewModel.movieId(movieId)
        }
    }

    private fun onBindObserver() {
        sharedViewModel.titleActionBar.observe(viewLifecycleOwner) {
            (requireActivity() as MainActivity).setTitleActionBar(it)
        }
        sharedViewModel.category.observe(viewLifecycleOwner) {
            when (it) {
                "trending" -> {
                    viewModel.trendingPage.observe(viewLifecycleOwner) { list ->
                        adapter.submitData(viewLifecycleOwner.lifecycle, list)
                    }
                }
                Constant.CAT_UPCOMING -> {
                    viewModel.upcomingPage.observe(viewLifecycleOwner) { list ->
                        adapter.submitData(viewLifecycleOwner.lifecycle, list)
                    }
                }
                Constant.CAT_NOW_PLAYING -> {
                    viewModel.nowPlayingPage.observe(viewLifecycleOwner) { list ->
                        adapter.submitData(viewLifecycleOwner.lifecycle, list)
                    }
                }
                Constant.CAT_TOP_RATED -> {
                    viewModel.topRatedPage.observe(viewLifecycleOwner) { list ->
                        adapter.submitData(viewLifecycleOwner.lifecycle, list)
                    }
                }
                Constant.CAT_POPULAR -> {
                    viewModel.popularPage.observe(viewLifecycleOwner) { list ->
                        adapter.submitData(viewLifecycleOwner.lifecycle, list)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}