package com.example.android_3.model

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


open class AuthRepository(private val application: Application) {
    private val firebaseAuth: FirebaseAuth
    val userLiveData: MutableLiveData<FirebaseUser>
    val loggedOutLiveData: MutableLiveData<Boolean>
    val checkdialog : MutableLiveData<Boolean>


    fun login(email: String?, password: String?) {

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                application.mainExecutor,
                OnCompleteListener { task ->
                    if (task.isSuccessful) {
                        userLiveData.postValue(firebaseAuth.currentUser)
                        checkdialog.value = true
                    } else {
                        Toast.makeText(
                            application.applicationContext,
                            "Login Failure: " + task.exception!!.message,
                            Toast.LENGTH_SHORT
                        ).show()

                        checkdialog.value = false

                    }
                })
    }

    fun register(email: String?, password: String?) {

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                application.mainExecutor,
                OnCompleteListener { task ->
                    if (task.isSuccessful) {
                        userLiveData.postValue(firebaseAuth.currentUser)
                        checkdialog.value = true
                    } else {
                        Toast.makeText(
                            application.applicationContext,
                            "Registration Failure: " + task.exception!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        checkdialog.value = false

                    }

                })
    }
    fun  resetPassword(email: String?){
        firebaseAuth!!.sendPasswordResetEmail(email)!!.addOnCompleteListener {
            if (it.isSuccessful) Toast.makeText(application.applicationContext,"check your email",Toast.LENGTH_LONG).show()
            else Toast.makeText(application.applicationContext,it.exception!!.message,Toast.LENGTH_LONG).show()
        }
    }

    fun gettrue() {
         checkdialog.value = true
    }
    fun getfalse(){
        checkdialog.value=false

    }

    fun logOut() {
        firebaseAuth.signOut()
        loggedOutLiveData.postValue(true)
    }

    init {
        firebaseAuth = FirebaseAuth.getInstance()
        userLiveData = MutableLiveData()
        loggedOutLiveData = MutableLiveData()
        checkdialog= MutableLiveData()
        checkdialog.value=false


        if (firebaseAuth.currentUser != null) {
            userLiveData.postValue(firebaseAuth.currentUser)
            loggedOutLiveData.postValue(false)
        }


    }
}