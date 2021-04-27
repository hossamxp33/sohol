package com.codesroots.mac.cards.presentaion

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.transition.Transition
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.core.view.isGone
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import cn.pedant.SweetAlert.SweetAlertDialog
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.codesroots.mac.cards.DataLayer.helper.MyService
import com.codesroots.mac.cards.DataLayer.helper.PreferenceHelper
import com.codesroots.mac.cards.R
import com.codesroots.mac.cards.models.Buypackge
import com.codesroots.mac.cards.models.CompanyDatum
import com.codesroots.mac.cards.models.LoginData
import com.codesroots.mac.cards.presentaion.addoffice.Register
import com.codesroots.mac.cards.presentaion.changepassword.changePassword
import com.codesroots.mac.cards.presentaion.companydetails.fragment.CompanyDetails
import com.codesroots.mac.cards.presentaion.main_menu.main_menu_fragment
import com.codesroots.mac.cards.presentaion.popup.CompanyDetailsActivity
import com.codesroots.mac.cards.presentaion.mainfragment.mainFragment
import com.codesroots.mac.cards.presentaion.mainfragment.viewmodel.MainViewModel
import com.codesroots.mac.cards.presentaion.menufragmen.MenuFragment
import com.codesroots.mac.cards.presentaion.myoffices.myofficesFragment
import com.codesroots.mac.cards.presentaion.mytrans.mytranActivty
import com.codesroots.mac.cards.presentaion.ordersfragment.OrdersFragment
import com.codesroots.mac.cards.presentaion.payment.Payment
import com.codesroots.mac.cards.presentaion.reportsFragment.ReportsFragment
import com.crashlytics.android.Crashlytics
import com.google.android.material.navigation.NavigationView
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.romellfudi.ussdlibrary.USSDController
import com.romellfudi.ussdlibrary.contains
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.alert_add_employee.view.*
import kotlinx.android.synthetic.main.alert_add_reserve.view.err
import kotlinx.android.synthetic.main.alert_add_reserve.view.from
import kotlinx.android.synthetic.main.alert_add_reserve.view.save
import org.jetbrains.anko.telephonyManager
import java.io.IOException
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet


class MainActivity : AppCompatActivity()  {

    lateinit var viewModel: MainViewModel
    private var map: HashMap<String, HashSet<String>>? = null
    lateinit var homeFragment: mainFragment
    lateinit var reportsFragment: ReportsFragment
    lateinit var moreFragment: MenuFragment
    lateinit var mytransfragment: mytranActivty
    lateinit var main_menu: main_menu_fragment

    lateinit var myorders: OrdersFragment
    lateinit var changepw: changePassword
    lateinit var myoffices: myofficesFragment

    lateinit var navigationView: NavigationView

