package com.gebeya.parking_lot.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gebeya.parking_lot.data.datapreference.DataStoreRepository
import com.gebeya.parking_lot.data.network.model.LotResponse
import com.gebeya.parking_lot.data.network.model.Reserve
import com.gebeya.parking_lot.data.network.model.TimeItemResponse
import com.gebeya.parking_lot.data.network.model.Vehicle
import com.gebeya.parking_lot.data.network.repository.LotRepository
import com.gebeya.parking_lot.data.network.repository.OpRepository
import com.gebeya.parking_lot.domain.repository.Response
import com.gebeya.parking_lot.domain.repository.VehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val lotRepository: LotRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val vehicleRepository: VehicleRepository,
    private val opRepository: OpRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var networkError: String? by mutableStateOf(null)

    val id = savedStateHandle.get<Int>("id")

    val isLoading = mutableStateOf(false)

    val lotError = mutableStateOf("")

    val lot = mutableStateOf<LotResponse?>(
        null
    )

    val durationError = mutableStateOf("")
    val reserveError = mutableStateOf("")

    val vehicleList = mutableStateOf(
        listOf<Vehicle?>(
            null
        )
    )

    val vehicleError = mutableStateOf("")

    val opTimeError = mutableStateOf("")

    val opTimes = mutableStateOf<List<TimeItemResponse>?>(
        null
    )


    fun getLotById() {
        viewModelScope.launch {
            val token = dataStoreRepository.getAuthenticationToken()
            token.let { flow ->
                flow.collect { tokenValue ->
                    tokenValue?.let {
                        val tokenWithoutQuotes = tokenValue.replace("\"", "")
                        id?.let {
                            when (val response = lotRepository.getLotById(tokenWithoutQuotes, id)) {
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

    fun validateInput(duration: String) {

        if (duration.isNullOrEmpty() || duration.isNullOrBlank()) {
            durationError.value = "Duration is required"
        } else {
            durationError.value = ""
        }
    }

    fun getVehicles() {
        viewModelScope.launch {
            val token = dataStoreRepository.getAuthenticationToken()
            println(token)
            token.let { flow ->
                flow.collect { tokenValue ->
                    tokenValue?.let {
                        val tokenWithoutQuotes = tokenValue.replace("\"", "")
                        when (val response = vehicleRepository.getVehicles(tokenWithoutQuotes)) {
                            is Response.Fail -> {
                                vehicleError.value = response.errorMessage ?: ""
                            }

                            is Response.Success -> {
                                vehicleList.value = response.data ?: listOf()
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

    fun addReservation(reserve: Reserve) {
        viewModelScope.launch {
            val token = dataStoreRepository.getAuthenticationToken()
            println(token)
            token.let { flow ->
                flow.collect { tokenValue ->
                    tokenValue?.let {
                        val tokenWithoutQuotes = tokenValue.replace("\"", "")
                        when (val response = lotRepository.createReserve(
                            tokenWithoutQuotes,
                            lot.value?.id ?: 0,
                            reserve
                        )) {
                            is Response.Fail -> {
                                reserveError.value = response.errorMessage ?: ""
                            }

                            is Response.Success -> {
                                reserveError.value = ""
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

}