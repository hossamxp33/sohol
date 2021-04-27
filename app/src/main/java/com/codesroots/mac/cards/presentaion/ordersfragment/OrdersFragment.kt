package com.codesroots.mac.cards.presentaion.ordersfragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.ColorRes
import androidx.annotation.RequiresApi
import androidx.core.view.isGone
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.codesroots.mac.cards.DataLayer.helper.PreferenceHelper
import com.codesroots.mac.cards.DataLayer.usecases.getTime
import com.codesroots.mac.cards.R
import com.codesroots.mac.cards.databinding.DialogCustomViewBinding
import com.codesroots.mac.cards.databinding.MytransItemBinding
import com.codesroots.mac.cards.databinding.ServerBinding
import com.codesroots.mac.cards.databinding.ServerFragmentBinding
import com.codesroots.mac.cards.models.CompanyData
import com.codesroots.mac.cards.models.CompanyDatum
import com.codesroots.mac.cards.presentaion.MainActivity
import com.codesroots.mac.cards.presentaion.mainfragment.Adapter.SliderAdapter
import com.codesroots.mac.cards.presentaion.mainfragment.viewmodel.MainViewModel
import com.codesroots.mac.cards.presentaion.payment.Payment
import com.codesroots.mac.cards.presentaion.reportsFragment.ReportsFragment
import com.codesroots.mac.cards.presentaion.snack
import com.google.android.gms.analytics.internal.zzy.v
import kotlinx.android.synthetic.main.alert_add_employee.view.*
import kotlinx.android.synthetic.main.alert_add_reserve.view.*
import kotlinx.android.synthetic.main.dialog_custom_view.*
import kotlinx.android.synthetic.main.dialog_custom_view.view.*
import kotlinx.android.synthetic.main.main_fragment.indicator
import kotlinx.android.synthetic.main.server_fragment.*
import kotlinx.android.synthetic.main.server_fragment.process_time_text

import kotlinx.android.synthetic.main.server_fragment.view.*
import org.jetbrains.anko.textColor
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import java.util.Calendar.*
import java.time.LocalDateTime.of as of1


class OrdersFragment : Fragment() {
    private var currentPage = 0
    private var NUM_PAGES = 0
    private var pager: ViewPager? = null
    var CompanyList:List<CompanyDatum>? = null
    var Companydata:CompanyDatum? = null

    var CompanyDetailsList:List<CompanyDatum>? = null
    var package_id:String? = ""

    var Price:String? = ""

    //    lateinit var MainAdapter: ordersAdapter
lateinit  var spinner: Spinner
    lateinit  var spinner_type: Spinner

    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment

        var view: ServerBinding = DataBindingUtil.inflate(inflater, R.layout.server, container,false)

      //  pager = view.serverPager
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.GetMyorders();
        viewModel.GetMyImages(PreferenceHelper.getToken())
        view.data = Companydata
        viewModel.getcompanyData()

//        viewModel.SliderDataResponseLD?.observe(this , Observer {
//            pager!!.offscreenPageLimit = 3
//            //  pager!!.pageMargin = 20
//            pager!!.clipChildren = false
//            pager!!.clipToPadding = false
//            //   pager!!.setPadding(100, 0, 50, 0)
//            view.serverPager.adapter = it?.let { it1 -> SliderAdapter(activity!!, it1) }
//            indicator.setViewPager(view.serverPager)
//            it?.size?.let { it1 -> init(it1) }
//
//        })

        spinner = view.orderSpinner
        spinner_type = view.orderTypeSpinner

        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat.getDateTimeInstance() //or use getDateInstance()
        val formatedDate = formatter.format(date)
    //    view.processTimeText.text = formatedDate

        viewModel.CompanyResponseLD?.observe(this, Observer {
            CompanyList = it.companies
            var arrayadapter =  CompanyList!!.
                filter { companyDatum -> companyDatum.id == "15" || companyDatum.id == "33"  || companyDatum.id == "42"}
                spinner.adapter = activity?.applicationContext?.
                let { it1 -> ArrayAdapter(it1, R.layout.spinner, arrayadapter.map { it.name }) }
        })
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
             val arrayadapter =  CompanyList!!.
            filter { companyDatum -> companyDatum.id == "15" || companyDatum.id == "33" || companyDatum.id == "42"}
            var  com_id = arrayadapter[position].id
                Companydata = arrayadapter[position]
            viewModel.getPackageDetails(com_id!!)

            }
        }


        viewModel.CompanyDetailsResponseLD?.observe(this , Observer {
             CompanyDetailsList = it.data
            spinner_type.adapter = activity?.applicationContext?.
                let { it1 -> ArrayAdapter(it1, R.layout.spinner, CompanyDetailsList!!.map { it.name }) }
        })

        spinner_type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                  package_id = CompanyDetailsList!![position].id
                 price_value.text = CompanyDetailsList!![position].sprice
                Price = CompanyDetailsList!![position].sprice

            }}

        view.presenter  =  object : Presenter {
            override fun AddClick() {
                showCustomDialog(container!!)

            }
        }

