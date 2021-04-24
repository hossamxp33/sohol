package com.codesroots.mac.cards.presentaion.changepassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.codesroots.mac.cards.presentaion.mainfragment.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.change_password.view.*
import kotlinx.android.synthetic.main.change_pw.view.*


class changePassword : Fragment()  {
    lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {

        val view= inflater.inflate(com.codesroots.mac.cards.R.layout.change_pw, container, false)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        view.changepwbtn.setOnClickListener {
            Toast.makeText(context, view.new_name.text.toString(), Toast.LENGTH_SHORT).show()
            Toast.makeText(context, view.currentPw.text.toString(), Toast.LENGTH_SHORT).show()
            Toast.makeText(context, view.newPW.text.toString(), Toast.LENGTH_SHORT).show()
            Toast.makeText(context, view.ConfirmPW.text.toString(), Toast.LENGTH_SHORT).show()

            if (view.newPW.text.toString() == view.ConfirmPW.text.toString()) {
                viewModel.ChangePassword(view.newPW.text.toString())

                viewModel.EditResponseLD?.observe(this, Observer {
                    if (it.success == true) {
                        Toast.makeText(context, "تم التغيير", Toast.LENGTH_SHORT).show()


                    }

                })
            }else {

                Toast.makeText(context, "غير متطابق", Toast.LENGTH_SHORT).show()

            }
        }

        return view
    }

}
