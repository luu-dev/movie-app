package com.example.android_3.ui.movie

import com.example.android_3.model.viewitem.MovieListItem

class MovieState(
private  val popularMoive:MovieListItem,
private  val nowPlayingrMoive:MovieListItem,
private  val upComingrMoive:MovieListItem
)
{
    fun getpopularMoive():MovieListItem = popularMoive
    fun getnowPlayingrMoive():MovieListItem = nowPlayingrMoive
    fun getupComingrMoive():MovieListItem = upComingrMoive


}