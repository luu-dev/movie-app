package com.example.android_3.ui.Activity

import android.os.Bundle
import android.view.View
import com.example.android_3.R
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.android_3.adapter.SlidePageAdapter
import com.example.android_3.api.ApiBuilder
import com.example.android_3.api.urlApi
import com.example.android_3.databinding.HomeActivityBinding
import com.example.android_3.model.Movie
import kotlin.collections.ArrayList
import java.util.*
import retrofit2.Call

import retrofit2.Callback

import retrofit2.Response


class HomeActivity : AppCompatActivity(){

    private lateinit var binding: HomeActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.home_activity)



    }




}