package com.example.basemodule.base
import android.app.ProgressDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.basemodule.R
import com.example.basemodule.base.base.BaseViewModel


abstract class BaseFragment<BD:ViewDataBinding,VM: BaseViewModel>: Fragment() {
    protected lateinit var binding: BD
    protected lateinit var viewModel:VM

     //lateinit var viewModelFactory: ViewModelProvider.Factory

    private var progressDialog: ProgressDialog? = null
    private var errorDialog: AlertDialog?= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater!!,getLayoutID(),container,false)
        binding.lifecycleOwner =viewLifecycleOwner
        return  binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


       viewModel = ViewModelProvider(this).get(getViewModelClass()!!)

        arguments?.let {
            viewModel.handleIntent(it)
        }
        progressDialog = ProgressDialog(context)
        progressDialog!!.setMessage("Loading...")

        var dialog = AlertDialog.Builder(this!!.context!!)
        var view = layoutInflater.inflate(R.layout.customdialog_layout,null)
        var btnRetry = view.findViewById(R.id.btnRetry) as Button
        btnRetry.setOnClickListener {
           // refresh()
            var f: FragmentTransaction = requireFragmentManager().beginTransaction()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                f.detach(this).commitNow();
            }
            f.detach(this).attach(this).commit()

            errorDialog!!.dismiss()

        }

        dialog?.setView(view)
        errorDialog =  dialog.create()




        viewModel!!.isLoading.observe(
           viewLifecycleOwner,
            Observer { isLoading: Boolean ->
                if (isLoading) {
                    progressDialog!!.show()
                    errorDialog!!.hide()
                } else {
                    progressDialog!!.dismiss()
                    errorDialog!!.hide()
                }
            }
        )
        viewModel!!.error.observe(
            viewLifecycleOwner,
            Observer { error: Throwable? ->
                if (error != null) {
                    errorDialog!!.setMessage(error.message)
                    errorDialog!!.show()
                }
                else errorDialog!!.hide()
            }
        )
        init()


    }
    protected abstract fun getLayoutID(): Int
    protected abstract fun getViewModelClass(): Class<VM>?

    protected open fun init(){}
    protected open fun refresh(){

    }



}