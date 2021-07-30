package com.example.android_3.ui.movie


import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.observe
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.example.android_3.R
import com.example.android_3.adapter.SlidePageAdapter
import com.example.android_3.databinding.FragmentMovieBinding
import com.example.android_3.model.viewitem.MovieItem
import com.example.android_3.ui.listmovie.PageType
import com.example.basemodule.base.BaseFragment
import java.util.*
import kotlin.collections.ArrayList

class MovieFragment : BaseFragment<FragmentMovieBinding, MovieViewModel>(), MovieListener,
    MovieItemClick {
    var slideTimer: Timer? = null

    companion object {
        var lstCount: Int? = 0
        var cancle = false
    }

    override fun getLayoutID(): Int {
        return R.layout.fragment_movie
    }

    override fun getViewModelClass(): Class<MovieViewModel>? {
        return MovieViewModel::class.java
    }

    override fun init() {
        super.init()
        viewModel.getMovieAll()
        viewModel.liveDataMovie_.observe(viewLifecycleOwner) {
            setView(it)
        }
        binding.layoutPopularMovies.textViewTitle.text = "Popular"
        binding.layoutUpComingMovies.textViewTitle.text = "Upcoming"
        binding.listener = this

    }


    private fun setView(it: MovieState) {
        setSmallItem(it.getpopularMoive().movies, binding.layoutPopularMovies.viewPager)
        setSmallItem(it.getupComingrMoive().movies, binding.layoutUpComingMovies.viewPager)
        setLargeItem(it.getnowPlayingrMoive().movies)

    }


    private fun setSmallItem(movies: List<MovieItem>?, papger: ViewPager) {
        SlidePageAdapter(requireContext(), SlidePageAdapter.ITEM_TYPE.SMALL).apply {
            mList = movies as ArrayList<MovieItem>
            this.listener = this@MovieFragment
            papger.adapter = this
        }
        binding.layoutPopularMovies.imageButtonMore.setOnClickListener {
            findNavController().navigate(
                MovieFragmentDirections.actionMovieFragmentToMovieListFragment(
                    type = PageType.POPULAR
                )
            )

        }
        binding.layoutUpComingMovies.imageButtonMore.setOnClickListener {
            findNavController().navigate(
                MovieFragmentDirections.actionMovieFragmentToMovieListFragment(
                    type = PageType.UPCOMING
                )
            )


        }

    }


    private fun setLargeItem(movies: List<MovieItem>?) {
        SlidePageAdapter(requireContext(), SlidePageAdapter.ITEM_TYPE.LARGE).apply {
            mList = movies as ArrayList<MovieItem>
            binding.viewpagerNowplaying.adapter = this
            lstCount = mList.size
            listener = this@MovieFragment
        }
        binding.viewpagerNowplaying.apply {
            pageMargin = 60
            setPageTransformer(false, BasicViewPagerTransformation())
            currentItem = movies!!.size / 2


        }
        setAutoTab()
    }


    inner class BasicViewPagerTransformation : ViewPager.PageTransformer {
        override fun transformPage(page: View, position: Float) {
            val absPosition = Math.abs(position)
            if (absPosition >= 1) {
                page.scaleY = 0.85f
            } else {
                page.scaleY = (0.85f - 1) * absPosition + 1
            }
        }
    }

    fun setAutoTab() {
        slideTimer = Timer()
       // slideTimer!!.schedule(AutoSlide(), 4000, 10000)

        slideTimer?.scheduleAtFixedRate(AutoSlide(), 4000, 10000)
        binding!!.tabSlide.setupWithViewPager(binding.viewpagerNowplaying, true)


    }


    inner class AutoSlide : TimerTask() {

        override fun run() {

            activity?.runOnUiThread(Runnable {
                run {
                    if (binding.viewpagerNowplaying.currentItem < MovieFragment.lstCount!! - 1) {
                        binding.viewpagerNowplaying.setCurrentItem(binding.viewpagerNowplaying.currentItem + 1)
                    } else binding.viewpagerNowplaying.setCurrentItem(0)
                }
            })
//            Handler(Looper.getMainLooper()).post(object:Runnable{
//                override fun run() {
//
//                }
//            })

        }


    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun searchMovieClick() {

        val extra =
            FragmentNavigatorExtras(binding.cardViewToolbarContent to binding.cardViewToolbarContent.transitionName)

        findNavController().navigate(R.id.action_movieFragment_to_searchFragment, null, null, extra)
    }

    override fun movieItemClick(movieItem: MovieItem) {
        findNavController().navigate(movieItem.id?.let {
            MovieFragmentDirections.actionMovieFragmentToDetailMovieFragment(
                movieId = it
            )
        }!!)

    }

    override fun onDestroy() {
        super.onDestroy()
        if (slideTimer != null) {
            slideTimer?.cancel()
            slideTimer = null
        }
    }
}





