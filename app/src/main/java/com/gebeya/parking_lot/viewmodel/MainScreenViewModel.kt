package com.gebeya.parking_lot.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gebeya.parking_lot.data.datapreference.DataStoreRepository
import com.gebeya.parking_lot.data.directions.DirectionsRepository
import com.gebeya.parking_lot.data.directions.DirectionsResponse
import com.gebeya.parking_lot.data.network.model.LotResponse
import com.gebeya.parking_lot.data.network.model.Park
import com.gebeya.parking_lot.data.place.Place
import com.gebeya.parking_lot.data.place.PlaceRepository
import com.gebeya.parking_lot.domain.repository.DriverRepository
import com.gebeya.parking_lot.domain.repository.LocationServiceRepository
import com.gebeya.parking_lot.domain.repository.Response
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val locationServiceRepository: LocationServiceRepository,
    private val placeRepository: PlaceRepository,
    private val directionsRepository: DirectionsRepository,
    private val dataStoreRepository: DataStoreRepository,
    private val driverRepository: DriverRepository
): ViewModel(){

    sealed class DirectionsState {
        object Loading : DirectionsState()
        data class Success(val response: DirectionsResponse) : DirectionsState()
        data class Error(val exception: Exception) : DirectionsState()
    }

    val location: MutableStateFlow<LatLng?> = MutableStateFlow(null)
    val searchResults = mutableStateOf<Response<List<Place>>>(Response.Loading())
    private val _directionsState = MutableStateFlow<DirectionsState>(DirectionsState.Loading)
    val directionsState: StateFlow<DirectionsState> = _directionsState

    init {
        getLocation()
    }

    private fun getLocation(){
        viewModelScope.launch {
            locationServiceRepository.requestLocationUpdate().collect{
                location.value = it
            }
        }
    }

    fun getDirections(lonlat1: String, lonlat2: String) {
        viewModelScope.launch {
            try {
                val response = directionsRepository.getDirections(lonlat1, lonlat2)
                _directionsState.value = DirectionsState.Success(response)
            } catch (e: Exception) {
                _directionsState.value = DirectionsState.Error(e)
            }
        }
    }


    fun searchPlaces(query: String) {
        viewModelScope.launch {
            searchResults.value = Response.Loading()
            try {
                val results = placeRepository.searchPlaces(query)
                searchResults.value = Response.Success(results)
            } catch (e: Exception) {
                searchResults.value = Response.Fail(e.localizedMessage ?: "An error occurred")
            }
        }
    }

    val isLoading = mutableStateOf(false)

    val parkError = mutableStateOf("")

    val park = mutableStateOf<List<Park>?>(
        null
    )


    fun getParks(lat: Double, lng: Double){
        viewModelScope.launch {
            val token = dataStoreRepository.getAuthenticationToken()
            println(token)
            token.let { flow ->
                flow.collect { tokenValue ->
                    tokenValue?.let {
                        val tokenWithoutQuotes = tokenValue.replace("\"", "")
                        when(val response = driverRepository.getParks(tokenWithoutQuotes, latitude = lat, longitude = lng)) {
                            is Response.Fail -> {
                                parkError.value = response.errorMessage ?: ""
                            }

                            is Response.Success -> {
                                park.value = response.data
                                parkError.value = ""
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