     var company_id: String = ""
    var  data : Buypackge ? = null

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(Build.VERSION_CODES.O)
    private fun makePhonecall(id: Long, phone: String) {


        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        map = HashMap()
        map!!["KEY_LOGIN"] = HashSet(Arrays.asList("espere", "waiting", "loading", "esperando"))
        map!!["KEY_ERROR"] = HashSet(Arrays.asList("problema", "problem", "error", "null"))
        intent.data = Uri.fromParts("tel", phone, "#")

        var ussdApi = USSDController.getInstance(this)
        ussdApi.callUSSDOverlayInvoke(phone, map!!, object : USSDController.CallbackInvoke {
            override fun responseInvoke(message: String) {
                // message has the response string data
                print(message)
                if (company_id == "33"){
                    Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
                    Toast.makeText(this@MainActivity, "في ال 33", Toast.LENGTH_SHORT).show()

                    viewModel.EditOrder(id)
                    Toast.makeText(this@MainActivity, id.toString() , Toast.LENGTH_SHORT).show()

                   // if (viewModel.EditResponseLD?.hasObservers() == false) {
                        viewModel.EditResponseLD?.observe(this@MainActivity, Observer {
                            if (it.success == true) {
                                Toast.makeText(this@MainActivity, "تم الطباعة كورك" , Toast.LENGTH_SHORT).show()



                            }


                        })
                 //   }
                }
                var dataToSend = "data"// <- send "data" into USSD's input text
                ussdApi!!.send("1") {
                    print(it)
                    Toast.makeText(this@MainActivity, company_id.toString() , Toast.LENGTH_SHORT).show()
                    Toast.makeText(this@MainActivity, id.toString() , Toast.LENGTH_LONG).show()
                    if (company_id == "33") {

                        ussdApi!!.send("1") {

                            Toast.makeText(this@MainActivity, "في الواحد", Toast.LENGTH_SHORT).show()
                            ussdApi!!.cancel()
                        }
                    }

                    if (it == "0مزيد") {
                        ussdApi!!.cancel()
                        viewModel.EditOrder(id)

                        if (viewModel.EditResponseLD?.hasObservers() == false) {
                            viewModel.EditResponseLD?.observe(this@MainActivity, Observer {
                                if (it.success == true) {
                                    supportFragmentManager!!.beginTransaction()
                                        .replace(R.id.main_frame, mainFragment())
                                        .addToBackStack(null).commit()
                                }


                            })
                        }
                        Toast.makeText(this@MainActivity, "في الصفر", Toast.LENGTH_SHORT).show()

                    }
                    Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()

                    // message has the response string data from USSD


                }
            }

            override fun over(message: String) {
                // message has the response string data from USSD or error
                // response no have input text, NOT SEND ANY DATA
                print(message)
                Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
                Toast.makeText(this@MainActivity, "error", Toast.LENGTH_SHORT).show()
                if (company_id == "33"){
                    Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
                    Toast.makeText(this@MainActivity, "في ال 33", Toast.LENGTH_SHORT).show()

                    viewModel.EditOrder(id)
                    Toast.makeText(this@MainActivity, id.toString() , Toast.LENGTH_SHORT).show()

                    // if (viewModel.EditResponseLD?.hasObservers() == false) {
                    viewModel.EditResponseLD?.observe(this@MainActivity, Observer {
                        if (it.success == true) {
                            Toast.makeText(this@MainActivity, "تم الطباعة كورك" , Toast.LENGTH_SHORT).show()



                        }


                    })
                    //   }
                }
            }
        })

    }
    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(Build.VERSION_CODES.O)
    private fun MakeasiaTa3b2a(id: Long, code: String, phone: String,price:String) {


        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        map = HashMap()
        map!!["KEY_LOGIN"] = HashSet(Arrays.asList("espere", "waiting", "loading", "esperando"))
        map!!["KEY_ERROR"] = HashSet(Arrays.asList("problema", "problem", "error", "null"))
        intent.data = Uri.fromParts("tel", phone, "#")

        var ussdApi = USSDController.getInstance(this)
        ussdApi.callUSSDOverlayInvoke(code, map!!, object : USSDController.CallbackInvoke {
            override fun responseInvoke(message: String) {
                // message has the response string data

                var dataToSend = "data"// <- send "data" into USSD's input text
//                ussdApi!!.send("1") {
//                    //
//                    ussdApi!!.send("1") {
//
//                    }
//                    ussdApi!!.send("1") {
//
//                    }
//                    if (!it.contains("More")) {
//                        ussdApi!!.send("1") {
//
//                        }
//                }
                Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()

                if (message.contains("1. Topup")) {
                    Toast.makeText(this@MainActivity, "true in Topup", Toast.LENGTH_SHORT).show()

                    ussdApi!!.send("1") {

                        ussdApi!!.send("4"){

                        }
                    }

                }
                if (message.contains("2. E-voucher")) {
                    Toast.makeText(this@MainActivity, "true in voucher", Toast.LENGTH_SHORT).show()

                    ussdApi!!.send("1") {

                        ussdApi!!.send(phone){

                        }
                    }

                }
                //    ussdApi!!.send("1") {
if (message.contains("تعريفي")) {
                ussdApi!!.send("0443") {

                    Toast.makeText(this@MainActivity, "true in تعريفي", Toast.LENGTH_SHORT).show()
                    ussdApi!!.send("3") {
                        Toast.makeText(this@MainActivity, "true in recharge", Toast.LENGTH_SHORT).show()

                    }
                }
}
                if (message.contains("Quantity?")) {
                    Toast.makeText(this@MainActivity, "true in Quantity", Toast.LENGTH_SHORT).show()

                    ussdApi!!.send(price) {

                        ussdApi!!.send("2"){

                            viewModel.EditOrder(id)
                            Toast.makeText(this@MainActivity, id.toString() , Toast.LENGTH_SHORT).show()

                            // if (viewModel.EditResponseLD?.hasObservers() == false) {
                            viewModel.EditResponseLD?.observe(this@MainActivity, Observer {
                                if (it.success == true) {
                                    Toast.makeText(this@MainActivity, "تم الطباعة اسيا تعبئة" , Toast.LENGTH_SHORT).show()



                                }


                            })
                        }
                    }

                }
                if (message.contains("Successful") ) {
                    Toast.makeText(this@MainActivity, "true in successful", Toast.LENGTH_SHORT).show()

                    viewModel.EditOrder(id)
                    Toast.makeText(this@MainActivity, id.toString() , Toast.LENGTH_SHORT).show()

                    // if (viewModel.EditResponseLD?.hasObservers() == false) {
                    viewModel.EditResponseLD?.observe(this@MainActivity, Observer {
                        if (it.success == true) {
                            Toast.makeText(this@MainActivity, "تم الطباعة اسيا تعبئة", Toast.LENGTH_SHORT).show()



                        }


                    })
                            ussdApi.cancel()

                }
              //    ussdApi!!.send("3") {

                      //  ussdApi!!.cancel()

                          //     ussdApi!!.send("1") {

//
                      //      ussdApi!!.send(phone) {
////                                ussdApi!!.send("1") {
////
////                                    ussdApi!!.send("4") {
////
////                                        ussdApi!!.send(price) {
////                                            ussdApi!!.send("2") {
////                                                ussdApi!!.send("2") {
////
////                                                    viewModel.EditOrder(id)
////
////                                                    if (viewModel.EditResponseLD?.hasObservers() == false) {
////                                                        viewModel.EditResponseLD?.observe(
////                                                            this@MainActivity,
////                                                            Observer {
////                                                                if (it.success == true) {
////                                                                    supportFragmentManager!!.beginTransaction()
////                                                                        .replace(
////                                                                            R.id.main_frame,
////                                                                            mainFragment()
////                                                                        )
////                                                                        .addToBackStack(null)
////                                                                        .commit()
////                                                                }
////
////
////                                                            })
////                                                    }
////
////                                                }
////                                            }
////                                        }
////
////                                        }

                          //  }
               //      }
                 //  }
//

           }

            override fun over(message: String) {
                // message has the response string data from USSD or error
                // response no have input text, NOT SEND ANY DATA
                print(message)
                Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
                Toast.makeText(this@MainActivity, "error", Toast.LENGTH_SHORT).show()

            }
        })

    }
    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        //////// when new order come
        val new_order = intent.getIntExtra("new_order", 0)
        if (new_order == 1) {
            var phone = intent.getStringExtra("phone")
            var code = intent.getStringExtra("code")
            var price = intent.getStringExtra("name")
            var id = intent.getLongExtra("id", 0)
            company_id = intent.getStringExtra("companyId")
            company_id!!.snack(window.decorView.rootView, 1000)

            if (company_id == "33" && telephonyManager.networkOperatorName == "KOREK") {
                makePhonecall(id, code + phone.substring(1) + "*" + price + "*12369" + "#")
                Toast.makeText(this, "طلب جديد", Toast.LENGTH_SHORT).show()


            } else if (company_id == "15") {
                makePhonecall(id, code + price + "*" + phone + "#")
                Toast.makeText(this, "طلب جديد", Toast.LENGTH_SHORT).show()


            } else if (company_id == "42") {
                MakeasiaTa3b2a(id, code,phone,price)


            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PreferenceHelper(this)
        FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance()
        FirebaseMessaging.getInstance().subscribeToTopic(PreferenceHelper.getUserGroupId().toString())
        FirebaseMessaging.getInstance().subscribeToTopic("10")

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        ///////// tool bar and drawerToggle
       // setSupportActionBar(toolBar)
//       val actionBar = supportActionBar
//        actionBar?.title = ""
//        val drawerToggle: ActionBarDrawerToggle =
//            object : ActionBarDrawerToggle(this, drawerLayout, toolBar, (R.string.open), (R.string.close)) {
//
//        }

        viewModel =   ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.getcompanyData()
        viewModel.CompanyResponseLD?.observe(this , Observer {
            //lastvalue.append(it.usercredit.toString())
           lastvaluee.text =  it.usercredit.toString()
            namevaluee.text = PreferenceHelper.getUsername()

        })
        main_btn.setOnClickListener {
            main_menu = main_menu_fragment()
            supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.ttb, 0, 0,0)
                .replace(R.id.main_frame, main_menu)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        }

        (telephonyManager.networkOperatorName).snack(window.decorView.rootView,3000)

//        drawerToggle.isDrawerIndicatorEnabled = true
     //   drawerLayout.addDrawerListener(drawerToggle)
        animation()
    //    drawerToggle.syncState()
        //nav_view.setNavigationItemSelectedListener(this)
        homeFragment = mainFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frame, homeFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()


        //startService(Intent(this, USSDService::class.java))
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE),
                15
            )
        }
