package com.example.android_3.repository

import com.example.android_3.api.RemoteApi
import com.example.android_3.api.urlApi
import com.example.android_3.model.response.CastResponse
import com.example.android_3.model.response.MovieDetailResponse
import com.example.android_3.model.response.MovieResponse
import com.example.android_3.model.response.VideoResponse
import com.example.android_3.model.viewitem.MovieItem
import com.example.android_3.model.viewitem.MovieListItem
import io.reactivex.Observable

class MovieRepository(api: RemoteApi) : Mapper<MovieResponse, MovieListItem> {

    enum class TYPEMOVIE{
        POPULAR,
        NOW_PLAYING,
        SIMILAR,
        UPCOMING
    }



    open  class RemoteRepository(private val api: RemoteApi){

        fun getPopularMovie(page: Int):Observable<MovieResponse> = api.getPopularMovies(page)
        fun getUpcomingMovie(page: Int): Observable<MovieResponse> = api.getUpComingMovies(page)
        fun getNowPlayingMoive(page: Int): Observable<MovieResponse> = api.getNowPlayingMovies(page)
        fun getSimilarMovie(movieid:Int):Observable<MovieResponse> = api.getSimilarMovies(movieid)
        fun getCast(movieid:Int):Observable<CastResponse> = api.getMovieCredits(movieid)
        fun getVideo(movieid: Int):Observable<VideoResponse> = api.getVideo(movieid)
        fun getMovieDetail(movieid: Int):Observable<MovieDetailResponse> = api.getMovieDetail(movieid)
        fun getMovieSearch(query:String,page: Int):Observable<MovieResponse> = api.searchMovie(query,page)

    }

    private val remote = RemoteRepository(api)




    override fun mapfrom(item: MovieResponse): MovieListItem {
        return MovieListItem(
            page = item.page,
            totalPage = item.totalPages,
            movies = item.results?.map { movie ->
                MovieItem(
                    id = movie.id,
                    imagePath = urlApi.getPosterPath(movie.posterPath) ,
                    title  = movie.title
                )
            }

        )
    }


    fun getMovie( typemovie:TYPEMOVIE,page: Int = 1, movieId: Int = 0) : Observable<MovieListItem>{
            return  when(typemovie){
                TYPEMOVIE.NOW_PLAYING -> remote.getNowPlayingMoive(page).map { mapfrom(it) }
                TYPEMOVIE.POPULAR -> remote.getPopularMovie(page).map { mapfrom(it) }
                TYPEMOVIE.UPCOMING -> remote.getUpcomingMovie(page).map { mapfrom(it) }
                TYPEMOVIE.SIMILAR -> remote.getSimilarMovie(movieId).map { mapfrom(it) }

            }
    }
    fun searchMovie(query: String,page: Int=1) = remote.getMovieSearch(query,page).map { mapfrom(it) }
}


