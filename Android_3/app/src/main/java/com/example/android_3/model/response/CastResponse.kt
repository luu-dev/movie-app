package com.example.android_3.model.response

import com.example.android_3.model.Cast
import com.google.gson.annotations.SerializedName

class CastResponse
    (
    @SerializedName("cast") val cast: List<Cast>?,
    @SerializedName("id") val id: Int?
)