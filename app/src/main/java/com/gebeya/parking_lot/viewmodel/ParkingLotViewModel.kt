package com.gebeya.parking_lot.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gebeya.parking_lot.data.datapreference.DataStoreRepository
import com.gebeya.parking_lot.data.network.model.LotResponse
import com.gebeya.parking_lot.data.network.model.Provider
import com.gebeya.parking_lot.data.network.model.TimeItem
import com.gebeya.parking_lot.data.network.model.TimeItemResponse
import com.gebeya.parking_lot.data.network.model.Vehicle
import com.gebeya.parking_lot.data.network.repository.LotRepository
import com.gebeya.parking_lot.data.network.repository.OpRepository
import com.gebeya.parking_lot.domain.repository.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ParkingLotViewModel @Inject constructor(
    private val lotRepository: LotRepository,
    private val opRepository: OpRepository,
    private val dataStoreRepository: DataStoreRepository
): ViewModel() {


    val isLoading = mutableStateOf(false)

    val lotError = mutableStateOf("")

    val lot = mutableStateOf<LotResponse?>(
        null
    )


    val opTimeError = mutableStateOf("")

    val opTimes = mutableStateOf<List<TimeItemResponse>?>(
        null
    )

    val addError = mutableStateOf("")
    val priceError = mutableStateOf("")


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

    fun getOpTime(){
        viewModelScope.launch {
            val token = dataStoreRepository.getAuthenticationToken()
            println(token)
            token.let { flow ->
                flow.collect { tokenValue ->
                    tokenValue?.let {
                        val tokenWithoutQuotes = tokenValue.replace("\"", "")
                        when(val response = opRepository.getOpTimes(tokenWithoutQuotes, lot.value?.id?:0)) {
                            is Response.Fail -> {
                                opTimeError.value = response.errorMessage ?: ""
                            }

                            is Response.Success -> {
                                opTimes.value = response.data
                                opTimeError.value = ""
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


    fun addOpTime(timeList: List<TimeItem>){
        viewModelScope.launch {
            val token = dataStoreRepository.getAuthenticationToken()
            token.let {flow ->
                flow.collect{token ->
                    token?.let { tokenValue ->
                        val tokenWithoutQuotes = tokenValue.replace("\"", "")
                        when(val response = opRepository.createOpTime(tokenWithoutQuotes, lot.value?.id?:0, timeList)){
                            is Response.Fail -> {
                                addError.value = response.errorMessage?:""
                                isLoading.value = false
                            }

                            is Response.Success -> {
                                addError.value = ""
                                isLoading.value = false
                                when(val getResponse = opRepository.getOpTimes(tokenWithoutQuotes, lot.value?.id?: 0)) {
                                    is Response.Fail -> {
                                        opTimeError.value = getResponse.errorMessage ?: ""
                                    }

                                    is Response.Success -> {
                                        opTimes.value = getResponse.data ?: listOf()
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


    fun validateInput(
        price: String
    ){
        priceError.value = if (price.isNullOrEmpty() || price.isNullOrBlank()) {
            "Price is required"
        } else {
            ""
        }
    }

}