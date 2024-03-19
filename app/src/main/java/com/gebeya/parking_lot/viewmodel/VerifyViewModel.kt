package com.gebeya.parking_lot.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.gebeya.parking_lot.data.datapreference.DataStoreRepository
import com.gebeya.parking_lot.data.keystore.Role
import com.gebeya.parking_lot.data.network.model.PhoneVerifyResponse
import com.gebeya.parking_lot.domain.repository.KeystoreRepository
import com.gebeya.parking_lot.domain.repository.Response
import com.gebeya.parking_lot.domain.repository.UserRepository
import com.gebeya.parking_lot.ui.util.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class VerifyViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository,
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


    fun verifyOTP(phoneNo: String, otp: String, navController: NavHostController) {
        viewModelScope.launch {
            when (val response = userRepository.verifyPhone(phoneNo, otp)) {
                is Response.Success -> {
                    val rawJson = response.data?.string()
                    val jsonObject = JSONObject(rawJson.toString())
                    val token = jsonObject.getString("token")
                    val code = jsonObject.getString("code")
                    println("code: $code")
                    when (code) {
                        "U100" -> {
                            navController.navigate(Screen.RoleSelection.route){
                                popUpTo(Screen.Welcome.route) { inclusive = true }
                            }
                        }
                        "U101" -> {
                            println("inside U101")
                            val deferred = async { keystoreRepository.setRole(Role.Driver) }
                            deferred.await() // Wait for the role to be set
                            dataStoreRepository.saveAuthenticationToken(token)
                            navController.navigate(Screen.MainScreen.route){
                                popUpTo(Screen.Welcome.route) { inclusive = true }
                            }
                        }
                        "U102" -> {
                            println("inside 102")
                            val deferred = async { keystoreRepository.setRole(Role.Provider) }
                            deferred.await()
                            dataStoreRepository.saveAuthenticationToken(token)
                            navController.navigate(Screen.ProviderMainScreen.route){
                                popUpTo(Screen.Welcome.route) { inclusive = true }
                            }
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