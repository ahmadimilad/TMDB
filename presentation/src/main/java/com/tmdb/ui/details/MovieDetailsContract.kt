package com.tmdb.ui.details

import com.tmdb.base.UiEffect
import com.tmdb.base.UiEvent
import com.tmdb.base.UiState
import com.tmdb.model.MovieUiModel

class MovieDetailsContract {
    sealed class Event : UiEvent {
        data class OnFetchMovieDetails(val movie: MovieUiModel) : Event()
    }

    sealed class Effect : UiEffect {
        data class ShowError(val message: String?) : Effect()
    }

    data class State(val moviesState: MoviesState) : UiState

    sealed class MoviesState {
        object Idle : MoviesState()
        object Loading : MoviesState()
        data class Success(val movie: MovieUiModel) : MoviesState()
    }
}