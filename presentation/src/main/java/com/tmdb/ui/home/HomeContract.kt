package com.tmdb.ui.home

import com.tmdb.base.UiEffect
import com.tmdb.base.UiEvent
import com.tmdb.base.UiState
import com.tmdb.model.MovieUiModel

class HomeContract {
    sealed class Event : UiEvent {
        object OnFetchMovies : Event()
        data class OnMovieItemClicked(val movie: MovieUiModel) : Event()
    }

    sealed class Effect : UiEffect {
        data class ShowError(val message: String?) : Effect()
    }

    data class State(
        val moviesState: MoviesState,
        val selectedMovie: MovieUiModel? = null
    ) : UiState

    sealed class MoviesState {
        object Idle : MoviesState()
        object Loading : MoviesState()
        data class Success(val movies: List<MovieUiModel>) : MoviesState()
    }
}