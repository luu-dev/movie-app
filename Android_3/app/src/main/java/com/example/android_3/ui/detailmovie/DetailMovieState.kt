package com.example.android_3.ui.detailmovie

import android.view.View
import com.example.android_3.model.viewitem.*

class DetailMovieState(
   private val movieDetailItem: MovieDetailItem,
   private val castItem:List<CastItem>,
   private val similarMovie:MovieListItem,
   private val video :List<VideoItem>
)
{
    fun getMovieDetail():MovieDetailItem = movieDetailItem
    fun getCastItem():List<CastItem> = castItem
    fun getSimilarMovie():MovieListItem = similarMovie
    fun getVideo():List<VideoItem> =  video
    fun getSimilarMovieVisibility():Int = if (similarMovie.movies!!.isNotEmpty()) View.VISIBLE else View.GONE
    fun CastVisibility():Int = if (castItem!!.isNotEmpty()) View.VISIBLE else View.GONE
    fun VideoVisibility():Int = if (video!!.isNotEmpty()) View.VISIBLE else View.GONE
}