package com.gebeya.parking_lot.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gebeya.parking_lot.data.datapreference.DataStoreRepository
import com.gebeya.parking_lot.data.network.model.Driver
import com.gebeya.parking_lot.data.network.model.Lot
import com.gebeya.parking_lot.data.network.model.LotResponse
import com.gebeya.parking_lot.data.network.repository.LotRepository
import com.gebeya.parking_lot.domain.repository.LocationServiceRepository
import com.gebeya.parking_lot.domain.repository.Response
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddParkingViewModel @Inject constructor(
    private val lotRepository: LotRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val locationServiceRepository: LocationServiceRepository
): ViewModel(){


    val lotData = mutableStateOf<LotResponse?>(
        null
    )
    val lotError = mutableStateOf("")
    val isLoading = mutableStateOf(false)
    val location: MutableStateFlow<LatLng?> = MutableStateFlow(null)

    val nameError = mutableStateOf("")
    val addressError = mutableStateOf("")
    val capacityError = mutableStateOf("")
    val parkingTypeError = mutableStateOf("")
    val imageError = mutableStateOf("")

    init {
        getLocation()
    }
    private fun getLocation(){
        viewModelScope.launch {
            locationServiceRepository.requestLocationUpdate().collect{
                println("location $it")
                location.value = it
            }
        }
    }


    fun addLot(
        lot: Lot
    ){
        viewModelScope.launch {
            val token = dataStoreRepository.getAuthenticationToken()
            println(token)
            token.let { flow ->
                flow.collect { tokenValue ->
                    tokenValue?.let {
                        val tokenWithoutQuotes = tokenValue.replace("\"", "")
                        when(val response = lotRepository.createLot(tokenWithoutQuotes, lot)) {
                            is Response.Fail -> {
                                lotError.value = response.errorMessage ?: ""
                            }

                            is Response.Success -> {
                                lotData.value = response.data
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

    fun validateInput(
        name: String,
        address: String,
        capacity: String,
        parkingType: String,
        images: List<String>
        ) {

        nameError.value = if (name.isNullOrEmpty() || name.isNullOrBlank()) {
            "Name is required"
        } else {
            ""
        }

        addressError.value = if (address.isNullOrEmpty() || address.isNullOrBlank()) {
            "Address is required"
        } else {
            ""
        }

        parkingTypeError.value = if (parkingType.isNullOrEmpty() || parkingType.isNullOrBlank()) {
            "Parking Type is required"
        } else {
            ""
        }

        capacityError.value = when {
            capacity.isNullOrEmpty() || capacity.isNullOrBlank() -> {
                "Capacity is required"
            }

            else -> {
                ""
            }
        }

        imageError.value = when{
            images.isEmpty() -> {
                "At least one image is required"
            }

            else -> {
                ""
            }
        }


    }


}