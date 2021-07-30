package com.example.android_3.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android_3.api.ApiBuilder
import com.example.android_3.api.RemoteApi
import com.example.android_3.model.viewitem.MovieListItem
import com.example.android_3.repository.MovieRepository
import com.example.basemodule.base.base.BaseViewModel
import io.reactivex.rxkotlin.Observables

class MovieViewModel(  ) : BaseViewModel() {
    val respo =  MovieRepository(ApiBuilder.getClient())
    val combine =
        { t1: MovieListItem, t2: MovieListItem, t3: MovieListItem -> MovieState(t1, t2, t3) }

    private val liveDataMovie = MutableLiveData<MovieState>()
    val liveDataMovie_: LiveData<MovieState> = liveDataMovie


    fun getMovieAll() {
           return Observables.zip(
                respo.getMovie(MovieRepository.TYPEMOVIE.POPULAR),
                respo.getMovie(MovieRepository.TYPEMOVIE.NOW_PLAYING),
                respo.getMovie(MovieRepository.TYPEMOVIE.UPCOMING),
                combine
            ).sendRequest {
               liveDataMovie.value = it
           }
}
}