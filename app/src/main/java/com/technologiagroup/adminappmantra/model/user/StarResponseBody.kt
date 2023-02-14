package com.technologiagroup.adminappmantra.model.user

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.technologiyagroup.matrajayotish.model.user.User

data class StarResponseBody(
    @SerializedName("starInfo")
    @Expose
    val starInfo: StartInfo
)
