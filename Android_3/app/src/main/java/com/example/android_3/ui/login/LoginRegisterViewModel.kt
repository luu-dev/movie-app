package com.example.android_3.ui.login

import android.app.Application
import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android_3.base.BaseViewModel
import com.example.android_3.model.AuthRepository
import com.google.firebase.auth.FirebaseUser

class LoginRegisterViewModel(application: Application) : BaseViewModel(application)
    {
    private val authAppRepository: AuthRepository
    val userLiveData: MutableLiveData<FirebaseUser>
        val checkdialog:MutableLiveData<Boolean>
        fun getauth(): AuthRepository {
            return this.authAppRepository
        }

        fun login(email: String?, password: String?) {
        authAppRepository.login(email, password)
    }

    fun register(email: String?, password: String?) {
        authAppRepository.register(email, password)
    }

     fun resetPassword(email: String?){
         authAppRepository!!.resetPassword(email)
     }


    init {
        authAppRepository = AuthRepository(application!!)
        userLiveData = authAppRepository.userLiveData
        checkdialog=authAppRepository.checkdialog
    }

}