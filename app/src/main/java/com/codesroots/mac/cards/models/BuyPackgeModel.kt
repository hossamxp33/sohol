package com.codesroots.mac.cards.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import org.jetbrains.anko.db.NULL
@Parcelize
data class Buypackge (
    val center: Center? = null,
    val order: Myorders? = null

):Parcelable {
}

data class EditOrder (
    val success: Boolean? = null,
    val data: List<Any?>? = null
)
@Parcelize
data class Center (
    val id: Int? = null,
    val err:String? = null,
    val price:String? = null,
    val buy_price:String? = null,
    val created:String? = null,
    @SerializedName("package_codes")
    val packageCodes: List<PackageCode>,
    val company: Company? = null
) : Parcelable{

}

data class Trans (
    val trans: Datatrans? = null,
    val data: List<Datatrans>? = null


)
data class Datatrans (
    val userID: Long? = null,
    val officeID: Long? = null,
    val centerID: Long? = null,
    val value: Long? = null,
    val created: String? = null,
    val user:LoginData? = null,
    val modified: String? = null,
    val id: Long? = null,
    val err:String? = null

)
data class LoginModel (
    val success: Boolean? = null,
    val data: LoginData? = null,
    val myoffices:List<LoginData>? = null
)

data class LoginData (
    val userid: Int? = null,
    val roomid: Long? = null,
    val email: String? = null,
    val mobile: String? = null,
    val groupid: String? = null,
    val username: String? = null,
    val token: String? = null,
    val message: String? = null,
    val id: Int? = null

    )

data class MyBalance (
    val companies: List<Company>,
    val usercredit: Int? = null
)

@Parcelize
data class Company (
    val id: Long? = null,
    val name: String? = null,
    val logo: String? = null,
    val code: String? = null,
    val created: String? = null
) : Parcelable
{

}

data class ReportDaily (
    val orders: List<Report>?= null,
    val myorders: Report?= null

)


data class Report (
    val id: Long,
    @SerializedName("package_id")
    val packageID: Long,
    @SerializedName("user_id")
    val userID: Long,
    @SerializedName("center_id")
    val centerID: Long,
    val created: String,
    val modified: String,
    val approve: Long,
    val mobile: String,
    val center: Centers,
    @SerializedName("package")
    val productPackage: Packagess
)

data class Centers (
    val id: Long,
    val username: String
)




data class Terms (
    val headline: String,
    val details: String,
    val mobile: String,
    val email: String,
    val fb: String
)

data class Packagess (
    val id: Long,
    val name: String,
    @SerializedName("company_id")
    val companyID: Long,
    var company:CompanyDatum?=null,
    val price: String,
    val photo:String? = null
)

data class Myordersdata (
    val orders: List<Myorders>?= null,
    val myorders: List<Myorders>?= null
)
@Parcelize
data class Myorders (
    val id: Long?= null,
    @SerializedName("package_id")
    val packageID: Long?= null,
    @SerializedName("user_id")
    val userID: Long?= null,
    @SerializedName("center_id")
    val centerID: Long?= null,
    val created: String?= null,
    val modified: String?= null,
    val approve: Long?= null,
    val mobile: String?= null,
    val name: String?= null,
    @SerializedName("package")
    val myordersPackage: Package?= null
) : Parcelable

@Parcelize
data class Package (
    val id: Long,
    val name: String,
    @SerializedName("company_id")
    val companyID: Long,
    val price: String,
    val photo: String,
    val company: Company
) : Parcelable

@Parcelize

data class PackageCode (
    val id: Long,
    val packageID: Long,
    val code: String,
    val active: Long,
    val cityID: Long,
    val created: String
) : Parcelable