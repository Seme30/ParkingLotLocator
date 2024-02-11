package com.gebeya.parking_lot.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
//    val userRepository: UserRepository
) : ViewModel(){

    var phoneError by mutableStateOf("")

    fun validatePhoneNumber(
        phone: String,
    ){
        phoneError = if(phone.isNullOrEmpty() || phone.isNullOrBlank()){
            "phone is required"
        }else{
            ""
        }

    }


    fun authUser(
        phone: String
    ){
//        viewModelScope.launch {
//            userRepository.authUser(phone)
//        }
    }




}