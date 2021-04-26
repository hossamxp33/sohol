//package com.codesroots.mac.cards.presentaion.Bill
//
//import com.codesroots.mac.cards.databinding.BillLayoutBinding
//
//
//import android.content.Intent
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import androidx.databinding.DataBindingUtil
//import com.codesroots.mac.cards.R
//import com.codesroots.mac.cards.databinding.ActivityPaymentBinding
//import com.codesroots.mac.cards.models.Buypackge
//import com.codesroots.mac.cards.presentaion.MainActivity
//
//
//class BillFragment : AppCompatActivity() {
//
//    private var woyouService: IWoyouService? = null
//
//    var dataa: Buypackge? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.bill_layout)
//        val binding = DataBindingUtil.setContentView<BillLayoutBinding>(this, R.layout.bill_layout)
//
//        var extras = intent.extras
//        val value = extras?.getParcelable<Buypackge>("myobj")
//        if (!value!!.center!!.packageCodes.isNullOrEmpty()) {
//
//            value!!.center!!.packageCodes!!.forEach {
//
//                numbervalue.append(it.code)
//                numbervalue.append("\n")
//                numbervalue.text.toString()
//                serialvalue.append(it.id)
//                serialvalue.append("\n")
//                serialvalue.text.toString()
//            }
//
//        }
//        binding.buy = value.center
//    }
//
//    override fun onBackPressed() {
//        super.onBackPressed()
//        finish()
//        val homeIntent = Intent(this, MainActivity::class.java)
//        startActivity(homeIntent)
//    }
//}