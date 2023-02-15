package com.technologiyagroup.matrajayotish.networkSetup


import com.technologiagroup.adminappmantra.model.user.StarResponse
import com.technologiagroup.adminappmantra.model.user.UserResponseForCreateUser
import com.technologiyagroup.matrajayotish.model.user.User
import com.technologiyagroup.matrajayotish.model.user.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("/api/user/get-pageable-user")
    suspend fun getAllUser(@Query("pageNo") pno:String,@Query("noOfitemPerPage") noOfitemPerPage:String) : UserResponse
    @POST("api/user/admin/create-user")
    suspend fun createUser(@Body requestModel: User):UserResponseForCreateUser

    @GET("/api/user/star/admin/get-all-stars")
    suspend fun getAllStars() : StarResponse

}