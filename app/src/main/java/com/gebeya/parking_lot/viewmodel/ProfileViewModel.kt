package com.gebeya.parking_lot.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gebeya.parking_lot.data.datapreference.DataStoreRepository
import com.gebeya.parking_lot.data.network.model.Driver
import com.gebeya.parking_lot.data.network.model.Provider
import com.gebeya.parking_lot.data.network.model.Vehicle
import com.gebeya.parking_lot.domain.repository.DriverRepository
import com.gebeya.parking_lot.domain.repository.KeystoreRepository
import com.gebeya.parking_lot.domain.repository.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val keystoreRepository: KeystoreRepository,
    private val driverRepository: DriverRepository
): ViewModel(){


    val driver = mutableStateOf<Driver?>(
        null
    )
    val driverError = mutableStateOf("")
    val updateDriverError = mutableStateOf("")

    val provider = mutableStateOf<Provider?>(
        null
    )
    val providerError = mutableStateOf("")
    val updateProviderError = mutableStateOf("")


    val isLoading = mutableStateOf(false)

    fun logout(){
        viewModelScope.launch {
                dataStoreRepository.deletePhoneNumber()
            dataStoreRepository.deleteAuthenticationToken()
            keystoreRepository.deleteRole()
        }
    }

    fun getProfile(){
        viewModelScope.launch {
            val token = dataStoreRepository.getAuthenticationToken()
            println(token)
            token.let { flow ->
                flow.collect { tokenValue ->
                    tokenValue?.let {
                        val tokenWithoutQuotes = tokenValue.replace("\"", "")
                        when(val response = driverRepository.getDriver(tokenWithoutQuotes)) {
                            is Response.Fail -> {
                                driverError.value = response.errorMessage ?: ""
                            }

                            is Response.Success -> {
                                driver.value = response.data
                                driverError.value = ""
                            }

                            is Response.Loading -> {
                                isLoading.value = true
                            }
                        }
                    }
                }
            }

        }
    }

    fun getParkingProfile(){
        viewModelScope.launch {
            val token = dataStoreRepository.getAuthenticationToken()
            println(token)
            token.let { flow ->
                flow.collect { tokenValue ->
                    tokenValue?.let {
                        val tokenWithoutQuotes = tokenValue.replace("\"", "")
                        when(val response = driverRepository.getProvider(tokenWithoutQuotes)) {
                            is Response.Fail -> {
                                providerError.value = response.errorMessage ?: ""
                            }

                            is Response.Success -> {
                                provider.value = response.data
                                providerError.value = ""
                            }

                            is Response.Loading -> {
                                isLoading.value = true
                            }
                        }
                    }
                }
            }

        }
    }

    fun updateProvider(newProvider: Provider){
        viewModelScope.launch {
            val token = dataStoreRepository.getAuthenticationToken()
            println(token)
            token.let { flow ->
                flow.collect { tokenValue ->
                    tokenValue?.let {
                        val tokenWithoutQuotes = tokenValue.replace("\"", "")
                        when(val response = driverRepository.updateProvider(tokenWithoutQuotes, id = newProvider.id?: 0, newProvider)) {
                            is Response.Fail -> {
                                updateProviderError.value = response.errorMessage ?: ""
                            }

                            is Response.Success -> {
                                provider.value = response.data
                                updateDriverError.value = ""
                                when(val getResponse = driverRepository.getDriver(tokenWithoutQuotes)) {
                                    is Response.Fail -> {
                                        providerError.value = getResponse.errorMessage ?: ""
                                    }

                                    is Response.Success -> {
                                        driver.value = getResponse.data
                                        providerError.value = ""
                                    }

                                    is Response.Loading -> {
                                        isLoading.value = true
                                    }
                                }
                            }

                            is Response.Loading -> {
                                isLoading.value = true
                            }
                        }
                    }
                }
            }

        }
    }

    fun updateDriver(newDriver: Driver){
        viewModelScope.launch {
            val token = dataStoreRepository.getAuthenticationToken()
            println(token)
            token.let { flow ->
                flow.collect { tokenValue ->
                    tokenValue?.let {
                        val tokenWithoutQuotes = tokenValue.replace("\"", "")
                        when(val response = driverRepository.updateDriver(tokenWithoutQuotes, id = newDriver.id?: 0, newDriver)) {
                            is Response.Fail -> {
                                updateDriverError.value = response.errorMessage ?: ""
                            }

                            is Response.Success -> {
                                driver.value = response.data
                                updateDriverError.value = ""
                                when(val getResponse = driverRepository.getDriver(tokenWithoutQuotes)) {
                                    is Response.Fail -> {
                                        driverError.value = getResponse.errorMessage ?: ""
                                    }

                                    is Response.Success -> {
                                        driver.value = getResponse.data
                                        driverError.value = ""
                                    }

                                    is Response.Loading -> {
                                        isLoading.value = true
                                    }
                                }
                            }

                            is Response.Loading -> {
                                isLoading.value = true
                            }
                        }
                    }
                }
            }

        }
    }
}