package com.technologiyagroup.matrajayotish.viewModel.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technologiagroup.adminappmantra.model.user.StarResponse
import com.technologiagroup.adminappmantra.model.user.UserResponseForCreateUser
import com.technologiyagroup.matrajayotish.model.user.NetworkResult
import com.technologiyagroup.matrajayotish.model.user.ResponseBody
import com.technologiyagroup.matrajayotish.model.user.User
import com.technologiyagroup.matrajayotish.model.user.UserResponse
import com.technologiyagroup.matrajayotish.repositories.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel  @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel(){

//    private var _movieResponse = MutableLiveData<NetworkResult<List<User>>>()
    private var _userResponse = MutableLiveData<NetworkResult<UserResponse>>()
    val userResponse: LiveData<NetworkResult<UserResponse>> = _userResponse

    private var _userResponse2 = MutableLiveData<NetworkResult<UserResponseForCreateUser>>()
    val userResponse2: LiveData<NetworkResult<UserResponseForCreateUser>> = _userResponse2

    private var _starResponse = MutableLiveData<NetworkResult<StarResponse>>()
    val starResponse: LiveData<NetworkResult<StarResponse>> = _starResponse
//    init {
//        u_phone = phone
//        getUser(u_phone)
//    }
    suspend fun getAllUser(pno:String,noOfitemPerPage:String) {
        viewModelScope.launch {
            userRepository.getAllUser(pno,noOfitemPerPage).collect {
                _userResponse.postValue(it)
            }
        }
    }
    suspend fun createUser(user:User) {
        viewModelScope.launch {
            userRepository.createUser(user).collect {
                _userResponse2.postValue(it)
            }
        }
    }

    suspend fun getAllStars() {
        viewModelScope.launch {
            userRepository.getAllStars().collect {
                _starResponse.postValue(it)
            }
        }
    }
}