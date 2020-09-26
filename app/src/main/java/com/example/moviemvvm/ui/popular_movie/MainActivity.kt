package com.example.moviemvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.azoft.carousellayoutmanager.CenterScrollListener
import com.example.moviemvvm.data.api.ApiService
import com.example.moviemvvm.data.data_model.top_rated.ResultTopRating
import com.example.moviemvvm.data.data_source.NetworkState
import com.example.moviemvvm.slider.Slider
import com.example.moviemvvm.slider.SliderPagerAdapter
import com.example.moviemvvm.ui.popular_movie.MainActivityViewModel
import com.example.moviemvvm.ui.popular_movie.MoviePagedListRepository
import com.example.moviemvvm.ui.popular_movie.PopularAdapter
import com.example.moviemvvm.utilis.CenterZoomLayoutManager
import com.github.nitrico.lastadapter.LastAdapter


import kotlinx.android.synthetic.main.activity_main.*
import com.rd.PageIndicatorView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {


    private lateinit var viewModel: MainActivityViewModel

    lateinit var movieRepository: MoviePagedListRepository
    private lateinit var slides: ArrayList<Slider>


    private val INTERVAL_DURATION = 5000L
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val apiService: ApiService = TheMovieDBClient.getClient()
        movieRepository = MoviePagedListRepository(apiService)
        viewModel = getViewModel()
        sliderShow()



       val movieAdapter = PopularAdapter(this)


        rv_movie_list.setHasFixedSize(true)
        rv_movie_list.layoutManager =  LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_movie_list.adapter = movieAdapter
        movieAdapter.notifyDataSetChanged()


      viewModel.moviePagedList.observe(this, Observer {
            movieAdapter.submitList(it)

        })

      viewModel.networkState.observe(this, Observer {
            progress_bar_popular.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_popular.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!viewModel.listIsEmpty()) {
                movieAdapter.setNetworkState(it)
            }
        })



    viewModel.topRatedMovie.observe(this, Observer {


         val centerZoomLayoutManager=CenterZoomLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
            rv_top_rating.layoutManager =
                centerZoomLayoutManager
            rv_top_rating.setHasFixedSize(true)

            LastAdapter(it, BR.item)
                .map<ResultTopRating>(R.layout.top_rating_item)
                .into(rv_top_rating)

        val  t=it.forEach { res-> res.toString() }
        Log.d("TopRating",t.toString())


        })
    }

    private fun sliderShow() {
        slides = ArrayList()

        slides.add(Slider(R.drawable.slide1, "Slide Title /more..."))
        slides.add(Slider(R.drawable.slide2, "Slide Title /more..."))
        slides.add(Slider(R.drawable.slide1, "Slide Title /more..."))
        slides.add(Slider(R.drawable.slide2, "Slide Title /more..."))
        slides.add(Slider(R.drawable.slide1, "Slide Title /more..."))


        val pageIndicatorView = findViewById<PageIndicatorView>(R.id.pageIndicatorView)

        val sliderPagerAdapter = SliderPagerAdapter(this, slides)

        slider_pager.adapter = sliderPagerAdapter



        pageIndicatorView.count = sliderPagerAdapter.count
        pageIndicatorView.selection = 1


        val subscribe = Observable.interval(INTERVAL_DURATION, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (slider_pager.currentItem < sliderPagerAdapter.count - 1) {
                    slider_pager.currentItem = slider_pager.currentItem + 1
                } else {
                    slider_pager.currentItem = 0
                }
            }
        compositeDisposable.add(subscribe)
    }


    private fun getViewModel(): MainActivityViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(movieRepository) as T
            }
        })[MainActivityViewModel::class.java]
    }
}