//

            val typeface =  ResourcesCompat.getFont(this, R.font.fonts)


        // Create messages
        Fabric.with(this, Crashlytics())
        //Crashlytics.getInstance().crash() // Force a crash

        val item1 = AHBottomNavigationItem(
            R.string.tab_1,
            R.drawable.house_outline, R.color.signinpurple

        )
        val item2 = AHBottomNavigationItem(
            R.string.tab_2,
            R.drawable.analytics, R.color.signinpurple
        )
        val item3 = AHBottomNavigationItem(
            R.string.tab_3,
            R.drawable.more, R.color.signinpurple
        )

        val item4 = AHBottomNavigationItem(
            R.string.tab_4,
            R.drawable.logout, R.color.signinpurple
        )
        with(bottom_navigation) {
            println(PreferenceHelper.getUserGroupId())
            if (PreferenceHelper.getUserGroupId() == 4 || PreferenceHelper.getUserGroupId() == 5 || PreferenceHelper.getUserGroupId() == 6) {

                addItems(listOf(item2,item1,  item3,item4))

            }else {
                addItems(listOf(item1,item2, item3,item4))


            }
            if (PreferenceHelper.getUserGroupId() == 2) {
                navigationView.getMenu().findItem(R.id.addoffice).setVisible(true);

            }else {
                navigationView.getMenu().findItem(R.id.addoffice).setVisible(false);

            }



            inactiveColor = ContextCompat.getColor(context, R.color.gray)
            accentColor = ContextCompat.getColor(context, R.color.signinpurple)

            currentItem = 0

            setOnTabSelectedListener { position, wasSelected ->
                Unit
                Log.e("tab positiion", position.toString())
                /*  getLastLocation()*/
                if (position == 3) {
                    supportFragmentManager!!.beginTransaction().setCustomAnimations(R.anim.ttb, 0, 0,0)
                        .replace(com.codesroots.mac.cards.R.id.main_frame, OrdersFragment())
                        .addToBackStack(null).commit()
                }
                if (position == 2) {
                    supportFragmentManager!!.beginTransaction().setCustomAnimations(R.anim.ttb, 0, 0,0)
                        .replace(com.codesroots.mac.cards.R.id.main_frame, MenuFragment())
                        .addToBackStack(null).commit()
                }
                if (position == 1) {
                    supportFragmentManager!!.beginTransaction().setCustomAnimations(R.anim.ttb, 0, 0,0)
                        .replace(R.id.main_frame, ReportsFragment()).addToBackStack(null).commit()

                }
                if (position == 0) {
                    supportFragmentManager!!.beginTransaction().setCustomAnimations(R.anim.ttb, 0, 0,0)
                        .replace(R.id.main_frame, mainFragment()).addToBackStack(null).commit()


                }
                true

            }
            supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.ttb, 0, 0,0)
                .replace(R.id.main_frame, mainFragment(), "Main").addToBackStack(null).commit()

        }

