package com.tmdb.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.tmdb.R

@BindingAdapter("imageUrlWithPlaceHolder")
fun ImageView.setImageUrlWithPlaceHolder(url: String?) {
    GlideApp
        .with(context)
        .load(url)
        .placeholder(R.drawable.ic_cover_place_holder)
        .into(this)
}