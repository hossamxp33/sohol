package com.codesroots.mac.cards.presentaion.register


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.transition.TransitionInflater
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.codesroots.hossam.mandoobapp.presentation.login.ViewModel.LoginViewModel
import com.codesroots.mac.cards.DataLayer.helper.PreferenceHelper
import com.codesroots.mac.cards.DataLayer.usecases.checkUserLogin
import com.codesroots.mac.cards.R
import com.codesroots.mac.cards.presentaion.MainActivity
import com.codesroots.mac.cards.presentaion.isInternetConnectionAvailable
import com.codesroots.mac.cards.presentaion.login.LoginActivity
import com.codesroots.mac.cards.presentaion.snack
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_signin.*
import kotlinx.android.synthetic.main.beginning_activity.*

class RegisterActivity : AppCompatActivity() {
    var network_enabled = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PreferenceHelper(this)
       setContentView(R.layout.register_activity)
         animation()

        if (checkUserLogin(this))    startActivity(Intent(this  , MainActivity::class.java))

            if (!isInternetConnectionAvailable(this)) "رجاء تأكد من اتصالك بالانترنت".snack(window.decorView.rootView)



    }



    private fun animation(){

        val slide = TransitionInflater.from(this).inflateTransition(R.transition.slide_activity)
        getWindow().setEnterTransition(slide);
    }
}
