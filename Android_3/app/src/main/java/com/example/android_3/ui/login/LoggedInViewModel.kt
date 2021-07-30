package com.example.android_3.ui.login

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.android_3.model.AuthRepository
import com.google.firebase.auth.FirebaseUser


class LoggedInViewModel(@NonNull application: Application?) :
    AndroidViewModel(application!!) {
    private val authRepository: AuthRepository = TODO()
    private val userLiveData: MutableLiveData<FirebaseUser>
    private val loggedOutLiveData: MutableLiveData<Boolean>
    fun logOut() {
        authRepository.logOut()
    }

    fun getUserLiveData(): MutableLiveData<FirebaseUser> {
        return userLiveData
    }

    fun getLoggedOutLiveData(): MutableLiveData<Boolean> {
        return loggedOutLiveData
    }

    init {
        authRepository = AuthRepository(application!!)
        userLiveData = authRepository.userLiveData
        loggedOutLiveData = authRepository.loggedOutLiveData
    }
}