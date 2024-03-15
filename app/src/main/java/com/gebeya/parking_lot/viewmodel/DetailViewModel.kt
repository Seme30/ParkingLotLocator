package com.gebeya.parking_lot.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gebeya.parking_lot.data.datapreference.DataStoreRepository
import com.gebeya.parking_lot.data.network.model.LotResponse
import com.gebeya.parking_lot.data.network.repository.LotRepository
import com.gebeya.parking_lot.domain.repository.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val lotRepository: LotRepository,
    private val dataStoreRepository: DataStoreRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var networkError: String? by mutableStateOf(null)

    val id = savedStateHandle.get<Int>("id")

    val isLoading = mutableStateOf(false)

    val lotError = mutableStateOf("")

    val lot = mutableStateOf<LotResponse?>(
        null
    )


    fun getLotById(){
        viewModelScope.launch {
            val token = dataStoreRepository.getAuthenticationToken()
            token.let { flow ->
                flow.collect { tokenValue ->
                    tokenValue?.let {
                        val tokenWithoutQuotes = tokenValue.replace("\"", "")
                        id?.let {
                            when(val response = lotRepository.getLotById(tokenWithoutQuotes, id)) {
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

}