view.send.setOnClickListener {




}




        return view.root;

    }





    private fun init(size: Int) {
        val density = getResources().getDisplayMetrics().density
        indicator.setRadius(4 * density)
        NUM_PAGES = size
        val handler = Handler()
        val Update:Runnable = Runnable {
            if (currentPage == NUM_PAGES) {
                currentPage = 0
            }
            pager?.setCurrentItem(currentPage++, true)
        }
        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(Update)
            }
        }, 4000, 10000)
        indicator.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                currentPage = position
            }

            override fun onPageScrolled(pos: Int, arg1: Float, arg2: Int) {}
            override fun onPageScrollStateChanged(pos: Int) {}
        })
    }

    private lateinit var alertDialog: AlertDialog
    private lateinit var alertDialog2: AlertDialog

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun showCustomDialog(container:ViewGroup) {
        val inflater: LayoutInflater = this.getLayoutInflater()
        val phone_number = phone_number.text.toString()
        if(phone_number.trim().length>=11) {
            println( phone_number.substring(1))
            if (phone_number.startsWith("077") || phone_number.startsWith("075")){

                val  dialogView: DialogCustomViewBinding = DataBindingUtil.inflate (
                    LayoutInflater.from(context),
                    R.layout.dialog_custom_view,container,false)

                val date = Calendar.getInstance().time
                val formatter = SimpleDateFormat.getDateTimeInstance() //or use getDateInstance()
                val formatedDate = formatter.format(date)
                dialogView.processTimeText.text = formatedDate

                dialogView.secondPrice.text = Price!!

                dialogView.price.text = Price!!
                dialogView.data = Companydata
                val dialogView2: View = inflater.inflate(R.layout.thanks_dialog, null)

                val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context!!,R.style.yourCustomDialog)

                dialogBuilder.setOnDismissListener(object : DialogInterface.OnDismissListener {
                    override fun onDismiss(arg0: DialogInterface) {
                    }
                })

                dialogBuilder.setView(dialogView.root)
                alertDialog = dialogBuilder.create();
                alertDialog.show()
                dialogView.cancelButton.setOnClickListener {

                    alertDialog.dismiss()

                }
                dialogView.acceptBuyNow.setOnClickListener {
                    // Dismiss the popup window
                    println(view!!.phone_number.text.toString())
                    println(Companydata!!.id!!)

                    viewModel.BuyPackage(1,package_id!!,view!!.phone_number.text.toString(),"server")


                    if (viewModel.BuyPackageResponseLD?.hasObservers() == false) {
                        viewModel.BuyPackageResponseLD?.observe(this, Observer {


                            if (it.center!!.err != null) {
                                it.center!!.err!!.snack((context as MainActivity).window.decorView.rootView)
                                dialogView.text.text = it.center!!.err!!
                                dialogView.text.textColor = Color.RED
                            } else {

                                if (it!!.center!!.id != null) {

                                    val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context!!,R.style.LLDialog)
                                    alertDialog.dismiss()
                                    dialogBuilder.setView(dialogView2)
                                    alertDialog2 = dialogBuilder.create();
                                    alertDialog2.show()



                                }

                            }

                        })
                    }


                }
                val done_button: Button = dialogView2.findViewById(R.id.view_order)
                val Cancel_button: Button = dialogView2.findViewById(R.id.done)

                done_button.setOnClickListener {
                    activity!!.supportFragmentManager!!.beginTransaction().setCustomAnimations(R.anim.ttb, 0, 0,0)
                        .replace(R.id.main_frame, ReportsFragment()).addToBackStack(null).commit()
                    alertDialog2.dismiss()
                }
                Cancel_button.setOnClickListener {

                    alertDialog2.dismiss()

                }
            }else{
                Toast.makeText(context, " يجب ان يبدا الكود ب 077", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(context, "من فضلك ادخل الكود صحيح!", Toast.LENGTH_SHORT).show()
        }






    }
}

