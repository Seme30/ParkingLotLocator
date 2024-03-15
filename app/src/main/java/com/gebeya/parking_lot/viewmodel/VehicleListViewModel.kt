package com.gebeya.parking_lot.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gebeya.parking_lot.data.datapreference.DataStoreRepository
import com.gebeya.parking_lot.data.network.model.Vehicle
import com.gebeya.parking_lot.domain.repository.Response
import com.gebeya.parking_lot.domain.repository.VehicleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehicleListViewModel @Inject constructor(
    private val vehicleRepository: VehicleRepository,
    private val dataStoreRepository: DataStoreRepository
): ViewModel() {

    val vehicleList = mutableStateOf(listOf<Vehicle?>(
        null
    ))

    val vehicleError = mutableStateOf("")

    val deleteError = mutableStateOf("")
    val addError = mutableStateOf<String>("")
    val editError = mutableStateOf("")
    val isLoading = mutableStateOf(false)
    val nameError = mutableStateOf("")
    val modelError = mutableStateOf("")
    val plateError = mutableStateOf("")



    fun addVehicle(vehicle: Vehicle){
        viewModelScope.launch {
            val token = dataStoreRepository.getAuthenticationToken()
            token.let {flow ->
                flow.collect{token ->
                    token?.let { tokenValue ->
                        val tokenWithoutQuotes = tokenValue.replace("\"", "")
                        when(val response = vehicleRepository.createVehicle(tokenWithoutQuotes,vehicle)){
                            is Response.Fail -> {
                                addError.value = response.errorMessage?:""
                                isLoading.value = false
                            }

                            is Response.Success -> {
                                addError.value = ""
                                isLoading.value = false
                                when(val getResponse = vehicleRepository.getVehicles(tokenWithoutQuotes)) {
                                    is Response.Fail -> {
                                        vehicleError.value = getResponse.errorMessage ?: ""
                                    }

                                    is Response.Success -> {
                                        vehicleList.value = getResponse.data ?: listOf()
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
    fun deleteVehicle(id: Int){
        viewModelScope.launch {
            val token = dataStoreRepository.getAuthenticationToken()
            token.let {flow ->
                flow.collect{token ->
                    token?.let { tokenValue ->
                        val tokenWithoutQuotes = tokenValue.replace("\"", "")
                        when(val response = vehicleRepository.deleteVehicle(tokenWithoutQuotes,id)){
                            is Response.Fail -> {
                                deleteError.value = response.errorMessage?:""
                                isLoading.value = false
                            }

                            is Response.Success -> {
                                deleteError.value = ""
                                isLoading.value = false

                                when(val getResponse = vehicleRepository.getVehicles(tokenWithoutQuotes)) {
                                    is Response.Fail -> {
                                        vehicleError.value = getResponse.errorMessage ?: ""
                                    }

                                    is Response.Success -> {
                                        vehicleList.value = getResponse.data ?: listOf()
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

    fun updateVehicle(vehicle: Vehicle){
        viewModelScope.launch {
            val token = dataStoreRepository.getAuthenticationToken()
            token.let {
                flow ->
                flow.collect{token ->
                    token?.let { tokenValue ->
                        val tokenWithoutQuotes = tokenValue.replace("\"", "")
                        when(val response = vehicleRepository.updateVehicle(token = tokenWithoutQuotes, id = vehicle.id?.toInt()?:0, vehicle = vehicle)) {
                            is Response.Fail -> {
                            editError.value = response.errorMessage?:""
                            isLoading.value = false
                        }

                            is Response.Success -> {
                            editError.value = ""
                            isLoading.value = false
                            when(val getResponse = vehicleRepository.getVehicles(tokenWithoutQuotes)) {
                                is Response.Fail -> {
                                    vehicleError.value = getResponse.errorMessage ?: ""
                                }

                                is Response.Success -> {
                                    vehicleList.value = getResponse.data ?: listOf()
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
    fun getVehicles(){
        viewModelScope.launch {
            val token = dataStoreRepository.getAuthenticationToken()
            println(token)
                    token.let { flow ->
                        flow.collect { tokenValue ->
                            tokenValue?.let {
                                val tokenWithoutQuotes = tokenValue.replace("\"", "")
                                when(val response = vehicleRepository.getVehicles(tokenWithoutQuotes)) {
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

    fun validateInput(
        name: String,
        plate: String,
        model: String
    ) {
        if (name.isNullOrEmpty() || name.isNullOrBlank()) {
            nameError.value = "Vehicle name is required"
        } else {
            nameError.value = ""
        }

        if (model.isNullOrEmpty() || model.isNullOrBlank()) {
            modelError.value = "Vehicle Model is required"
        } else {
            modelError.value = ""
        }

        if (plate.isNullOrEmpty() || plate.isNullOrBlank()) {
            plateError.value = "plate is required"
        } else {
            plateError.value = ""
        }
    }

}