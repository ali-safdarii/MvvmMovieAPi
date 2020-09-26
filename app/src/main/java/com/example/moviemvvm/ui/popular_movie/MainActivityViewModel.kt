package com.example.moviemvvm.ui.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.moviemvvm.data.data_source.NetworkState
import com.example.moviemvvm.data.data_model.Movie
import com.example.moviemvvm.data.data_model.top_rated.ResultTopRating
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel(private val movieRepository: MoviePagedListRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val moviePagedList: LiveData<PagedList<Movie>> by lazy {

        movieRepository.fetchMoviePagedList(compositeDisposable)

    }



    val topRatedMovie: LiveData<List<ResultTopRating>> by lazy {

        movieRepository.fetchTopRatedMovie(compositeDisposable)

    }


    val networkState: LiveData<NetworkState> by lazy {


        movieRepository.getNetworkState()
    }


    fun listIsEmpty(): Boolean {


        return moviePagedList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}