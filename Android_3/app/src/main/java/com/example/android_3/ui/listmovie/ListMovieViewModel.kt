package com.example.android_3.ui.listmovie

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.android_3.api.ApiBuilder
import com.example.android_3.model.response.MovieResponse
import com.example.android_3.model.viewitem.MovieItem
import com.example.android_3.model.viewitem.MovieListItem
import com.example.android_3.repository.MovieRepository
import com.example.basemodule.base.base.BaseViewModel
import com.example.basemodule.base.models.BaseModel
import io.reactivex.Observable

class ListMovieViewModel: BaseViewModel() {
    val res = MovieRepository(ApiBuilder.getClient())
    var  dataSource:ListMovieDataSource?=null
    private  val liveState = MutableLiveData<ListMovieState>()
    val liveState_:LiveData<ListMovieState> = liveState

    override fun handleIntent(extras: Bundle) {
        super.handleIntent(extras)
        var arg = MovieListFragmentArgs.fromBundle(extras)
        this.liveState.value = ListMovieState(arg.type,arg.query)

    }
    init {
        isLoading.value = true
    }

    fun getMovies(pageType:PageType,page:Int,query:String?=null):Observable<MovieListItem>{
        return when(pageType){
            PageType.POPULAR -> res.getMovie(typemovie = MovieRepository.TYPEMOVIE.POPULAR,page =  page)
            PageType.UPCOMING ->res.getMovie(typemovie = MovieRepository.TYPEMOVIE.UPCOMING,page = page)
            PageType.SEARCH->res.searchMovie(query=query!!,page = page)
        }

    }

    fun getListMove():LiveData<PagedList<MovieItem>>{
        val config = PagedList.Config.Builder()
            .setPageSize(10)
            .setInitialLoadSizeHint(10)
            .setPrefetchDistance(5)
            .setEnablePlaceholders(true)
            .build()
        return  getFactory(config).build()

    }
    fun getFactory(config:PagedList.Config): LivePagedListBuilder<Int,MovieItem>{
        val factory =  object :DataSource.Factory<Int,MovieItem>(){
            override fun create(): DataSource<Int, MovieItem> {
                    dataSource = ListMovieDataSource(
                        this@ListMovieViewModel,liveState.value!!.getPageType(),liveState.value!!.getQuery()
                    )
                return dataSource!!
            }
        }
        return LivePagedListBuilder<Int,MovieItem>(factory,config)
    }

}