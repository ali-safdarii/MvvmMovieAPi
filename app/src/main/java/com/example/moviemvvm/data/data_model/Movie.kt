package com.example.moviemvvm.data.data_model


import com.google.gson.annotations.SerializedName

data class Movie(
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val title: String
)