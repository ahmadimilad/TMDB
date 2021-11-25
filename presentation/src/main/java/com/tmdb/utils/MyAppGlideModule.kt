package com.tmdb.utils

import android.content.Context
import android.graphics.drawable.Drawable
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.tmdb.R

@GlideModule
class MyAppGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        var requestOptions = RequestOptions()
        requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)
//        requestOptions = requestOptions.placeholder(R.drawable.ic_cover_place_holder)
        requestOptions = requestOptions.error(R.drawable.ic_cover_place_holder)
        requestOptions = requestOptions.fallback(R.drawable.ic_cover_place_holder)

        val memoryCacheSizeBytes = 1024 * 1024 * 20 // 20mb
        val bitmapPoolSizeBytes = 1024 * 1024 * 30 // 30mb

        builder.setMemoryCache(LruResourceCache(memoryCacheSizeBytes.toLong()))
        builder.setBitmapPool(LruBitmapPool(bitmapPoolSizeBytes.toLong()))

        builder.setDefaultRequestOptions(requestOptions)
        builder.setDefaultTransitionOptions(
            Drawable::class.java,
            DrawableTransitionOptions.withCrossFade()
        )
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}