println(PreferenceHelper.getUserGroupId())
        if (PreferenceHelper.getUserGroupId() == 1) {
            startService(Intent(this, MyService::class.java))
            //   startService(Intent(this, USSDService::class.java))

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //询问用户权限
        if (permissions[0] == Manifest.permission.WRITE_EXTERNAL_STORAGE && grantResults[0] == PackageManager.PERMISSION_GRANTED && permissions[0] == Manifest.permission.READ_PHONE_STATE) {
            //用户同意
        } else {
            //用户不同意
        }
    }


//    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
//
//        when (menuItem.itemId) {
//            R.id.home -> {
//                homeFragment = mainFragment()
//                supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.ttb, 0, 0,0)
//                    .replace(R.id.main_frame, homeFragment)
//                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                    .commit()
//
//
//            }
//            R.id.reports -> {
//                reportsFragment = ReportsFragment()
//                supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.ttb, 0, 0,0)
//                    .replace(R.id.main_frame, reportsFragment)
//                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                    .commit()
//            }
//            R.id.more -> {
//            moreFragment = MenuFragment()
//            supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.ttb, 0, 0,0)
//                .replace(R.id.main_frame, moreFragment)
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                .commit()
//        }
//            R.id.mytrans -> {
//                mytransfragment = mytranActivty()
//                supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.ttb, 0, 0,0)
//                    .replace(R.id.main_frame, mytransfragment)
//                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                    .commit()
//            }
//            R.id.myorder -> {
////                myorders = OrdersFragment()
////                supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.ttb, 0, 0,0)
////                    .replace(R.id.main_frame, myorders)
////                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
////                    .commit()
//            }
//
//            R.id.myoffices -> {
//                myoffices = myofficesFragment()
//                supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.ttb, 0, 0,0)
//                    .replace(R.id.main_frame, myoffices)
//                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                    .commit()
//            }
//
//            R.id.addoffice -> {
//                val intent1 = Intent(applicationContext, Register::class.java)
//
//                startActivity(intent1)
//
//            }
//            R.id.changepw -> {
//                changepw = changePassword()
//                supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.ttb, 0, 0,0)
//                    .replace(R.id.main_frame, changepw)
//                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                    .commit()
//            }
//        }
//
//        drawerLayout.closeDrawer(GravityCompat.START)
//        return true
//    }

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.isDrawerOpen(GravityCompat.START)
        }
        else{
        super.onBackPressed()
            }
    }

    private fun animation(){
        navigationView = nav_view
        val ttb = AnimationUtils.loadAnimation(this, R.anim.ttb)
        navigationView!!.animation = ttb



    }
}


