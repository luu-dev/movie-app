package com.example.android_3.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.example.android_3.BR
import com.example.android_3.R
import com.example.android_3.databinding.MovieitemLayoutBinding
import com.example.android_3.databinding.MovieitemlargeLayoutBinding
import com.example.android_3.model.viewitem.MovieItem

class SlidePageAdapter(context: Context, private val itemType: ITEM_TYPE) : PagerAdapter() {
    var mList: ArrayList<MovieItem> = ArrayList()
        get() = field
        set(value) {
            field = value
        }
     var listener:Listener?=null
         set(value) {
            field=value
        }



    var inflater: LayoutInflater = LayoutInflater.from(context)
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return if (mList == null) 0 else mList!!.size
    }



    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object`as View?)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = if (itemType == ITEM_TYPE.SMALL) DataBindingUtil.inflate<MovieitemLayoutBinding>(inflater, R.layout.movieitem_layout, container, false)
        else DataBindingUtil.inflate<MovieitemlargeLayoutBinding>(inflater, R.layout.movieitemlarge_layout, container, false)
        binding.setVariable(BR.movie, mList[position])


        if (listener !=null)
            binding.setVariable(BR.listener,listener)
        binding.executePendingBindings()

        container.addView(binding.root)



        return binding.root


    }
    enum class ITEM_TYPE {
        SMALL,
        LARGE
    }
    override fun getPageWidth(position: Int): Float {
        return if (itemType == ITEM_TYPE.SMALL) 0.29f else 1.0f
    }
    open interface  Listener{}




}