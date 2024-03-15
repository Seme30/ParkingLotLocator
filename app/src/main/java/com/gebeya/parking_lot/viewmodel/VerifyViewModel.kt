package com.gebeya.parking_lot.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gebeya.parking_lot.data.datapreference.DataStoreRepository
import com.gebeya.parking_lot.data.keystore.Role
import com.gebeya.parking_lot.data.network.model.PhoneVerifyResponse
import com.gebeya.parking_lot.domain.repository.KeystoreRepository
import com.gebeya.parking_lot.domain.repository.Response
import com.gebeya.parking_lot.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class VerifyViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val userRepository: UserRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val keystoreRepository: KeystoreRepository
): ViewModel() {

    val phoneNumber = mutableStateOf(savedStateHandle.get<String>("phone"))

    val phoneVerifyResponse = mutableStateOf(
        PhoneVerifyResponse(
            "",
            ""
        )
    )

    val isLoading = mutableStateOf(false)
    val codeError = mutableStateOf("")
    val incorrectCodeError = mutableStateOf("")


    fun verifyOTP(phoneNo: String, otp: String) {
        viewModelScope.launch {
            val response = userRepository.verifyPhone(phoneNo, otp)

            when (response) {
                is Response.Success -> {
                    val rawJson = response.data?.string()
                    val jsonObject = JSONObject(rawJson.toString())
                    val token = jsonObject.getString("token")
                    val code = jsonObject.getString("code")
                    println("code: $code")
                    when (code) {
                        "U100" -> {
                            dataStoreRepository.saveAuthenticationToken(token)
                        }
                        "U101" -> {
                            println("inside U101")
                            keystoreRepository.setRole(Role.Driver)
                            dataStoreRepository.saveAuthenticationToken(token)
                        }
                        "U102" -> {
                            println("inside 102")
                            keystoreRepository.setRole(Role.Provider)
                            dataStoreRepository.saveAuthenticationToken(token)
                        }
                    }
                    println("Success$code")
                    phoneVerifyResponse.value = PhoneVerifyResponse(token, code)
                }
                is Response.Fail -> {
                    if (response.errorMessage == "The code you entered is incorrect. Please try again.") {
                        // Handle incorrect code error
                        incorrectCodeError.value = "Incorrect code. Please try again."
                    } else {
                        // Handle other errors
                        codeError.value = response.errorMessage?:""
                    }
                }

                is Response.Loading -> {
                    isLoading.value = true
                }
            }
        }
    }




}