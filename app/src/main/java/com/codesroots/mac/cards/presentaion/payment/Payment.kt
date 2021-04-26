package com.codesroots.mac.cards.presentaion.payment

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil


import com.codesroots.mac.cards.databinding.ReceiptBinding
import com.codesroots.mac.cards.models.Buypackge
import com.codesroots.mac.cards.presentaion.MainActivity

import kotlinx.android.synthetic.main.activity_payment.*
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.R




class Payment : AppCompatActivity() {

    var  text : TextView ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.codesroots.mac.cards.R.layout.receipt)
        val binding = DataBindingUtil.setContentView<ReceiptBinding>(this, com.codesroots.mac.cards.R.layout.receipt)
        var extras = intent.extras


      val    value = extras?.getParcelable<Buypackge>("myobj")
      val total = extras?.getSerializable("total")
         binding.paidAmount.text = total.toString()
      //  animation()
       binding.buy = value

        binding.saveBtn.setOnClickListener {
                    val homeIntent = Intent(this, BookedSuccessful::class.java)
                                       startActivity(homeIntent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun animation(){
        val ttb = AnimationUtils.loadAnimation(this, com.codesroots.mac.cards.R.anim.ttb)
         text!!.animation = ttb

    }
}