class ClickHandler {
    var  mLastClickTime: Long = 0
     fun makePhonecall(context: Context,phone: String) {

         val intent = Intent(Intent.ACTION_CALL)
         intent.data = Uri.parse("tel:$phone")
         if (ActivityCompat.checkSelfPermission(
                 context,
                 Manifest.permission.CALL_PHONE
             ) != PackageManager.PERMISSION_GRANTED
         ) {
             ActivityCompat.requestPermissions(
                 context as Activity,
                 arrayOf(Manifest.permission.CALL_PHONE),
                 15
             )
             return
         }
         context.startActivity(intent)

    }
    fun SwitchToPackages( context: Context,comid :CompanyDatum,id :String) {
        PreferenceHelper.settype(comid.type.toString())

    if (comid.type == 0){
    val bundle = Bundle()
    //  bundle.putParcelable("cliObj" ,clients[position] )
    val frag = CompanyDetails()
    frag.arguments = bundle
    bundle.putString("packageId" , comid.id)
    ( context as MainActivity).supportFragmentManager!!.beginTransaction().setCustomAnimations(R.anim.ttb,0, 0,0)
        .replace(R.id.main_frame, frag).addToBackStack(null).commit()
}else {
    val homeIntent = Intent(context,
        CompanyDetailsActivity()::class.java)
        homeIntent.putExtra("packageId" , id)
    (context as MainActivity).startActivity(homeIntent)

}


    }
//    fun SwitchToReports( context: Context,comid :String) {
//
//        val bundle = Bundle()
//        //  bundle.putParcelable("cliObj" ,clients[position] )
//        val frag = ReportsFragment()
//        frag.arguments =bundle
//        bundle.putString("packageId" , comid)
//        ( context as MainActivity).supportFragmentManager!!.beginTransaction()
//
//            .replace(com.codesroots.mac.cards.R.id.main_frame, frag).addToBackStack(null).commit()
//    }
    fun transactions(context: Context,data:LoginData?,viewmodel:MainViewModel,type:Int){
    val dialogBuilder = AlertDialog.Builder(( context as MainActivity) )
    val inflater = ( context as MainActivity).getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    var dialogView = inflater.inflate(R.layout.alert_offices, null)
    dialogBuilder.setView(dialogView)
    val alertDialog = dialogBuilder.create()
    var  title =  TextView(context as MainActivity);
// You Can Customise your Title here
    title.setText("إضافة طلب");
    title.setBackgroundColor(Color.DKGRAY);
    title.setPadding(10, 10, 10, 10);
    title.setGravity(Gravity.CENTER);
    title.setTextSize(20f);

    dialogBuilder.setCustomTitle(title);
    alertDialog.show()

    dialogView.save.setOnClickListener { v: View? ->
        if (SystemClock.elapsedRealtime() - mLastClickTime < 10000){
            return@setOnClickListener
        }

        v!!.isGone = true

        mLastClickTime = SystemClock.elapsedRealtime();
         var mobile = ""
        if (type == 1) {

            mobile = dialogView.from.text.toString()
        }else {

           mobile = data!!.mobile.toString()

        }


        viewmodel.transactions(mobile,dialogView.name.text.toString())


        if (viewmodel.transResponseLD?.hasObservers() == false) {
            viewmodel.transResponseLD?.observe(context, Observer {


                    if (it!!.trans!!.id != null) {
                        "تم تحويل المبلغ بنجاح".snack(context.window.decorView.rootView)

                        val homeIntent = Intent(context, Payment::class.java)


                        (context as MainActivity).startActivity(homeIntent)


                    }else if (it!!.trans!!.err != null ) {

                        it!!.trans!!.err!!.snack(context.window.decorView.rootView)

                    }
            })

        }

    }

    }
    fun AddCardOrder(context: Context,viewmodel:MainViewModel) {
        var MyData = context as CompanyDetailsActivity
        var pDialog : SweetAlertDialog? = null;
          var total = MyData.totalvalue
        val auth = PreferenceHelper.getAuthId()
        viewmodel.AddCardOrder(MyData!!.Company_id!!,MyData!!.minteger!!.toString())

        if (viewmodel.BuyPackageResponseLD?.hasObservers() == false) {
            viewmodel.BuyPackageResponseLD?.observe(context as CompanyDetailsActivity, Observer {
                if (it.center!!.err != null) {
                    val alertDialogBuilder = AlertDialog.Builder(context)
                    pDialog =  SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
                    pDialog!!.setTitleText("يوجد خطأ!")
                    pDialog!!.setContentText(it.center!!.err!!)
                    pDialog!!.setConfirmText("OK")
                    pDialog!!.show()

                } else {
                    var data = it
                    pDialog =  SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
                    pDialog!!.setTitleText("تم اضافة الطلب!")
                    pDialog!!.setContentText("إضغط  لطباعة الطلب")
                    pDialog!!.setConfirmText("طباعة")
                    pDialog!!.confirmButtonBackgroundColor = R.color.blue
                    pDialog!!.show()

                    pDialog!!.setConfirmClickListener {
                                        pDialog!!.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                        pDialog!!.show();
                                              val homeIntent = Intent(context, Payment::class.java)

                                                    homeIntent.putExtra("myobj", data)
                                                     homeIntent.putExtra("total", total)
                                                        (context).startActivity(homeIntent)
                                    }
                                }
                            })
                    }
    }

