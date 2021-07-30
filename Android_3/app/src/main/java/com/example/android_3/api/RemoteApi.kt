package com.example.android_3.api

import com.example.android_3.model.response.CastResponse
import com.example.android_3.model.response.MovieDetailResponse
import com.example.android_3.model.response.MovieResponse
import com.example.android_3.model.response.VideoResponse
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call
import retrofit2.http.Path


interface RemoteApi {

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int): Observable<MovieResponse>

    @GET("movie/upcoming")
    fun getUpComingMovies(@Query("page") page: Int): Observable<MovieResponse>

    @GET("movie/now_playing")
    fun getNowPlayingMovies(@Query("page") page: Int): Observable<MovieResponse>

    @GET("movie/{movie_id}/detail")
    fun getMovieDetail(@Path("movie_id") movieId: Int): Observable<MovieDetailResponse>

    @GET("movie/{movie_id}/similar")
    fun getSimilarMovies(@Path("movie_id") movieId: Int): Observable<MovieResponse>

    @GET("movie/{movie_id}/credits")
    fun getMovieCredits(@Path("movie_id") movieId: Int): Observable<CastResponse>

    @GET("movie/search")
    fun searchMovie(@Query("query") query: String, @Query("page") page: Int): Observable<MovieResponse>

    @GET("movie/{movie_id}/videos")
    fun getVideo(@Path("movie_id") movieId: Int):Observable<VideoResponse>

}