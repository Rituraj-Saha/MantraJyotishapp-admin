package com.technologiagroup.adminappmantra.model.user

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserUpdate(
    @SerializedName("name")
    @Expose
    val name:String,
    @SerializedName("date_of_birth")
    @Expose
    val date_of_birth:String,
    @SerializedName("time_of_birth")
    @Expose
    val time_of_birth:String,
    @SerializedName("place_of_birth")
    @Expose
    val place_of_birth:String,
    @SerializedName("payment_status")
    @Expose
    val payment_status:String,
    @SerializedName("payment_amt")
    @Expose
    val payment_amt:String,
    @SerializedName("payment_date")
    @Expose
    val payment_date:String,
    @SerializedName("star_id")
    @Expose
    val star_id:String,
    @SerializedName("id")
    @Expose
    val id:Int,
):java.io.Serializable