    fun SwitchToPayment(context: Context,id:CompanyDatum,viewmodel:MainViewModel) {

    val dialogBuilder = AlertDialog.Builder(( context as MainActivity) )
    val inflater = ( context as MainActivity).getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    var dialogView = inflater.inflate(R.layout.alert_add_employee, null)


    if (id.companyID!! != 15  || id.companyID!! != 30 || id.companyID!! != 33 ) {
        dialogView = inflater.inflate(R.layout.alert_add_employee, null)


    }

    if (id.companyID!! == 15  || id.companyID!! == 30 || id.companyID !! == 33 || id.companyID !! == 39 ) {
        fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
            this.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    afterTextChanged.invoke(s.toString())
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
            })
        }
        dialogView.from.afterTextChanged {
            val content =  dialogView.from.text.toString()
            dialogView.from.error = if (content.startsWith("077") || content.startsWith("075") || content.startsWith("078")) null

            else "من فضلك ادخل الكود صحيح! "


        }
    }else{
        fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
            this.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    afterTextChanged.invoke(s.toString())
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
            })
        }
        dialogView.from.afterTextChanged {
            if (id.companyID!! == 2  ) {
                val content =  dialogView.from.text.toString()
                dialogView.from.error = if (content.length == 10) null

                else "من فضلك ادخل الكود صحيح! "
            }else {

                val content =  dialogView.from.text.toString()
                dialogView.from.error = if (content.length == 16) null

                else "من فضلك ادخل الكود صحيح! "
            }



        }
    }

    dialogBuilder.setView(dialogView)
    val alertDialog = dialogBuilder.create()
    var  title =  TextView(context as MainActivity);
