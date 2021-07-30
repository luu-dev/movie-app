package com.example.android_3.model.response

import com.example.android_3.model.Movie
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MovieResponse (
    @SerializedName("page") val page: Int?,
    @SerializedName("total_pages") val totalPages: Int?,
    @SerializedName("results") val results: ArrayList<Movie>?
)