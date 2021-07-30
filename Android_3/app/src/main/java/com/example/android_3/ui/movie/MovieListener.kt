package com.example.android_3.ui.movie

import com.example.android_3.adapter.SlidePageAdapter
import com.example.android_3.model.viewitem.MovieItem

interface MovieListener {


    fun searchMovieClick()

}

interface  MovieItemClick:SlidePageAdapter.Listener{
    fun movieItemClick(movieItem: MovieItem)
}