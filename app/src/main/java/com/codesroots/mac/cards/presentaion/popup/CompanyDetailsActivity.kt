package com.codesroots.mac.cards.presentaion.popup

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.codesroots.mac.cards.R
import com.codesroots.mac.cards.databinding.PopupCompanyDetailsBinding
import com.codesroots.mac.cards.models.CompanyDatum
import com.codesroots.mac.cards.presentaion.ClickHandler
import com.codesroots.mac.cards.presentaion.mainfragment.viewmodel.MainViewModel
import com.codesroots.mac.cards.presentaion.mainfragment.viewmodel.setImageResourcee
import com.codesroots.mac.cards.presentaion.reportsFragment.adapters.CompanyDetailsAdapter
import kotlinx.android.synthetic.main.company_details.*
import kotlinx.android.synthetic.main.popup_company_details.*
import kotlinx.android.synthetic.main.popup_company_details.recyler


public class CompanyDetailsActivity  : AppCompatActivity(), ContentListener {


    override fun onItemClicked(item: CompanyDatum) {

        Company_id = item.id

        totalvalue = item.sprice + "  IQD"
        totalvalue?.let { displaytext(it) }

    }
    lateinit var MainAdapter: PopUpAdapter
    lateinit var viewModel: MainViewModel
    var  binding : PopupCompanyDetailsBinding? = null
    var data : List<CompanyDatum>? = null
    var minteger = 1
    var totalvalue : String ? = null
    var Company_id : String? = null

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.popup_company_details)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        var extras = intent.extras
        val packageId = extras?.getString("packageId")

        binding!!.viewmodel = viewModel
        binding!!.context = this
        binding!!.listener = ClickHandler()
         binding!!.data = CompanyDatum()
        viewModel.getPackageDetails(packageId!!)
        minteger   = Integer.parseInt(integer_number.getText().toString());


        decrease.setOnClickListener {
            if (minteger > 1) {
                decreaseInteger (decrease)
            }else{
                Log.d("src", "Value can't be less than 0");
            }
        }
        increase.setOnClickListener {
            if (minteger >=1) {
                increaseInteger (increase)
            }else{
                Log.d("src", "Value can't be less than 0");
            }
        }
        viewModel.CompanyDetailsResponseLD?.observe(this, Observer {

            MainAdapter = PopUpAdapter(viewModel,this, it.data!!,this)
            recyler.layoutManager = GridLayoutManager(this,3, GridLayoutManager.VERTICAL, false)
            recyler.adapter = MainAdapter;

            setImageResourcee(logo,it.data!!.get(0).photo)

            progressBar.setVisibility(View.GONE)
            progressBar2.setVisibility(View.GONE)

        })


    }
    public fun display(number: Int) {
        val displayInteger = findViewById<View>(R.id.integer_number) as TextView
        val totalInteger = findViewById<View>(R.id.total) as TextView

     totalInteger.text = "" + (number * totalvalue!!.replace(" IQD", "")!!.toInt()) + " IQD"
        displayInteger.text = "" + number
    }
    public fun displaytext(number: String) {
        val displayInteger = findViewById<View>(R.id.total) as TextView

        displayInteger.text = "" + number
    }
    fun increaseInteger(view: View) {

        minteger += 1
        display(minteger)

    }


    fun decreaseInteger(view: View) {

        minteger -= 1

        display(minteger)
    }







}