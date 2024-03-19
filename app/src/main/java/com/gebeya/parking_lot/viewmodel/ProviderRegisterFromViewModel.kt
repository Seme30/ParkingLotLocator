package com.gebeya.parking_lot.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gebeya.parking_lot.data.datapreference.DataStoreRepository
import com.gebeya.parking_lot.data.keystore.Role
import com.gebeya.parking_lot.data.network.model.Provider
import com.gebeya.parking_lot.data.network.model.ProviderResponse
import com.gebeya.parking_lot.domain.repository.DriverRepository
import com.gebeya.parking_lot.domain.repository.KeystoreRepository
import com.gebeya.parking_lot.domain.repository.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProviderRegisterFromViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val driverRepository: DriverRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val keystoreRepository: KeystoreRepository
): ViewModel() {

    val code = mutableStateOf(savedStateHandle.get<String>("code"))

    var firstNameError by mutableStateOf("")
    var lastNameError by mutableStateOf("")
    var emailError by mutableStateOf("")

    val phoneNumber = mutableStateOf<String?>("")

    init {
        getPhone()
    }

    private fun getPhone() {
        viewModelScope.launch {
            dataStoreRepository.readPhoneNumber().collect { savedPhoneNumber ->
                phoneNumber.value = savedPhoneNumber
            }
        }
    }

    fun validateInput(firstName: String, lastName: String, email: String){

        firstNameError = if(firstName.isNullOrEmpty() || firstName.isNullOrBlank()){
            "First Name is required"
        }else{
            ""
        }

        lastNameError = if(lastName.isNullOrEmpty() || lastName.isNullOrBlank()){
            "Last Name is required"
        }else{
            ""
        }

        emailError = when {
            email.isNullOrEmpty() || email.isNullOrBlank() -> {
                "Email is required"
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                "Invalid email format"
            }
            else -> {
                ""
            }
        }


    }

    val driverResponse = MutableLiveData<ProviderResponse?>()
    val errorMessage = MutableLiveData<String?>()
    val isLoading = MutableLiveData<Boolean>()

    fun createProvider(provider: Provider) {
        viewModelScope.launch {
            when (val response = driverRepository.createProvider(provider)) {
                is Response.Fail -> {
                    isLoading.value = false
                    errorMessage.value = response.errorMessage
                    println("Error: ${errorMessage.value}")
                }

                is Response.Loading -> {
                    isLoading.value = true
                    println("loading")
                }

                is Response.Success -> {
                    isLoading.value = false
                    driverResponse.value = response.data
                    println(driverResponse.value)
                    driverResponse.value?.token?.let {
                        dataStoreRepository.saveAuthenticationToken(
                            it
                        )
                        keystoreRepository.setRole(Role.Provider)
                    }
                }
            }
        }
    }


}