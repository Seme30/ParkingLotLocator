package com.gebeya.parking_lot.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gebeya.parking_lot.data.datapreference.DataStoreRepository
import com.gebeya.parking_lot.data.network.model.LotResponse
import com.gebeya.parking_lot.data.network.model.Provider
import com.gebeya.parking_lot.data.network.repository.LotRepository
import com.gebeya.parking_lot.domain.repository.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SuccessViewModel @Inject constructor(
    private val lotRepository: LotRepository,
    private val dataStoreRepository: DataStoreRepository
): ViewModel(){

    val lotError = mutableStateOf("")

    val lot = mutableStateOf<LotResponse?>(
        null
    )


    val isLoading = mutableStateOf(false)

    init {
        getLot()
    }

    private fun getLot(){
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



}