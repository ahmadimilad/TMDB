package com.tmdb.ui.details

import androidx.lifecycle.viewModelScope
import com.tmdb.base.BaseViewModel
import com.tmdb.domain.common.Mapper
import com.tmdb.domain.common.Resource
import com.tmdb.domain.entities.MovieEntity
import com.tmdb.domain.usecases.GetMovieDetailsUseCase
import com.tmdb.model.MovieUiModel
import com.tmdb.utils.error_handler.GlobalErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val globalErrorHandler: GlobalErrorHandler,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val movieMapper: Mapper<MovieEntity, MovieUiModel>
) : BaseViewModel<MovieDetailsContract.Event, MovieDetailsContract.State, MovieDetailsContract.Effect>() {

    override fun createInitialState(): MovieDetailsContract.State {
        return MovieDetailsContract.State(
            moviesState = MovieDetailsContract.MoviesState.Idle
        )
    }

    override fun handleEvent(event: MovieDetailsContract.Event) {
        when (event) {
            is MovieDetailsContract.Event.OnFetchMovieDetails -> {
                fetchMovieDetails(event.movie)
            }
        }
    }

    private fun fetchMovieDetails(movie: MovieUiModel) {
        viewModelScope.launch {
            getMovieDetailsUseCase(movie.id)
                .onStart { emit(Resource.Loading) }
                .collect {
                    when (it) {
                        is Resource.Loading -> {
                            setState { copy(moviesState = MovieDetailsContract.MoviesState.Loading) }
                        }
                        is Resource.Empty -> {
                            setState { copy(moviesState = MovieDetailsContract.MoviesState.Idle) }
                        }
                        is Resource.Success -> {
                            setState { copy(moviesState = MovieDetailsContract.MoviesState.Success(movie = movieMapper.from(it.data))) }
                        }
                        is Resource.Error -> {
                            setEffect { MovieDetailsContract.Effect.ShowError(message = globalErrorHandler.getErrorMessage(it.exception)) }
                        }
                    }
                }
        }
    }
}