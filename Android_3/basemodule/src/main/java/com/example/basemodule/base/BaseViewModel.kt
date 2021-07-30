package com.example.basemodule.base.base

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers


import kotlin.jvm.Throws


abstract class BaseViewModel : ViewModel() {
    val disposable = CompositeDisposable()

    val isLoading = MutableLiveData<Boolean>()
    val error = MutableLiveData<Throwable?>()

    fun Disposable.track() = disposable.add(this)

    open fun handleIntent(extras: Bundle) {}
    inline fun <T> Observable<T>.sendRequest(
        crossinline succes: (T) -> Unit
    ) {
        isLoading.postValue(true)
        error.postValue(null)

        subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { data ->
                    succes(data)
                },
                { throwable ->
                    isLoading.postValue(false)
                    error.postValue(throwable)

                },
                {
                    isLoading.postValue(false)
                }
        ).track()


    }
    open fun turnOffLoading(){
        isLoading.postValue(false)
    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }


}