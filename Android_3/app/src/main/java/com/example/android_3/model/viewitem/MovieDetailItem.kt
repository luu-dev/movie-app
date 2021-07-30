package com.example.android_3.model.viewitem

import com.example.android_3.model.Genre

data class MovieDetailItem(
    val id: Int?,
    val backdropPath: String?,
    val genres: List<Genre>?,
    val title: String?,
    val overview: String?,
    val popularity: Double?,
    val imagePath: String?,
    val releaseDate: String?,
    val runtime: String?,
    val status: String?,
    val voteAverage: Float?
){
    fun getTextBottomOfTitle() = "$releaseDate • $runtime"
}