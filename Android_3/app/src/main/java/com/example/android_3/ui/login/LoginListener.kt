package com.example.android_3.ui.login

import android.view.View

interface LoginListener {
    fun onLoginClicked()
    fun onRegisterClicked(v: View)
    fun onResetPassword()
}