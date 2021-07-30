package com.example.android_3.ui.listmovie

import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.example.android_3.R
import com.example.android_3.base.BaseViewModel
import com.example.android_3.databinding.ListmoviefragmentBinding
import com.example.android_3.model.viewitem.MovieItem
import com.example.basemodule.base.BaseFragment

class MovieListFragment: BaseFragment<ListmoviefragmentBinding,ListMovieViewModel>() {

    val adapter:ListPageApdater by lazy{ListPageApdater()}
    override fun getLayoutID(): Int {
        return R.layout.listmoviefragment
    }

    override fun getViewModelClass(): Class<ListMovieViewModel>? {
        return ListMovieViewModel::class.java
    }

    override fun init() {
        super.init()
        viewModel.getListMove().observe(viewLifecycleOwner,::movieload)
        viewModel.liveState_.observe(viewLifecycleOwner,::setState)
        setListener()
        viewModel.isLoading.value = false

        binding.recyclerview.adapter = adapter
        (binding.recyclerview.layoutManager as GridLayoutManager).spanSizeLookup =  adapter.spanSizeLookup
    }


    private  fun movieload(list:PagedList<MovieItem>){
            adapter.submitList(list)
    }
    private fun setState(state: ListMovieState){


        binding.state = state
        binding.executePendingBindings()
    }
    private  fun setListener(){
        binding.imageButton.setOnClickListener { activity?.onBackPressed() }
        adapter.onMovieItemClick =::movieitemclick
    }
    fun movieitemclick(movie:MovieItem){
        findNavController().navigate(MovieListFragmentDirections.actionMovieListFragmentToDetailMovieFragment(movieId = movie.id!!))
    }



}