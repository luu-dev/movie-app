package com.example.android_3.repository

interface Mapper<in data,out uidata> {
    fun mapfrom(item : data):uidata
}