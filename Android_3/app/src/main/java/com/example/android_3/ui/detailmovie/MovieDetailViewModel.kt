package com.example.android_3.ui.detailmovie

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavArgument
import androidx.navigation.fragment.navArgs
import com.example.android_3.api.ApiBuilder
import com.example.android_3.model.response.MovieResponse
import com.example.android_3.model.viewitem.CastItem
import com.example.android_3.model.viewitem.MovieDetailItem
import com.example.android_3.model.viewitem.MovieListItem
import com.example.android_3.model.viewitem.VideoItem
import com.example.android_3.repository.DetailMovieRepository
import com.example.android_3.repository.MovieRepository
import com.example.android_3.ui.login.LoginFragmentArgs
import com.example.basemodule.base.base.BaseViewModel
import io.reactivex.rxkotlin.Observables

class MovieDetailViewModel : BaseViewModel() {

    val respoDetailMovie = DetailMovieRepository(ApiBuilder.getClient())
    var movieid: Int = 0
    override fun handleIntent(extras: Bundle) {
        super.handleIntent(extras)
        val arg = DetailMovieFragmentArgs.fromBundle(extras)
        movieid = arg.movieId
        Log.e("MOVIEID",movieid.toString() )

    }
    private  val detail = MutableLiveData<DetailMovieState>()
    val detail_ :LiveData<DetailMovieState> = detail



    val combine = {
        t1:MovieDetailItem,t2:List<CastItem>,t3:MovieListItem,t4:List<VideoItem> ->DetailMovieState(t1,t2,t3,t4)
    }

        fun getAllMovieDetail(){
                return Observables.zip(
                        respoDetailMovie.getDetailMovie(movieid=movieid),
                        respoDetailMovie.getCast(movieid=movieid),
                        respoDetailMovie.getSimilarMovie(movieid=movieid),
                        respoDetailMovie.getVideo(movieid=movieid),
                        combine
                ).sendRequest {
                        detail.value = it

                }
        }


}