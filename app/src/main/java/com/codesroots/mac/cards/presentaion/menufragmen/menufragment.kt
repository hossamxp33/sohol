package com.codesroots.mac.cards.presentaion.menufragmen

import android.content.Intent
import com.codesroots.mac.cards.R
import com.codesroots.mac.cards.presentaion.MainActivity
import com.codesroots.mac.cards.presentaion.login.LoginActivity
import com.codesroots.mac.cards.presentaion.payment.Payment
import kotlinx.android.synthetic.main.menu_fragment.view.*


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.codesroots.mac.cards.DataLayer.helper.PreferenceHelper
import com.codesroots.mac.cards.DataLayer.usecases.checkUserLogin
import kotlinx.android.synthetic.main.contact_fragment.*
import kotlinx.android.synthetic.main.contact_fragment.view.*
import kotlinx.android.synthetic.main.menu_fragment.*
import org.jetbrains.anko.support.v4.makeCall
import android.net.Uri




class MenuFragment : Fragment() {

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,  savedInstanceState: Bundle? ): View? {

        val view= inflater.inflate(com.codesroots.mac.cards.R.layout.menu_fragment, container, false)

//        view.login.setOnClickListener {
//            val homeIntent = Intent(context, LoginActivity::class.java)
//            ( context as MainActivity).startActivity(homeIntent)
//        }
//        view.partners.setOnClickListener {
//            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.main_frame,Partners())?.addToBackStack("login")?.commit()
//        }
//
//        view.profile.setOnClickListener {
//            if (checkUserLogin(context!!))
//                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.main_frame,ContactFragment())?.addToBackStack("login")?.commit()
//        }
        view.profile.setOnClickListener {
            val phone = "07721499299"
            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
            startActivity(intent)
            }
//
//        view.favoffers.setOnClickListener {
//            if (checkUserLogin(context!!))
//                activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.main_frame,TermsFragment())?.addToBackStack(null)?.commit()
//        }
   //     view.profile.setOnClickListener { v -> makeCall(profile.text.toString()) }

        view.logout.setOnClickListener {
            if (checkUserLogin(context!!)) {
                PreferenceHelper.setToken("",context)
                PreferenceHelper.setUserGroupId(0)
                PreferenceHelper.setUserId(0)

                Toast.makeText(context, "تم تسجيل خروجك", Toast.LENGTH_SHORT).show()

                val homeIntent = Intent(context, LoginActivity::class.java)
                ( context as MainActivity).startActivity(homeIntent)
            }else {
                val homeIntent = Intent(context, LoginActivity::class.java)
                ( context as MainActivity).startActivity(homeIntent)

            }
        }

        return view
    }

}
