package com.example.moviemvvm.utilis

import POSTER_BASE_URL
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.moviemvvm.R

@BindingAdapter("img_url")
fun loadImage(imageView: ImageView, url: String?) {
    val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(R.drawable.poster_placeholder)
    Glide.with(imageView.context).load(POSTER_BASE_URL+url).apply(requestOptions).into(imageView)

}