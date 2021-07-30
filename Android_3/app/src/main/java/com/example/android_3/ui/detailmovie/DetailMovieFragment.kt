package com.example.android_3.ui.detailmovie

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.example.android_3.R
import com.example.android_3.adapter.SlidePageAdapter
import com.example.android_3.databinding.FragmentMovieDetailBinding
import com.example.android_3.databinding.ItemcastBinding
import com.example.android_3.databinding.TrailerlayoutBinding
import com.example.android_3.databinding.VideoBinding
import com.example.android_3.generated.callback.OnClickListener
import com.example.android_3.model.Genre
import com.example.android_3.model.viewitem.CastItem
import com.example.android_3.model.viewitem.MovieItem
import com.example.android_3.model.viewitem.Video
import com.example.android_3.model.viewitem.VideoItem
import com.example.android_3.ui.movie.MovieItemClick
import com.example.android_3.ui.movie.MovieListener
import com.example.android_3.ui.playvideo.PlayerVideoActivity
import com.example.basemodule.base.BaseFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class DetailMovieFragment : BaseFragment<FragmentMovieDetailBinding, MovieDetailViewModel>(),
    DetailMovieListener, MovieItemClick, View.OnClickListener {
    companion object{
        var movie_id = "";
    }
    override fun getLayoutID(): Int {
        return R.layout.fragment_movie_detail
    }

    override fun getViewModelClass(): Class<MovieDetailViewModel>? {
        return MovieDetailViewModel::class.java
    }

    override fun init() {

        super.init()
        viewModel.getAllMovieDetail()
        viewModel.detail_.observe(viewLifecycleOwner) {
            setview(it)



        }

        binding.layoutSimilarMovies.textViewTitle.text = "SimilarMovie"
        binding.layoutSimilarMovies.imageButtonMore.visibility = View.GONE
//        binding.playvideo.setOnClickListener {
//            findNavController().navigate(DetailMovieFragmentDirections.actionDetailMovieFragmentToPlayerVideoFragment())
//        }
        binding.playvideo.setOnClickListener(this)

    }


    private fun setview(it: DetailMovieState) {
        binding.detail = it
        binding.executePendingBindings()

        setlayoutViewPager(it.getSimilarMovie().movies, binding.layoutSimilarMovies.viewPager)
        setCast(it.getCastItem())
        setGenre(it.getMovieDetail().genres)
        setVideo(it.getVideo())

    }

    private fun setlayoutViewPager(list: List<MovieItem>?, v: ViewPager) {
        SlidePageAdapter(requireContext(), SlidePageAdapter.ITEM_TYPE.SMALL).apply {
            mList = list as ArrayList<MovieItem>
            listener = this@DetailMovieFragment
            v.adapter = this
        }

    }

    private fun setGenre(list: List<Genre>?) {
        binding.chipGroupGenres.apply {
            for (i in list!!) {
                val chip = Chip(context)
                chip.text = i.name
                addView(chip)
            }
        }
    }

    private fun setCast(list: List<CastItem>) {
        binding.linearLayoutCastContent.removeAllViews()
        for (cast in list) {
            val castItem = DataBindingUtil.inflate<ItemcastBinding>(
                LayoutInflater.from(requireContext()),
                R.layout.itemcast,
                null,
                false
            )
            castItem.cast = cast
            binding.linearLayoutCastContent.addView(castItem.root)
        }
    }

    private fun setVideo(list: List<VideoItem>) {
        binding.layoutTrailers.trailers.removeAllViews()
        if (list.isEmpty())
            binding.layoutTrailers.apply {
                textTrailer.visibility = View.GONE
                trailers.visibility = View.GONE
                trailersContainer.visibility = View.GONE
            }
        else {
            binding.layoutTrailers.apply {
                textTrailer.visibility = View.VISIBLE
                trailers.visibility = View.VISIBLE
                trailersContainer.visibility = View.VISIBLE
                for (video in list) {
                    var videoItem = DataBindingUtil.inflate<VideoBinding>(
                        LayoutInflater.from(requireContext()), R.layout.video,
                        null, false
                    )
                    videoItem.videoItem = video
                    videoItem.listener = this@DetailMovieFragment
                    binding.layoutTrailers.trailers.addView(videoItem.root)

                }
            }


        }

    }


    override fun videoClick(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        activity?.startActivity(intent)
    }

    override fun movieItemClick(movieItem: MovieItem) {
        var id = movieItem.id
       // movie_id = id!!;
        viewModel.movieid = id!!
        viewModel.getAllMovieDetail()
        Toast.makeText(context, id.toString(), Toast.LENGTH_LONG).show()
    }

    override fun onClick(v: View?) {
        //  findNavController().navigate(DetailMovieFragmentDirections.actionDetailMovieFragmentToPlayerVideoFragment())
        val intent = Intent(activity, PlayerVideoActivity::class.java)
        movie_id=  viewModel.movieid.toString()
        intent.putExtra("movie_id",movie_id)
        Toast.makeText(context, movie_id.toString(),Toast.LENGTH_LONG).show()
        activity?.startActivity(intent)
    }

}