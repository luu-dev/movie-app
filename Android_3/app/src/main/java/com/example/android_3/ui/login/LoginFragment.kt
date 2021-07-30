package com.example.android_3.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.android_3.ui.Activity.MainActivity
import com.example.android_3.R
import com.example.android_3.databinding.LoginfragmentBinding
import com.example.android_3.ui.BaseFragment1
import com.example.android_3.ui.Activity.HomeActivity
import com.github.ybq.android.spinkit.style.FadingCircle


class LoginFragment : BaseFragment1<LoginfragmentBinding, LoginRegisterViewModel>(), LoginListener,
    View.OnTouchListener {
    lateinit var loginRegisterViewModel: LoginRegisterViewModel

    lateinit var sharedPref:SharedPreferences

    private var resetDialog: AlertDialog?= null

    companion object{
        val ID = "ID"
        val password = "password"
        val checked ="checked"
        val dataLogin ="dataLogin"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun getLayoutID(): Int {
        return R.layout.loginfragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.progress.indeterminateDrawable = FadingCircle()
        binding.progress.visibility= View.INVISIBLE

        var animation = binding.layoutlogin.background as AnimationDrawable
        animation.run {
            setEnterFadeDuration(3000)
            setExitFadeDuration(5000)
            start()
        }

        binding.checkbox.isChecked = false
        loginRegisterViewModel = ViewModelProviders.of(this).get(
            LoginRegisterViewModel::class.java
        )

        loginRegisterViewModel.userLiveData.observe(viewLifecycleOwner, Observer {
            //if (it != null) Toast.makeText(context, "User create", Toast.LENGTH_SHORT).show()
        }

        )


        sharedPref = activity?.getSharedPreferences(dataLogin,Context.MODE_PRIVATE) ?: return

        binding.edtID.editText!!.setText(sharedPref.getString("ID",""))
        binding.edtpassword.editText!!.setText(sharedPref.getString("password",""))
        binding.checkbox.isChecked = sharedPref.getBoolean("checked",false)



        Log.e(  "text",sharedPref.getString(ID,"").toString())
        binding.edtpassword.editText!!.setText(sharedPref.getString(password,""))

        binding.setListener(this)
//        binding.edtID.editText!!.addTextChangedListener(onTextChanged =  {
//                text,_,_,_ -> if (text=="")   binding.edtID.error ="not allowed empty"
//
//        })
        val args: LoginFragmentArgs by navArgs()
        
        val email = args.id
        val password = args.password
        if (email !="@null" && password !="@null" && email!=null && password!=null) {
            binding.edtID.editText!!.setText(email)
            binding.edtpassword.editText!!.setText(password)
        }
        else {

            binding.edtID.editText!!.setText(sharedPref.getString(Companion.ID, ""))
            binding.edtpassword.editText!!.setText(sharedPref.getString(Companion.password, ""))
        }


    }
    fun remmemberuser(){
        sharedPref = activity?.getSharedPreferences(dataLogin,Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            if (binding.checkbox.isChecked==true) {
                putString(ID, binding.edtID.editText!!.text.toString())
                putString(password, binding.edtpassword.editText!!.text.toString())
                putBoolean(checked, true)
                apply()
            }
            else if(binding.checkbox.isChecked==false) {

                remove(ID)
                remove(password)
                remove(checked)
                apply()
            }
        }
    }
    override fun onLoginClicked() {
        remmemberuser()
        binding.progress.visibility = View.VISIBLE

        val email: String = binding.edtID.editText!!.text.toString()
        var password = binding.edtpassword.editText!!.text.toString()

        if (email == "")
        {binding.edtID.error = "not allow empty"
            binding.progress.visibility = View.INVISIBLE}else binding.edtID.error = null
        if (password == "")
        {binding.edtpassword.error =
            "not allow empty"
            binding.progress.visibility = View.INVISIBLE
        } else binding.edtpassword.error = null
        if (email.length > 0 && password.length > 0) {

            binding.edtID.isErrorEnabled = false
            binding.edtpassword.isErrorEnabled = false
            viewModel.login(
               email,password
            )
            loginRegisterViewModel.checkdialog.observe(this, Observer {
                if (it == true) {
                    var main: MainActivity = activity as MainActivity
                    main?.let {
                       findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMovieFragment())
                    }
                    loginRegisterViewModel.getauth().checkdialog.removeObservers(this)
                }
                Handler().postDelayed(
                    Runnable {
                        binding.progress.visibility = View.GONE
                    }, 1500
                )
            })
        }
    }




    override fun onRegisterClicked(v: View) {
        binding.signupReg.setOnTouchListener(this)
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        Navigation.findNavController(v).navigate(action)
    }

    override fun onResetPassword() {
        var dialog = AlertDialog.Builder(requireContext())
        var view = LayoutInflater.from(requireContext()).inflate(R.layout.reset_password_dialog,null)
        var email = view.findViewById(R.id.edtResetEmail) as EditText
        var btnReset = view.findViewById(R.id.btnReset) as Button
        var btnCancle = view.findViewById(R.id.btnCancle) as Button
        btnReset.setOnClickListener {
                if (email.text.toString().isEmpty())
                    Toast.makeText(context," input email",Toast.LENGTH_LONG).show()
                else
                    viewModel.resetPassword(email =email!!.text.toString())
        }
        btnCancle.setOnClickListener {
           resetDialog?.dismiss()
        }
        dialog.setView(view)
        resetDialog=dialog.create()

        resetDialog!!.show()

    }

    override fun getViewModelClass(): Class<LoginRegisterViewModel> {
        return LoginRegisterViewModel::class.java
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                return false
    }


}