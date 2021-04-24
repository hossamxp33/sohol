package com.codesroots.mac.cards.models

import com.google.gson.annotations.SerializedName


data class CompanyData (
    val success: Boolean? = null,
    val companies: List<CompanyDatum>? = null,
    val data: List<CompanyDatum>? = null,

    var usercredit:Float? = null


)


data class CompanyDatum (
    val id: String? = null,
    val name: String? = null,
    val photo: String? = null,
    @SerializedName("logo")
    val src: String? = null,
    @SerializedName("price")
    val sprice: String? = null,
    val code: String? = null,
    val type: Int? = null,

    val created: String? = null,
    @SerializedName("company_id")
    val companyID: Int? = null
)
