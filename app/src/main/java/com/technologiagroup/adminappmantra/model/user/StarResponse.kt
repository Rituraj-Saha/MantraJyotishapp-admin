package com.technologiagroup.adminappmantra.model.user

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class StarResponse (

    @SerializedName("response_code")
    @Expose
    val responseCode:String,
    @SerializedName("response_msg")
    @Expose
    val responseMsg:String,
    @SerializedName("response_body")
    @Expose
    val responseBody:List<StarResponseBody>

    )