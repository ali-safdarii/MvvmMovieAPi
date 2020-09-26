package com.example.moviemvvm.data.data_source

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.moviemvvm.data.api.ApiService
import com.example.moviemvvm.data.data_model.Movie
import io.reactivex.disposables.CompositeDisposable


class MovieDataSourceFactory(
    private val apiService: ApiService,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, Movie>() {

    val movieLiveDataSource = MutableLiveData<MovieDataSource>()
    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(apiService, compositeDisposable)
        movieLiveDataSource.postValue(movieDataSource)

        return movieDataSource
    }
}