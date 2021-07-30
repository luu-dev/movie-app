package com.example.android_3.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.android_3.R

@BindingAdapter("imageUrl","applyCorner")
fun setImageUrl(imageView: ImageView,url:String?,applyCorner:Boolean){
    if (url.isNullOrEmpty()) return

    Glide
        .with(imageView.context)
        .load(url)
        .placeholder(R.drawable.placeholder)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .apply(RequestOptions().transform(CenterCrop(),RoundedCorners(if (applyCorner) 32 else 1)))
        .into(imageView)
}

@BindingAdapter("circleImageUrl")
fun getCircleImageUrl(img:ImageView,url:String?)
{
    if (url.isNullOrEmpty()) return

    Glide.with(img.context)
        .load(url)
        .circleCrop()
        .placeholder(R.drawable.placeholder)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(img)
}

@BindingAdapter("videoImageUrl")
fun getVideoImageUrl(img: ImageView,url:String?){
    if (url.isNullOrEmpty()) return
    Glide.with(img.context)
        .load(url)
        .placeholder(R.drawable.placeholder)
        .apply(RequestOptions.placeholderOf(R.color.black).centerCrop().override(150,150))
        .into(img)
}

