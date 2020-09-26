package com.example.moviemvvm.ui.single_movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviemvvm.data.data_source.NetworkState
import com.example.moviemvvm.data.data_model.MovieDetails
import com.example.moviemvvm.data.data_model.now_playing.Result

import io.reactivex.disposables.CompositeDisposable

class SingleMovieViewModel (private val movieRepository : MovieDetailsRepository, movieId: Int)  : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val  movieDetails : LiveData<MovieDetails> by lazy {
        movieRepository.fetchSingleMovieDetails(compositeDisposable,movieId)
    }

    val networkState : LiveData<NetworkState> by lazy {
        movieRepository.getMovieDetailsNetworkState()
    }

    val nowPlaying:LiveData<List<Result>> by lazy {

        movieRepository.getNowPlayingMovie(compositeDisposable)

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }



}