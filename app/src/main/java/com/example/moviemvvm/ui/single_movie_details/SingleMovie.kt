package com.example.moviemvvm.ui.single_movie_details

import POSTER_BASE_URL
import TheMovieDBClient
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.moviemvvm.R
import com.example.moviemvvm.data.api.ApiService
import com.example.moviemvvm.data.data_model.MovieDetails
import com.example.moviemvvm.data.data_model.now_playing.Result
import com.example.moviemvvm.data.data_source.NetworkState
import com.example.moviemvvm.utilis.CenterZoomLayoutManager
import com.github.nitrico.lastadapter.BR
import com.github.nitrico.lastadapter.LastAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_single_movie.*


class SingleMovie : AppCompatActivity() {

    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieRepository: MovieDetailsRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)

        val movieId: Int = intent.getIntExtra("id", 1)

        val apiService: ApiService = TheMovieDBClient.getClient()
        movieRepository = MovieDetailsRepository(apiService)

        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this, Observer {
            test_progress_bar.visibility =
                if (it == NetworkState.LOADING) View.VISIBLE else View.GONE

            if (test_progress_bar.visibility == View.GONE)
                detail_movie_title.visibility = View.VISIBLE

            //txt_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (it == NetworkState.ERROR)
                Snackbar
                    .make(snackBarContainer, "Something wrong...", Snackbar.LENGTH_LONG).show()

        })



       viewModel.nowPlaying.observe(this, Observer {


            val centerZoomLayoutManager =
                CenterZoomLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            rv_nowPlaying.layoutManager =
                centerZoomLayoutManager
            rv_nowPlaying.setHasFixedSize(true)

            LastAdapter(it, BR.item)
                .map<Result>(R.layout.item_now_playing)
                .into(rv_nowPlaying)

           it.forEach { i -> Log.d("Image",POSTER_BASE_URL+i.posterPath) }


        })

    }

    fun bindUI(it: MovieDetails) {

        detail_movie_title.text = it.title
        detail_movie_desc.text = getString(R.string.details_des)


        val moviePosterURL = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(details_MovieImageView)

        val movieposterurl2 = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(movieposterurl2)
            .into(detail_movie_img)

        YoYo.with(Techniques.SlideInDown)
            .duration(700)
            .repeat(0)
            .playOn(detail_movie_img)

        ratingBar.rating= it.rating.toFloat()


    }


    private fun getViewModel(movieId: Int): SingleMovieViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleMovieViewModel(movieRepository, movieId) as T
            }
        })[SingleMovieViewModel::class.java]
    }
}
