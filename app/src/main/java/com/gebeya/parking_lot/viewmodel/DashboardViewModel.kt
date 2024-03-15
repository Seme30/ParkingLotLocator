package com.gebeya.parking_lot.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gebeya.parking_lot.data.datapreference.DataStoreRepository
import com.gebeya.parking_lot.data.network.model.Driver
import com.gebeya.parking_lot.data.network.model.LotResponse
import com.gebeya.parking_lot.data.network.model.Provider
import com.gebeya.parking_lot.data.network.repository.LotRepository
import com.gebeya.parking_lot.domain.repository.DriverRepository
import com.gebeya.parking_lot.domain.repository.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val driverRepository: DriverRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val lotRepository: LotRepository
): ViewModel() {

    val providerError = mutableStateOf("")

    val provider = mutableStateOf<Provider?>(
        null
    )


    val isLoading = mutableStateOf(false)

    val lotError = mutableStateOf("")

    val lot = mutableStateOf<LotResponse?>(
        null
    )


    fun getLot(){
        viewModelScope.launch {
            val token = dataStoreRepository.getAuthenticationToken()
            println(token)
            token.let { flow ->
                flow.collect { tokenValue ->
                    tokenValue?.let {
                        val tokenWithoutQuotes = tokenValue.replace("\"", "")
                        when(val response = lotRepository.getLot(tokenWithoutQuotes)) {
                            is Response.Fail -> {
                                lotError.value = response.errorMessage ?: ""
                            }

                            is Response.Success -> {
                                lot.value = response.data
                                lotError.value = ""
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

    fun getProvider(){
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







}