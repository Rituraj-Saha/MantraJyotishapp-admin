package com.technologiyagroup.matrajayotish.repositories.user

import com.technologiyagroup.matrajayotish.model.user.NetworkResult
import com.technologiyagroup.matrajayotish.model.user.UserResponse
import com.technologiyagroup.matrajayotish.networkSetup.ApiService
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.catch

import javax.inject.Inject

class UserRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getAllUser(pno:String,noOfitemPerPage:String) = flow {
        emit(NetworkResult.Loading(true))
        val response = apiService.getAllUser(pno,noOfitemPerPage)
        emit(NetworkResult.Success(response))
    }.catch { e ->
        emit(NetworkResult.Failure(e.message ?: "Unknown Error"))
    }
}