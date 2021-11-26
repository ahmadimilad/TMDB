package com.tmdb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tmdb.R
import com.tmdb.base.BaseFragment
import com.tmdb.databinding.FragmentHomeBinding
import com.tmdb.ui.home.adapter.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: MainViewModel by viewModels()
    private val adapter: MovieAdapter by lazy {
        MovieAdapter { movie ->
            HomeFragmentDirections.actionHomeFragmentToDetailFragment(movie).also { action ->
                findNavController().let { navController ->
                    if (navController.currentDestination?.id == R.id.homeFragment) {
                        navController.navigate(action)
                    }
                }
            }
        }
    }

    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun prepareView(savedInstanceState: Bundle?) {
        binding.rvMovies.adapter = adapter
        initObservers()

        // Fetch only once
        if (viewModel.currentState.moviesState is HomeContract.MoviesState.Idle) {
            viewModel.setEvent(HomeContract.Event.OnFetchMovies)
        }

        binding.btnRetry.setOnClickListener {
            viewModel.setEvent(HomeContract.Event.OnFetchMovies)
            binding.prLoading.isVisible = true
            visibleErrorBox(false)
        }
    }

    /**
     * Initialize Observers
     */
    private fun initObservers() {
        viewModel.uiState.asLiveData(Dispatchers.IO).observe(viewLifecycleOwner) {
            when (val state = it.moviesState) {
                is HomeContract.MoviesState.Idle -> {
                    binding.prLoading.isVisible = false
                }
                is HomeContract.MoviesState.Loading -> {
                    binding.prLoading.isVisible = true
                    visibleErrorBox(false)
                }
                is HomeContract.MoviesState.Success -> {
                    val data = state.movies
                    adapter.submitList(data)
                    binding.prLoading.isVisible = false
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.effect.asLiveData(Dispatchers.IO).observe(viewLifecycleOwner) {
                when (it) {
                    is HomeContract.Effect.ShowError -> {
                        if (adapter.currentList.size > 0) return@observe

                        binding.prLoading.isVisible = false
                        visibleErrorBox(true)

                        binding.tvErrorMessage.text = it.message
                    }
                }
            }
        }
    }

    private fun visibleErrorBox(isShow: Boolean) {
        binding.ivErrorIcon.isVisible = isShow
        binding.tvErrorMessage.isVisible = isShow
        binding.btnRetry.isVisible = isShow
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//    }
}