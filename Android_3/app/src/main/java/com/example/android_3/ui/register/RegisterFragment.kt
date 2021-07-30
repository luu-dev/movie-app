package com.example.android_3.ui.register

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.android_3.R
import com.example.android_3.databinding.LoginfragmentBinding
import com.example.android_3.databinding.RegisterfragmentBinding
import com.example.android_3.model.AuthRepository
import com.example.android_3.ui.BaseFragment1
import com.example.android_3.ui.login.LoginListener
import com.example.android_3.ui.login.LoginRegisterViewModel
import com.github.ybq.android.spinkit.style.FadingCircle
import kotlinx.coroutines.delay

class RegisterFragment : BaseFragment1<RegisterfragmentBinding, LoginRegisterViewModel>(),
RegisterListener {
    lateinit var loginRegisterViewModel: LoginRegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun getLayoutID(): Int {
        return R.layout.registerfragment
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.progressRegister.indeterminateDrawable = FadingCircle()
        binding.progressRegister.visibility= View.INVISIBLE

        var animation = binding.layoutlogin.background as AnimationDrawable
        animation.run {
            setEnterFadeDuration(3000)
            setExitFadeDuration(5000)
            start()
        }


        loginRegisterViewModel = ViewModelProviders.of(this).get(
            LoginRegisterViewModel::class.java
        )

        loginRegisterViewModel.userLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
              //  Toast.makeText(context, "User create", Toast.LENGTH_SHORT).show()

            }



        }
        )

        binding.setListener(this)
    }

    override fun getViewModelClass(): Class<LoginRegisterViewModel> {
        return LoginRegisterViewModel::class.java
    }

    override fun startRegisterClick(v: View) {
        var check:Boolean = false

        val email: String = binding.edtID.editText!!.text.toString()
        var password = binding.edtpassword.editText!!.text.toString()

        if (email == "") binding.edtID.error = "not allow empty" else binding.edtID.error=null
        if (password == "") binding.edtpassword.error = "not allow empty" else binding.edtpassword.error=null

        if (email.length > 0 && password.length > 0) {

            binding.progressRegister.visibility= View.VISIBLE

            binding.edtID.isErrorEnabled = false
            binding.edtpassword.isErrorEnabled = false
            loginRegisterViewModel.register(email, password)


            loginRegisterViewModel.checkdialog.observe(this, Observer {
                if (it ==true) {
                    findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment(email, password))
                    loginRegisterViewModel.getauth().checkdialog.removeObservers(this)
                    Toast.makeText(context, "User create", Toast.LENGTH_SHORT).show()
                }
                Handler().postDelayed(
                    Runnable {
                        binding.progressRegister.visibility= View.GONE

                    },1500
                )
            })


        }


    }

    override fun cancleRegisterClick(v: View) {
        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment("", "")
        Navigation.findNavController(v).navigate(action)
    }
}