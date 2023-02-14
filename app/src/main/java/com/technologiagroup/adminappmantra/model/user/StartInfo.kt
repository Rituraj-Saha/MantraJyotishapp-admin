package com.technologiagroup.adminappmantra.model.user

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StartInfo (
        @SerializedName("id")
        @Expose
        val id:Int? = null,
        @SerializedName("star_name")
        @Expose
        val star_name:String,
)