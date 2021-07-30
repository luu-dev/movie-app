package com.example.android_3.ui.listmovie

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.android_3.model.viewitem.MovieItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ListMovieDataSource(
    val viewModel: ListMovieViewModel,
    val pageType: PageType,
    val query: String? = null
) : PageKeyedDataSource<Int, MovieItem>() {

    companion object{
        var page = 1
    }
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieItem>
    ) {
        pageType.let { listPageType ->
            viewModel.disposable.add(
                viewModel.getMovies(pageType = pageType, page = 1, query = query)
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { data ->
                            val nextPage =
                                if (data.totalPage!! > data.page!!.plus(1)) data.page?.plus(1) else null
                            //callback.onResult(data.movies!!, null, nextPage)
                            callback.onResult(data.movies!!, null, page+1)

                            Log.d("paging", "loadInitial: "+data)

                        }
                        , { t: Throwable? ->
                            viewModel.error.postValue(t)
                        }

                    )
            )
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieItem>) {
        pageType?.let { listPageType ->
            viewModel.disposable.add(
                viewModel.getMovies(pageType = pageType, page  = params.key, query = query)
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { data ->
                            val nextPage =
                                if (data.totalPage!! > data.page!!.plus(1)) data.page?.plus(1) else null
                           // callback.onResult(data.movies!!, nextPage)
                            callback.onResult(data.movies!!, params.key+1)

                            Log.d("paging1", "loadAfter: "+data)

                        }
                        , { t: Throwable? ->
                            viewModel.error.postValue(t)
                        }

                    )
            )
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieItem>) {
        TODO("Not yet implemented")
    }


}