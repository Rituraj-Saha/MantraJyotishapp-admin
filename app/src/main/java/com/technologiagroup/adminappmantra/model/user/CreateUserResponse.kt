package com.technologiagroup.adminappmantra.model.user

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.technologiyagroup.matrajayotish.model.user.ResponseBody

data class CreateUserResponse(
    @SerializedName("response_code")
    @Expose
    val responseCode:String,
    @SerializedName("response_msg")
    @Expose
    val responseMsg:String,
    @SerializedName("response_body")
    @Expose
    val responseBody:ResponseBody
)
