package com.tmdb.ui.home.adapter

import com.tmdb.base.BaseViewHolder
import com.tmdb.databinding.RowMovieItemLayoutBinding
import com.tmdb.model.MovieUiModel

class MovieViewHolder constructor(
    private val binding: RowMovieItemLayoutBinding,
    private val click: ((MovieUiModel?) -> Unit)? = null
) : BaseViewHolder<MovieUiModel, RowMovieItemLayoutBinding>(binding) {
    init {
        binding.root.setOnClickListener {
            click?.invoke(getRowItem())
        }
    }

    override fun bind() {
        getRowItem()?.let {
            binding.item = it
            binding.executePendingBindings()
        }
    }
}