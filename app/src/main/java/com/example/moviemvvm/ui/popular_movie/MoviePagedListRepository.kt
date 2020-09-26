package com.example.moviemvvm.ui.popular_movie

import POST_PER_PAGE
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.moviemvvm.data.api.ApiService
import com.example.moviemvvm.data.data_source.MovieDataSource
import com.example.moviemvvm.data.data_source.MovieDataSourceFactory
import com.example.moviemvvm.data.data_source.NetworkState
import com.example.moviemvvm.data.data_model.Movie
import com.example.moviemvvm.data.data_model.top_rated.ResultTopRating
import com.example.moviemvvm.data.data_source.top_rated.TopRatedDataSource
import io.reactivex.disposables.CompositeDisposable

class MoviePagedListRepository(private val  apiService:ApiService) {

    lateinit var moviePagedList:LiveData<PagedList<Movie>>
    lateinit var movieDataSourceFactory: MovieDataSourceFactory

    lateinit var movieTopRatedDataSource: TopRatedDataSource

    fun fetchMoviePagedList(compositeDisposable: CompositeDisposable):LiveData<PagedList<Movie>>{

        movieDataSourceFactory= MovieDataSourceFactory(apiService,compositeDisposable)

        val config=PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()


        moviePagedList=LivePagedListBuilder(movieDataSourceFactory,config).build()

        return moviePagedList

    }


    fun getNetworkState():LiveData<NetworkState>{

        return Transformations.switchMap<MovieDataSource,NetworkState>(movieDataSourceFactory.movieLiveDataSource,MovieDataSource::networkState)

    }


    fun fetchTopRatedMovie(compositeDisposable: CompositeDisposable): LiveData<List<ResultTopRating>> {

        movieTopRatedDataSource= TopRatedDataSource(apiService,compositeDisposable)

        movieTopRatedDataSource.fetchRated()



        return movieTopRatedDataSource.topMovieResponse


    }







}