package com.gebeya.parking_lot.ui.screens.driver

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.DirectionsCar
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.gebeya.parking_lot.domain.repository.Response
import com.gebeya.parking_lot.ui.components.PText
import com.gebeya.parking_lot.ui.components.montserratFamily
import com.gebeya.parking_lot.ui.theme.PBlue
import com.gebeya.parking_lot.ui.theme.PDeepBlue
import com.gebeya.parking_lot.ui.theme.PWhite
import com.gebeya.parking_lot.ui.theme.PWhite2
import com.gebeya.parking_lot.ui.util.Screen
import com.gebeya.parking_lot.data.network.model.Park
import com.gebeya.parking_lot.ui.components.PButton
import com.gebeya.parking_lot.viewmodel.MainScreenViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun MapViewScreen(
    mainScreenViewModel: MainScreenViewModel,
    navController: NavHostController,
){
    val location = mainScreenViewModel.location.collectAsState()
    var selectedMarker by remember { mutableStateOf<Marker?>(null) }
    var selectPark by remember {
        mutableStateOf<Park?>(null)
    }
    val coroutineScope = rememberCoroutineScope()
    val searchQuery = remember { mutableStateOf("") }
    val isSearchActive = remember { mutableStateOf(false) }
    val searchedLocation = remember { mutableStateOf<LatLng?>(null) }

    val searchResults = mainScreenViewModel.searchResults.value
    val parkList = mainScreenViewModel.park.value
    val polylinePoints = remember { mutableStateOf(listOf<LatLng>()) }


    if(location.value != null){
        mainScreenViewModel.getParks(location.value!!.latitude, location.value!!.longitude)
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
                    draggable = true,
                )

                parkList?.let { parks ->
                    parks.forEach { park ->
                        val latLng = LatLng(park.coordinate.latitude, park.coordinate.longitude)
                        Marker(
                            state = MarkerState(latLng),
                            title = park.title,
                            snippet = park.description,
                            onClick = {
                                selectedMarker = it
                                selectPark = park
                                true
                            }
                        )
                    }
                }
                Polyline(
                    points = polylinePoints.value,
                    color = PDeepBlue,
                    width = 10f
                )

                searchedLocation.value?.let { latLng ->
                    Marker(
                        state = MarkerState(latLng),
                        title = "Searched Location",
                        snippet = "This is the location you searched for",
                        onClick = {
                            println("Marker location: ${it.position}")
                            selectedMarker = it
                            true
                        }
                    )

                    LaunchedEffect(searchedLocation.value) {
                        cameraPosition.centerCamera(location.value ?: LatLng(0.0, 0.0))
                    }
                    mainScreenViewModel.getParks(latLng.latitude, latLng.latitude)

//                    mainScreenViewModel.getDirections(
//                        "${location.value?.longitude},${location.value?.latitude}",
//                        "${latLng.longitude},${latLng.latitude}"
//                    )
                }
            }



            // Display the CustomInfoWindow for the selected marker
            selectedMarker?.let { marker ->
                Dialog(onDismissRequest = { selectedMarker = null }) {
                    CustomInfoWindow(marker = marker, onClose = { selectedMarker = null }, navController = navController, selectPark)
                }
            }


            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 20.dp, start = 20.dp)
                ,
                onClick = {
                        navController.navigate(Screen.ProfileScreen.route)
                     }) {
                Icon(Icons.Rounded.Person, contentDescription = "Profile")
            }

            if (!isSearchActive.value){
                FloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = 20.dp, start = 90.dp)
                    ,
                    onClick = {
                        navController.navigate(Screen.VehicleList.route)
                    }) {
                    Icon(Icons.Rounded.DirectionsCar, contentDescription = "Car")
                }
            }



            if (isSearchActive.value) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(start = 40.dp, top = 20.dp)
                            .clip(CircleShape)
                            .background(
                                color = PWhite
                            )
                    ){

                        TextField(
                            value = searchQuery.value,
                            onValueChange = {
                                searchQuery.value = it
                            },
                            textStyle = TextStyle(
                                fontFamily = montserratFamily,
                            ),
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = PWhite2,
                                unfocusedContainerColor = PWhite2,
                                cursorColor = PBlue,
                                disabledTextColor = Color.White,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                            ),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(onSearch = {
                                mainScreenViewModel.searchPlaces(searchQuery.value)
                            }),
                            trailingIcon = {
                                IconButton(onClick = { isSearchActive.value = false
                                    searchQuery.value = ""
                                    mainScreenViewModel.searchResults.value = Response.Success(listOf())
                                }) {
                                    Icon(Icons.Filled.Close, contentDescription = "Close")
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = PWhite2,
                                    shape = CircleShape
                                )
                                .border(
                                    width = 0.dp,
                                    shape = CircleShape,
                                    color = PWhite
                                ),
                        )
                    }

                    Spacer(modifier = Modifier.height(1.dp))

                    when (searchResults) {
                        is Response.Loading -> {
                            // Show loading indicator
                            CircularProgressIndicator()
                        }
                        is Response.Success -> {
                            // Show search results
                            LazyColumn(
                                modifier = Modifier
                                    .padding(horizontal = 20.dp)
                                    .clip(
                                        RoundedCornerShape(20.dp)
                                    )
                            ) {
                                searchResults.data?.let {
                                    items(it.size) { index ->
                                        ListItem(
                                            headlineContent = { Text(it[index].displayName) },
                                            modifier = Modifier.clickable {
                                                val latLng = LatLng(it[index].lat.toDouble(), it[index].lon.toDouble())
                                                // send this latling to api to get a list of locations
                                                // related to this location and show the list of locations on the map
                                                coroutineScope.launch(Dispatchers.Main) {
                                                    searchedLocation.value = latLng
                                                    cameraPosition.centerCamera(latLng)
                                                }

                                                coroutineScope.launch (Dispatchers.Main){
                                                    val lonlat2 = "${latLng.longitude},${latLng.latitude}"
                                                    val lonlat1 = "${location.value!!.longitude}, ${location.value!!.latitude}"
                                                    mainScreenViewModel.getDirections(lonlat1, lonlat2)
                                                }

                                            }
                                        )
                                        Box(
                                            modifier = Modifier
                                                .height(1.dp)
                                                .background(PBlue)
                                        )
                                    }
                                }
                            }
                        }
                        is Response.Fail -> {
                            println("Error ${searchResults.errorMessage}")
                            // Show error message
                            Text(text = "Error Occurred!")
                        }
                    }
                }
            } else {
                FloatingActionButton(
                    onClick = { isSearchActive.value = true },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 20.dp, end = 20.dp)
                ) {
                    Icon(Icons.Filled.Search, contentDescription = "Search")
                }
            }
        }
    }else{
        Box(modifier = Modifier.fillMaxSize()){
            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 20.dp, start = 20.dp)
                ,
                onClick = {
                    navController.navigate(Screen.ProfileScreen.route)
                     }) {
                Icon(Icons.Rounded.Person, contentDescription = "Open drawer")
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){ CircularProgressIndicator() }
        }
    }
}

suspend fun CameraPositionState.centerCamera(location: LatLng){
    return animate(
        update = CameraUpdateFactory.newLatLngZoom( location, 15f ),
        durationMs = 2000
    )
}

@Composable
fun CustomInfoWindow(marker: Marker, onClose: () -> Unit, navController: NavHostController, selectPark:  Park?) {

    Card(
        modifier = Modifier
            .padding(16.dp)
            .clickable { onClose() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(20.dp)
        ) {
            marker.title?.let { PText(text = it, size = 20.sp, fontWeight = FontWeight.SemiBold) }
            marker.snippet?.let { PText(text = it, size = 14.sp) }
            Spacer(modifier = Modifier.height(20.dp))
            PButton(
                text = "Go to details",
                click = {
                    println("$selectPark")
                selectPark?.let {
                    navController.navigate("${Screen.Detail.route}/${it.id}")
                }
            })
        }
    }
}