package com.gebeya.parking_lot.viewmodel


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gebeya.parking_lot.data.directions.DirectionsRepository
import com.gebeya.parking_lot.data.directions.DirectionsResponse
import com.gebeya.parking_lot.domain.repository.LocationServiceRepository
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DirectionsMapViewModel @Inject constructor(
    private val locationServiceRepository: LocationServiceRepository,
    private val directionsRepository: DirectionsRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    val lat = savedStateHandle.get<String>("lat")
    val lng = savedStateHandle.get<String>("lng")


    sealed class DirectionsState {
        object Loading : DirectionsState()
        data class Success(val response: DirectionsResponse) : DirectionsState()
        data class Error(val exception: Exception) : DirectionsState()
    }

    val location: MutableStateFlow<LatLng?> = MutableStateFlow(null)
    private val _directionsState = MutableStateFlow<DirectionsState>(DirectionsState.Loading)
    val directionsState: StateFlow<DirectionsState> = _directionsState

    init {
        getLocation()
    }

    private fun getLocation(){
        viewModelScope.launch {
            locationServiceRepository.requestLocationUpdate().collect{
                location.value = it
                getDirections(
                    "${it?.longitude},${it?.latitude}",
                    "${lng},${lat}"
                )
            }
        }
    }

    fun getDirections(lonlat1: String, lonlat2: String) {
        println("Start: $lonlat1")
        println("End: $lonlat2")
        viewModelScope.launch {
            try {
                val response = directionsRepository.getDirections(lonlat1, lonlat2)
                _directionsState.value = DirectionsState.Success(response)
            } catch (e: Exception) {
                _directionsState.value = DirectionsState.Error(e)
            }
        }
    }


}