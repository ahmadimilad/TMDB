package com.tmdb.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.tmdb.base.BaseViewModel
import com.tmdb.domain.common.Mapper
import com.tmdb.domain.common.Resource
import com.tmdb.domain.entities.MovieEntity
import com.tmdb.domain.usecases.GetMoviesUseCase
import com.tmdb.model.MovieUiModel
import com.tmdb.utils.error_handler.GlobalErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val globalErrorHandler: GlobalErrorHandler,
    private val moviesUseCase: GetMoviesUseCase,
    private val movieMapper: Mapper<MovieEntity, MovieUiModel>
) : BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>() {

    override fun createInitialState(): HomeContract.State {
        return HomeContract.State(
            moviesState = HomeContract.MoviesState.Idle,
            selectedMovie = null
        )
    }

    override fun handleEvent(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.OnFetchMovies -> {
                fetchMovies()
            }
            is HomeContract.Event.OnMovieItemClicked -> {
                val item = event.movie
                setSelectedMovie(movie = item)
            }
        }
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            moviesUseCase(1)
                .onStart { emit(Resource.Loading) }
                .collect {
                    when (it) {
                        is Resource.Loading -> {
                            setState { copy(moviesState = HomeContract.MoviesState.Loading) }
                        }
                        is Resource.Empty -> {
                            setState { copy(moviesState = HomeContract.MoviesState.Idle) }
                        }
                        is Resource.Success -> {
                            val movies = movieMapper.fromList(it.data)
                            setState { copy(moviesState = HomeContract.MoviesState.Success(movies = movies)) }
                        }
                        is Resource.Error -> {
                            setEffect { HomeContract.Effect.ShowError(message = globalErrorHandler.getErrorMessage(it.exception)) }
                        }
                    }
                }
        }
    }

    private fun setSelectedMovie(movie: MovieUiModel?) {
        setState { copy(selectedMovie = movie) }
    }
}