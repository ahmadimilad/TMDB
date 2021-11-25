package com.tmdb.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.tmdb.base.BaseRecyclerAdapter
import com.tmdb.databinding.RowMovieItemLayoutBinding
import com.tmdb.model.MovieUiModel

class MovieAdapter constructor(
    private val clickFunc: ((MovieUiModel?) -> Unit)? = null
) : BaseRecyclerAdapter<MovieUiModel, RowMovieItemLayoutBinding, MovieViewHolder>(MovieItemDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = RowMovieItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding = binding, click = clickFunc)
    }
}

class MovieItemDiffUtil : DiffUtil.ItemCallback<MovieUiModel>() {
    override fun areItemsTheSame(oldItem: MovieUiModel, newItem: MovieUiModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieUiModel, newItem: MovieUiModel): Boolean {
        return oldItem == newItem
    }
}