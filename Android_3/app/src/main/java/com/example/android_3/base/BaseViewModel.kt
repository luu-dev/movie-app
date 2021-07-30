package com.example.android_3.base

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel

open class BaseViewModel(@NonNull application: Application?) : AndroidViewModel(application!!) {
}