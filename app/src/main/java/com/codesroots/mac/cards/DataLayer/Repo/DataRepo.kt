package com.codesroots.mac.firstkotlon.DataLayer.Repo

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.codesroots.mac.cards.DataLayer.ApiService.ApiClient
import com.codesroots.mac.cards.DataLayer.helper.PreferenceHelper
import com.codesroots.mac.cards.models.*
import com.codesroots.mac.firstkotlon.DataLayer.ApiService.APIServices

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class  DataRepo {


@SuppressLint("CheckResult")


fun Login(username:String,password:String,livedata: MutableLiveData<LoginData>?) {

    getServergetway().userlogin(username,password)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .map { data -> data }
    .subscribe(
                { success ->
                    Log.i("success",success.toString())
                    livedata?.postValue(success.data)
                },
            { error ->

            }
        )
}
    @SuppressLint("CheckResult")

    fun Register(username:String,password:String,mobile:String,groupid:String,agency:String,livedata: MutableLiveData<LoginData>?) {

        getServergetway().userregister(username,password,mobile,groupid,agency,"1","1")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { data -> data }
            .subscribe(
                { success ->
                    livedata?.postValue(success.data)
                },
                { error ->

                }
            )
    }


    @SuppressLint("CheckResult")

    fun GetData(livedata: MutableLiveData<CompanyData>?) {

        getServergetway().GetCompanyData(PreferenceHelper.getToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { data -> data }
            .subscribe(
                { books ->
                    livedata?.postValue(books)
                },
                { error ->

                }
            )
    }
    @SuppressLint("CheckResult")

    fun  getPopcompanyData(livedata: MutableLiveData<List<CompanyDatum>>?) {

        getServergetway().GetPopCompanyData(PreferenceHelper.getToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { data -> data }
            .subscribe(
                { books ->
                    livedata?.postValue(books)
                },
                { error ->

                }
            )
    }

    @SuppressLint("CheckResult")

    fun GetPackageDetails(id:String,livedata: MutableLiveData<CompanyData>?) {

        getServergetway().GetPackageDetails(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { data -> data }
            .subscribe(
                { books ->
                    livedata?.postValue(books)
                },
                { error ->

                }
            )
    }

    @SuppressLint("CheckResult")

    fun BuyPackage(type:Int,id:String,user_id:String,phone:String,name:String,livedata: MutableLiveData<Buypackge>?,compiste: CompositeDisposable) {

        compiste .add(   getServergetway().BuyPackage(user_id,id,phone,type,name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

            .subscribe(
                { books ->
                    livedata?.postValue(books)
                },
                { error ->
print(error)
                }
            ))
    }

    @SuppressLint("CheckResult")

    fun addCardOrder(id:String,amount:String,livedata: MutableLiveData<Buypackge>?,compiste: CompositeDisposable) {

        compiste .add(   getServergetway().addCardOrder(id,5,amount.toInt())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

            .subscribe(
                { books ->
                    livedata?.postValue(books)
                },
                { error ->
                    print(error)
                }
            ))
    }


    @SuppressLint("CheckResult")

    fun GetMyBalance(livedata: MutableLiveData<MyBalance>?) {

        getServergetway().GetMyBlanceData(PreferenceHelper.getToken())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { data -> data }
            .subscribe(
                { books ->
                    livedata?.postValue(books)
                },
                { error ->

                }
            )
    }
    @SuppressLint("CheckResult")

    fun EditOrder(id:Long,livedata: MutableLiveData<EditOrder>?) {

        getServergetway().EditOrder("1",id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { data -> data }
            .subscribe(
                { books ->
                    livedata?.postValue(books)
                },
                { error ->

                }
            )
    }
    @SuppressLint("CheckResult")

    fun ChangePassword(password:String,livedata: MutableLiveData<EditOrder>?) {

        getServergetway().ChangePassword(password,PreferenceHelper.getUserId().toLong())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { data -> data }
            .subscribe(
                { books ->
                    livedata?.postValue(books)
                },
                { error ->

                }
            )
    }
    @SuppressLint("CheckResult")
    fun GetMyorders(livedata: MutableLiveData<List<Myorders>>?) {

        getServergetway().GetMyorders()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { data -> data }
            .subscribe(
                { books ->
                    livedata?.postValue(books.myorders)
                },
                { error ->

                }
            )
    }
    @SuppressLint("CheckResult")
    fun GetMyDeialyReport(
        auth:String,
        livedata: MutableLiveData<List<Report>>?
    ) {

        getServergetway().GetMyDeialyReport()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { data -> data }
            .subscribe(
                { books ->
                    livedata?.postValue(books.orders)
                },
                { error ->
                    print(error)
                }
            )
    }
    @SuppressLint("CheckResult")
    fun GetMyMonthReport(
        auth:String,
        from:String,
        to:String,
        livedata: MutableLiveData<List<Report>>?
    ) {

        getServergetway().GetDatesReport(from,to)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { data -> data }
            .subscribe(
                { books ->
                    livedata?.postValue(books.orders)
                },
                { error ->
                    print(error)
                }
            )
    }
    @SuppressLint("CheckResult")

    fun getMyOffices(livedata: MutableLiveData<List<LoginData>>?) {

        getServergetway().Myoffices()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { data -> data }
            .subscribe(
                { success ->
                    livedata?.postValue(success.myoffices)
                },
                { error ->

                }
            )
    }
    @SuppressLint("CheckResult")

    fun getMyTrans(livedata: MutableLiveData<List<Datatrans>>?) {

        getServergetway().MyTrans()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { data -> data }
            .subscribe(
                { success ->
                    livedata?.postValue(success.data)
                },
                { error ->

                }
            )
    }
    @SuppressLint("CheckResult")
    fun GetTermsData(livedata: MutableLiveData<Terms>?) {

        getServergetway().GetTerms()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { data -> data }
            .subscribe(
                { books ->
                    livedata?.postValue(books)
                },
                { error ->
                    print(error)
                }
            )
    }


    @SuppressLint("CheckResult")
    fun GetPartnersData(livedata: MutableLiveData<List<PartnersModel>>?) {

        getServergetway().GetPartnersData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { data -> data }
            .subscribe(
                { books ->
                    livedata?.postValue(books)
                },
                { error ->
                    print(error)
                }
            )
    }

    @SuppressLint("CheckResult")
    fun PrintReport(oopo:String,auth:String,livedata: MutableLiveData<Buypackge>?) {

        getServergetway().PrintReport(oopo,auth)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { data -> data }
            .subscribe(
                { books ->
                    livedata?.postValue(books)
                },
                { error ->
                    print(error)
                }
            )
    }

    @SuppressLint("CheckResult")
    fun GetMyImages(livedata: MutableLiveData<List<SliderElement>>?) {

        getServergetway().SliderData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { data -> data }
            .subscribe(
                { books ->
                    livedata?.postValue(books.data)
                },
                { error ->
                    print(error)
                }
            )
    }

    @SuppressLint("CheckResult")
    fun ConfirmOrder(id:Long,livedata: MutableLiveData<EditOrder>?) {

         getServergetway().EditOrderConfirm("1",id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

            .subscribe(
                { books ->
                    livedata?.postValue(books)
                },
                { error ->
                    print(error)
                }
            )
    }
    @SuppressLint("CheckResult")

    fun Transactions(user_id:String,mobile:String,value:String,livedata: MutableLiveData<Trans>?,compiste: CompositeDisposable) {

        compiste .add(   getServergetway().Transactions(user_id,value,mobile)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

            .subscribe(
                { books ->
                    livedata?.postValue(books)
                },
                { error ->
                    print(error)
                }
            ))
    }
    fun getServergetway () : APIServices
    {
        return ApiClient.getClient().create(APIServices::class.java)
    }
}

