package com.example.android_3.repository

import com.example.android_3.api.RemoteApi
import com.example.android_3.api.urlApi
import com.example.android_3.model.Movie
import com.example.android_3.model.response.CastResponse
import com.example.android_3.model.response.MovieDetailResponse
import com.example.android_3.model.response.MovieResponse
import com.example.android_3.model.response.VideoResponse
import com.example.android_3.model.viewitem.*
import io.reactivex.Observable

class DetailMovieRepository (val api:RemoteApi) {
    val remote= MovieRepository.RemoteRepository(api)


    inner class  DetailMovieRes():Mapper<MovieDetailResponse,MovieDetailItem>{
        override fun mapfrom(item: MovieDetailResponse): MovieDetailItem {
            return  MovieDetailItem(id = item.id, title = item.title, backdropPath = urlApi.getBackDrop(item.backdropPath), genres = item.genres, imagePath = urlApi.getPosterPath(item.posterPath), overview = item.overview, popularity = item.popularity, releaseDate = item.releaseDate, runtime = "${item.runtime} min", status = item.status, voteAverage = item.voteAverage?.toFloat()?.div(2))
        }
    }


    fun getDetailMovie(movieid: Int):Observable<MovieDetailItem> =  remote.getMovieDetail(movieid).map { DetailMovieRes().mapfrom(it) }

    inner class CastRes():Mapper<CastResponse,List<CastItem>>{
        override fun mapfrom(item: CastResponse): List<CastItem> {
            return item.cast!!.map {
                cast -> CastItem(
                    name = cast.name,
                    character = cast.character,
                    profilePath = urlApi.getProfilePath(cast.profilePath)
            )
            }
        }
    }

    fun getCast(movieid:Int):Observable<List<CastItem>>{
        return remote.getCast(movieid).map{CastRes().mapfrom(it)}
    }

    inner class SimilarMovie():Mapper<MovieResponse,MovieListItem>{
        override fun mapfrom(item: MovieResponse): MovieListItem {
            return MovieListItem(
                page = item.page,
                totalPage = item.totalPages,
                movies = item.results!!.map {
                    movie-> MovieItem(
                    id = movie.id,
                    imagePath = urlApi.getPosterPath(movie.posterPath) ,
                    title  = movie.title
                )
                }
            )
        }


    }

    fun getSimilarMovie(movieid: Int): Observable<MovieListItem> = MovieRepository(api).getMovie(typemovie = MovieRepository.TYPEMOVIE.SIMILAR,page = 1,movieId = movieid)

    inner class VideoMovie():Mapper<VideoResponse,List<VideoItem>>{
        override fun mapfrom(item: VideoResponse): List<VideoItem> {
            return item.results.map {
                it->
                VideoItem(
                url = urlApi.getUrlVideo(it), thumbUrl = urlApi.getThumbUrl(it)
            )
            }
        }
    }

    fun getVideo(movieid: Int):Observable<List<VideoItem>> =  remote.getVideo(movieid).map { VideoMovie().mapfrom(it) }


}