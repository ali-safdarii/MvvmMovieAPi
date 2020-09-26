package com.example.moviemvvm.data.api

import com.example.moviemvvm.data.data_model.MovieDetails
import com.example.moviemvvm.data.data_model.MovieResponse
import com.example.moviemvvm.data.data_model.now_playing.NowPlaying
import com.example.moviemvvm.data.data_model.top_rated.TopRated
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {


    // https://api.themoviedb.org/3/movie/popular?api_key=7aa34f6554cb9c7f8a9bcfd358cd31dc&page=1
    // https://api.themoviedb.org/3/movie/299534?api_key=6e63c2317fbe963d76c3bdc2b785f6d1
    // https://api.themoviedb.org/3/


    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails>
    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page: Int): Single<MovieResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovie(): Single<TopRated>

    //now_playing
    @GET("movie/now_playing")
    fun getNowPlaying(): Single<NowPlaying>




}