package com.gebeya.parking_lot.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gebeya.parking_lot.data.network.model.PhoneVerifyResponse
import com.gebeya.parking_lot.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class VerifyViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val userRepository: UserRepository
): ViewModel() {

    val phoneNumber = mutableStateOf(savedStateHandle.get<String>("phone"))

    val phoneVerifyResponse = mutableStateOf(
        PhoneVerifyResponse(
            "",
            ""
        )
    )


    fun verifyOTP(phoneNo: String, otp: String){
        viewModelScope.launch {
            val response = userRepository.verifyPhone(
                phoneNo, otp
            )
            val rawJson = response.string()

            val jsonObject = JSONObject(rawJson)
            val token = jsonObject.getString("token")
            val code = jsonObject.getString("code")

            phoneVerifyResponse.value = PhoneVerifyResponse(token, code)
        }
    }



}