package com.example.moviemvvm.data.data_source.top_rated

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviemvvm.data.api.ApiService
import com.example.moviemvvm.data.data_model.top_rated.ResultTopRating
import com.example.moviemvvm.data.data_source.NetworkState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TopRatedDataSource(private val apiService : ApiService, private val compositeDisposable: CompositeDisposable) {

    private val _networkState  = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState



    private val _topRatedResponse =  MutableLiveData<List<ResultTopRating>>()
    val topMovieResponse: LiveData<List<ResultTopRating>>
        get() = _topRatedResponse

    fun fetchRated() {

        _networkState.postValue(NetworkState.LOADING)


        try {
            compositeDisposable.add(
                apiService.getTopRatedMovie()
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                        _topRatedResponse.postValue(it.resultTopRatings)



                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("MovieDetailsDataSource", it.message)
                        }
                    )
            )

        }

        catch (e: Exception){
            Log.e("MovieDetailsDataSource",e.message)
        }


    }


}