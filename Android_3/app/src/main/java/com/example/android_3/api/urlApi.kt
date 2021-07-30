package com.example.android_3.api

import com.example.android_3.model.viewitem.Video
import java.net.URI

class urlApi {
    companion object {
        val BASE_URL = "https://172ff945a1e5.ngrok.io/"
        val BASE_POSTER_PATH = BASE_URL+"images"
       // val BASE_URL = "https://api.themoviedb.org/3/"
        //val KEY = "7110422e82d3a45a61db16eaa75f3810"
        val KEY = "luuquangchau"
        val site ="YouTube"
        val YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v=%1\$s"
        val YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/%1\$s/0.jpg"
        val BASR_BACKDROP_PATH = BASE_URL+"images"
        val PROFILE_PATH =BASE_URL+"images"

        val BASE_URL_HLS =""
        fun getUrlHls(url:String)= BASE_URL_HLS + url


        open fun getPosterPath(posterPath: String?): String? {
            return BASE_POSTER_PATH + posterPath
        }

        fun getBackDrop(path:String?):String?{
            return  BASR_BACKDROP_PATH + path
        }

        fun getProfilePath(path: String?):String?{
            return PROFILE_PATH + path
        }
        open fun getUrlVideo(video:Video):String? {
            if(site.equals(video.site))
                        return String.format(YOUTUBE_VIDEO_URL,video.key)
            return  null
        }

        open fun getThumbUrl(video: Video):String?{
            if(site.equals(video.site))
                        return  String.format(YOUTUBE_THUMBNAIL_URL,video.key)
            return null
        }
    }


}