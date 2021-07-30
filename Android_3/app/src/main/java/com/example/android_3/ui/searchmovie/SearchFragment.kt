package com.example.android_3.ui.searchmovie

import android.app.Activity
import android.content.Context
import android.os.Build
import android.transition.TransitionInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import com.example.android_3.R
import com.example.android_3.databinding.SearchfragmentBinding
import com.example.android_3.ui.listmovie.PageType
import com.example.basemodule.base.BaseFragment
import com.example.basemodule.base.base.BaseViewModel

class SearchFragment :BaseFragment<SearchfragmentBinding,SearchViewModel>(),SearchListener{
    override fun getLayoutID(): Int {
        return  R.layout.searchfragment
    }

    override fun getViewModelClass(): Class<SearchViewModel>? {
       return  SearchViewModel::class.java

    }

    override fun init() {
        super.init()
        context?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            sharedElementEnterTransition = TransitionInflater.from(it).inflateTransition(android.R.transition.move)
        }
            setupSearchView()
            binding.listener= this
        }
    }

    private fun setupSearchView() {
        binding.searchView.submit{
                query-> findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToMovieListFragment(query = query,
            type = PageType.SEARCH

        ))

        }


    }





    fun SearchView.submit(clicked:(String?)->Unit ){
        setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                    clicked(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    fun View.showKeyboard(activity: Activity) {
        val inputManager: InputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    override fun backclick() {
        findNavController().popBackStack()
    }
    override fun onStart() {
        super.onStart()
        binding.searchView.showKeyboard(requireActivity())
        binding.searchView.onActionViewExpanded()
    }



}