// You Can Customise your Title here
    title.setText("إضافة طلب");
    title.setBackgroundColor(Color.DKGRAY);
    title.setPadding(10, 10, 10, 10);
    title.setGravity(Gravity.CENTER);
    title.setTextSize(20f);

    dialogBuilder.setCustomTitle(title);
    alertDialog.show()

    dialogView.save.setOnClickListener { v: View? ->
        val msg: String = dialogView.from.text.toString()
        //check if the EditText have values or not
        if (id.companyID!! == 15 || id.companyID!! == 30 || id.companyID!! == 33) {
            if (msg.trim().length >= 11) {
                println(msg.substring(1))
                if (msg.startsWith("077") || msg.startsWith("075")) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 10000) {
                        return@setOnClickListener
                    }

                    v!!.isGone = true

                    mLastClickTime = SystemClock.elapsedRealtime();

                AddNewOrder(context, id, viewmodel, dialogView )

            } else {
                Toast.makeText(context, " يجب ان يبدا الكود ب 077", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(context, "من فضلك ادخل الكود صحيح!", Toast.LENGTH_SHORT).show()
        }

        }else if (id.companyID!! == 29  || id.companyID!! == 31 )
    {

        if(msg.trim().length == 16) {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 10000){
                return@setOnClickListener
            }

            v!!.isGone = true

            mLastClickTime = SystemClock.elapsedRealtime();

            AddNewOrder(context, id, viewmodel, dialogView)


        }else{
            Toast.makeText(context, "من فضلك ادخل الكود صحيح!", Toast.LENGTH_SHORT).show()
        }


    }

    else{
        if(msg.trim().length >=10) {

            if (SystemClock.elapsedRealtime() - mLastClickTime < 10000){
                return@setOnClickListener
            }

            v!!.isGone = true

            mLastClickTime = SystemClock.elapsedRealtime();
            AddNewOrder(context, id, viewmodel, dialogView)
        }
        else{
            Toast.makeText(context, "من فضلك ادخل الكود صحيح! ", Toast.LENGTH_SHORT).show()
        }
    }

    }
}

    fun AddNewOrder(context: Context,id:CompanyDatum,viewmodel:MainViewModel,Dialog:View){

        viewmodel.BuyPackage(PreferenceHelper.gettype().toInt(),id.id!!,Dialog.from.text.toString(),"admin")


        if (viewmodel.BuyPackageResponseLD?.hasObservers() == false) {
            viewmodel.BuyPackageResponseLD?.observe(context as MainActivity, Observer {


                if (it.center!!.err != null) {
                    it.center!!.err!!.snack((context as MainActivity).window.decorView.rootView)
                    Dialog.err.text = it.center!!.err
                    Dialog.err.isGone = false
                } else {

                    if (it!!.center!!.id != null) {
                        val homeIntent = Intent(context, Payment::class.java)


                        (context as MainActivity).startActivity(homeIntent)


                    }

                }

            })

        }


    }
fun ConfirmOrder(id:Long,viewmodel:MainViewModel,context: Context){

    viewmodel.ConfirmOrder(id)


    if (viewmodel.EditResponseLD?.hasObservers() == false) {
        viewmodel.EditResponseLD?.observe( context as MainActivity, Observer {

            viewmodel.GetMyorders();




        })
    }


}


}
