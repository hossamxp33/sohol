package com.codesroots.mac.cards.presentaion.main_menu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ReportFragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.codesroots.mac.cards.R
import com.codesroots.mac.cards.databinding.CompanyDetailsBinding
import com.codesroots.mac.cards.databinding.MainMenuBinding
import com.codesroots.mac.cards.presentaion.MainActivity
import com.codesroots.mac.cards.presentaion.changepassword.changePassword
import com.codesroots.mac.cards.presentaion.login.LoginActivity
import com.codesroots.mac.cards.presentaion.mainfragment.mainFragment
import com.codesroots.mac.cards.presentaion.mainfragment.viewmodel.MainViewModel
import com.codesroots.mac.cards.presentaion.menufragmen.ContactFragment
import com.codesroots.mac.cards.presentaion.menufragmen.SendSecretFragment
import com.codesroots.mac.cards.presentaion.menufragmen.TermsFragment
import com.codesroots.mac.cards.presentaion.reportsFragment.ReportsFragment
import com.codesroots.mac.cards.presentaion.reportsFragment.adapters.CompanyDetailsAdapter
import kotlinx.android.synthetic.main.main_menu.*

class main_menu_fragment : Fragment() {

    lateinit var MainAdapter: CompanyDetailsAdapter
    lateinit var viewModel: MainViewModel
    lateinit var callUs: ContactFragment
    lateinit var sendSecretFragment: SendSecretFragment
    lateinit var changePassword : changePassword
    lateinit var reportFragment : ReportsFragment
    lateinit var mainPage : mainFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        var view: MainMenuBinding =
            DataBindingUtil.inflate(inflater, R.layout.main_menu, container,false)

view.callUs.setOnClickListener {
    callUs = ContactFragment()
    fragmentManager!!.beginTransaction().setCustomAnimations(R.anim.ttb, 0, 0,0)
        .replace(R.id.main_frame, callUs)
        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        .commit()
}
        view.exchange.setOnClickListener {
            changePassword = changePassword()
            fragmentManager!!.beginTransaction().setCustomAnimations(R.anim.ttb, 0, 0,0)
                .replace(R.id.main_frame, changePassword)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        }
        view.send.setOnClickListener {
            sendSecretFragment = SendSecretFragment()
            fragmentManager!!.beginTransaction().setCustomAnimations(R.anim.ttb, 0, 0,0)
                .replace(R.id.main_frame, sendSecretFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        }
        view.reports.setOnClickListener {
            reportFragment = ReportsFragment()
            fragmentManager!!.beginTransaction().setCustomAnimations(R.anim.ttb, 0, 0,0)
                .replace(R.id.main_frame, reportFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        }
        view.mainPage.setOnClickListener {
            startActivity(Intent(context  , LoginActivity::class.java))
        }
        view.policy.setOnClickListener {
            fragmentManager!!.beginTransaction().setCustomAnimations(R.anim.ttb, 0, 0,0)

                .replace(R.id.main_frame, TermsFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        }
        return  view.root
    }

}