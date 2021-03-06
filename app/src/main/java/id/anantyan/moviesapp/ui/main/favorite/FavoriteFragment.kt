package id.anantyan.moviesapp.ui.main.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.anantyan.moviesapp.databinding.FragmentFavoriteBinding
import id.anantyan.moviesapp.ui.main.MainViewModel
import id.anantyan.utils.dividerVertical
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoriteViewModel by viewModels()
    private val sharedViewModel: MainViewModel by activityViewModels()
    @Inject lateinit var adapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBindView(view)
        onBindObserver()
    }

    private fun onBindView(view: View) {
        binding.rvFavorites.setHasFixedSize(true)
        binding.rvFavorites.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavorites.itemAnimator = DefaultItemAnimator()
        binding.rvFavorites.isNestedScrollingEnabled = true
        binding.rvFavorites.addItemDecoration(dividerVertical(requireContext(), 32, 32))
        binding.rvFavorites.adapter = adapter
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        adapter.onClick { _, movieId ->
            val destination = FavoriteFragmentDirections.actionFavoriteFragmentToHomeDetailFragment()
            view.findNavController().navigate(destination)
            sharedViewModel.movieId(movieId)
        }
    }

    private fun onBindObserver() {
        viewModel.getList().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}