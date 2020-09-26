package com.example.moviemvvm.ui.single_movie_details

import androidx.lifecycle.LiveData
import com.example.moviemvvm.data.api.ApiService
import com.example.moviemvvm.data.data_source.MovieDetailsNetworkDataSource
import com.example.moviemvvm.data.data_source.NetworkState
import com.example.moviemvvm.data.data_model.MovieDetails
import com.example.moviemvvm.data.data_model.now_playing.Result
import com.example.moviemvvm.data.data_source.now_playing_ds.NowPlayingDataSource

import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository (private val apiService : ApiService) {

    private lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource
    private lateinit var nowPlayingDataSource:NowPlayingDataSource

    fun fetchSingleMovieDetails (compositeDisposable: CompositeDisposable, movieId: Int) : LiveData<MovieDetails> {

        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieResponse

    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return movieDetailsNetworkDataSource.networkState
    }


    fun getNowPlayingMovie(compositeDisposable: CompositeDisposable) :LiveData<List<Result>>{
        nowPlayingDataSource= NowPlayingDataSource(apiService,compositeDisposable)

        nowPlayingDataSource.fetchNowPlayingmovie()


        return nowPlayingDataSource.nowPlayingLiveData

    }



}