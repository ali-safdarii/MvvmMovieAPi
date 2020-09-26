package com.example.moviemvvm.data.data_model.top_rated


import com.google.gson.annotations.SerializedName

data class TopRated(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val resultTopRatings: List<ResultTopRating>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)