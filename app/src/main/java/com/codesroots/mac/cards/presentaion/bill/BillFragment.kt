package com.codesroots.mac.cards.presentaion.bill

import com.codesroots.mac.cards.databinding.BillLayoutBinding


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.codesroots.mac.cards.R
import com.codesroots.mac.cards.databinding.ReceiptBinding
import com.codesroots.mac.cards.models.Buypackge
import com.codesroots.mac.cards.presentaion.MainActivity

import kotlinx.android.synthetic.main.bill_layout.*
import kotlinx.android.synthetic.main.receipt.*


class BillFragment : AppCompatActivity() {


    var dataa: Buypackge? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.receipt)
        val binding = DataBindingUtil.setContentView<ReceiptBinding>(this, R.layout.receipt)

        var extras = intent.extras
        val value = extras?.getParcelable<Buypackge>("myobj")
        if (!value!!.center!!.packageCodes.isNullOrEmpty()) {

            value!!.center!!.packageCodes!!.forEach {

                numbervalue.append(it.code)
                numbervalue.append("\n")
                numbervalue.text.toString()
                expire.append(it.id.toString())
                expire.append("\n")
                expire.text.toString()
            }

        }
        binding.buy = value
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        val homeIntent = Intent(this, MainActivity::class.java)
        startActivity(homeIntent)
    }
}