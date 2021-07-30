package com.example.basemodule.base.base

import android.app.ProgressDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

abstract class BaseActivity<BD : ViewDataBinding, VM : BaseViewModel> :
    AppCompatActivity() {
    private var progressDialog: ProgressDialog? = null
    private var errorDialog: AlertDialog? = null
    protected var binding: BD? = null
    protected var viewModel: VM? = null
    private var permissionCallback: PermissionCallback? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        viewModel = ViewModelProvider(this)[viewModelClass!!]
        progressDialog = ProgressDialog(this)
        progressDialog!!.setMessage("Loading...")
        errorDialog = AlertDialog.Builder(this).create()
        viewModel!!.isLoading.observe(
            this,
            Observer { isLoading: Boolean ->
                if (isLoading) {
                    progressDialog!!.show()
                } else {
                    progressDialog!!.dismiss()
                }
            }
        )
        viewModel!!.error.observe(
            this,
            Observer { error: Throwable? ->
                if (error != null) {
                    errorDialog!!.setMessage(error.message)
                    errorDialog!!.show()
                }
            }
        )
    }

    protected abstract val viewModelClass: Class<VM>?

    @get:LayoutRes
    protected abstract val layoutId: Int
    protected fun checkPermission(
        p: Array<String>,
        callback: PermissionCallback
    ) {
        if (checkPermission(p, true)) {
            callback.onGranted()
        } else {
            permissionCallback = callback
        }
    }

    private fun checkPermission(
        permission: Array<String>,
        withRequest: Boolean
    ): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (p in permission) {
                if (checkSelfPermission(p) != PackageManager.PERMISSION_GRANTED) {
                    if (withRequest) {
                        requestPermissions(permission, 0)
                    }
                    return false
                }
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (checkPermission(permissions, false)) {
            permissionCallback!!.onGranted()
        } else {
            permissionCallback!!.onDenied()
        }
    }
}