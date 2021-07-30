package com.example.android_3.ui.Activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.WindowManager
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity

import androidx.databinding.DataBindingUtil
import com.example.android_3.databinding.SplashActivityBinding
import android.view.animation.AnimationUtils;

import com.example.android_3.R


class SplashActivity : AppCompatActivity() {
    lateinit var binding: SplashActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.splash_activity)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            );
        }
        var topAnimation = AnimationUtils.loadAnimation(this,R.anim.slide_down)
        var bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_up)
        var middleAnimation = AnimationUtils.loadAnimation(this,R.anim.fade_in)

        binding.appTitle.animation= middleAnimation
        binding.appDesc.animation= middleAnimation

        binding.image1.animation= topAnimation
        binding.image2.animation= topAnimation
        binding.image3.animation= topAnimation

        binding.image4.animation= bottomAnimation
        binding.image5.animation= bottomAnimation
        binding.image6.animation= bottomAnimation





        var i =  Intent(this, MainActivity::class.java)
        val timer: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(3000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {
                    startActivity(i)
                    finish()
                }
            }
        }
        timer.start()
    }


    }






