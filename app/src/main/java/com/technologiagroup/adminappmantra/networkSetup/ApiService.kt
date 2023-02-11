package com.technologiyagroup.matrajayotish.networkSetup


import com.technologiyagroup.matrajayotish.model.user.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/api/user/get-pageable-user")
    suspend fun getAllUser(@Query("pageNo") pno:String,@Query("noOfitemPerPage") noOfitemPerPage:String) : UserResponse
}