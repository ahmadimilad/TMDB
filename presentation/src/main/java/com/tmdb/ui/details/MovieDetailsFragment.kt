package com.tmdb.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.tmdb.R
import com.tmdb.base.BaseFragment
import com.tmdb.databinding.FragmentDetailsBinding
import com.tmdb.utils.setImageUrlWithPlaceHolder
import com.tmdb.utils.summery
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<FragmentDetailsBinding>() {
    private val viewModel: MovieDetailsViewModel by viewModels()
    private val args: MovieDetailsFragmentArgs by navArgs()

    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailsBinding
        get() = FragmentDetailsBinding::inflate

    override fun prepareView(savedInstanceState: Bundle?) {
        initObservers()

        if (viewModel.currentState.moviesState is MovieDetailsContract.MoviesState.Idle) {
            args.movie?.let {
                viewModel.setEvent(MovieDetailsContract.Event.OnFetchMovieDetails(it))
            }
        }
    }

    private fun initObservers() {
        viewModel.uiState.asLiveData(lifecycleScope.coroutineContext).observe(viewLifecycleOwner) {
            when (val state = it.moviesState) {
                is MovieDetailsContract.MoviesState.Idle -> {
                }
                is MovieDetailsContract.MoviesState.Loading -> {
                }
                is MovieDetailsContract.MoviesState.Success -> {
                    val data = state.movie

                    binding.ivCover.setImageUrlWithPlaceHolder(getString(R.string.original_image_path_template, data.backdropPath))
                    binding.collapsingToolbar.title = data.title
                    binding.tvOverview.text = data.overview
                    binding.tvVoteAverage.text = data.voteAverage.toString()
                    binding.tvVoteCount.text = data.voteCount.summery()
                }
            }
        }

        viewModel.effect.asLiveData(lifecycleScope.coroutineContext).observe(viewLifecycleOwner) {
            when (it) {
                is MovieDetailsContract.Effect.ShowError -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}