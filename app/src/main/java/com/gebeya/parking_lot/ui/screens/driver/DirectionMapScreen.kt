package com.gebeya.parking_lot.ui.screens.driver

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.gebeya.parking_lot.ui.theme.PDeepBlue
import com.gebeya.parking_lot.viewmodel.DirectionsMapViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun DirectionMapScreen(
){

    val directionsMapViewModel = hiltViewModel<DirectionsMapViewModel>()
    val location = directionsMapViewModel.location.collectAsState()
    val directionsState = directionsMapViewModel.directionsState.collectAsState()

    val polylinePoints = remember { mutableStateOf(listOf<LatLng>()) }

    if(location.value != null){

        Box(modifier = Modifier.fillMaxSize()){
            val cameraPosition = rememberCameraPositionState()

            LaunchedEffect(location.value) {
                cameraPosition.centerCamera(location.value ?: LatLng(0.0, 0.0))
            }
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPosition,
                properties = MapProperties(
                    mapType = MapType.TERRAIN, isMyLocationEnabled = true
                )
            ) {

                Marker(state = MarkerState(location.value ?: LatLng(0.0, 0.0)),
                    title = "My Location",
                )

                val lat = directionsMapViewModel.lat?.toDouble()
                val lng = directionsMapViewModel.lng?.toDouble()

                Marker(
                    state = MarkerState(LatLng(lat?:0.0, lng?:0.0)),
                    title = "Destination",
                )


                Polyline(
                    points = polylinePoints.value,
                    color = PDeepBlue,
                    width = 10f
                )
            }



                when (directionsState.value) {
                    is DirectionsMapViewModel.DirectionsState.Error -> {
                        CircularProgressIndicator()
                    }
                    DirectionsMapViewModel.DirectionsState.Loading -> {
                        println(directionsState)
                    }
                    is DirectionsMapViewModel.DirectionsState.Success -> {
                        val response = ((directionsState).value as DirectionsMapViewModel.DirectionsState.Success).response
                        println(response)

                        val coordinates = response.features[0].geometry.coordinates
                        println("coordinates: $coordinates")

                        polylinePoints.value = coordinates.map { LatLng(it[1], it[0]) }
                    }
                }
            }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

