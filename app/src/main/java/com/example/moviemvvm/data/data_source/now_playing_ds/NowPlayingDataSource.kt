package com.example.moviemvvm.data.data_source.now_playing_ds

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviemvvm.data.api.ApiService
import com.example.moviemvvm.data.data_model.now_playing.Result
import com.example.moviemvvm.data.data_source.NetworkState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class NowPlayingDataSource(
    private val apiService: ApiService,
   private val compositeDisposable: CompositeDisposable
) {


    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState


    private val _nowPlayingMutable = MutableLiveData<List<Result>>()

    val nowPlayingLiveData: LiveData<List<Result>>
        get() = _nowPlayingMutable



    fun fetchNowPlayingmovie(){
        _networkState.postValue(NetworkState.LOADING)


        try {
            compositeDisposable.add(
                apiService.getNowPlaying()
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _nowPlayingMutable.postValue(it.results)


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