package com.example.android_3.model.response

import com.example.android_3.model.viewitem.Video
import com.google.gson.annotations.SerializedName

class VideoResponse (
    @SerializedName("results") val results:List<Video>
)