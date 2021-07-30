package com.example.android_3.ui.listmovie

class ListMovieState(val type:PageType,val searchQuery:String?) {
    fun getPageType():PageType = type
    fun getQuery():String?= searchQuery

    fun getTypes():String =
        when(type){
            PageType.UPCOMING ->"UPCOMING"
            PageType.POPULAR ->"POPULAR"
            PageType.SEARCH->"SEARCH FOR ${searchQuery}"
        }
}


enum class PageType(){
    POPULAR,UPCOMING,SEARCH
}