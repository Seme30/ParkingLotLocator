package com.gebeya.parking_lot.ui.screens.driver


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.gebeya.parking_lot.data.network.model.Vehicle
import com.gebeya.parking_lot.ui.components.PButton
import com.gebeya.parking_lot.ui.components.PText
import com.gebeya.parking_lot.ui.components.VehicleListItem
import com.gebeya.parking_lot.ui.components.montserratFamily
import com.gebeya.parking_lot.ui.theme.PBlue
import com.gebeya.parking_lot.ui.theme.PWhite
import com.gebeya.parking_lot.viewmodel.VehicleListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleListScreen(
    navController: NavHostController
) {
//    val vehicles = listOf(
//        Vehicle(name = "Toyota", model = "Toyota Prius", plate = "8H3Y2H3", year = 2023),
//        Vehicle(name = "Green Toyota", model ="Toyota Corolla", plate = "9U3Y2H3", year=2020)
//    )

    val context = LocalContext.current

    val vehicleListViewModel = hiltViewModel<VehicleListViewModel>()
    vehicleListViewModel.getVehicles()
    val showDialog = remember { mutableStateOf(false) }

    val name = remember {
        mutableStateOf("")
    }
    val vehicleModel = remember { mutableStateOf("") }
    val licensePlate = remember { mutableStateOf("") }
    val year = remember { mutableStateOf<String?>("") }

    if (showDialog.value){
        Dialog(onDismissRequest = { showDialog.value = false }) {
            Column(
                modifier = Modifier
                    .background(PWhite, shape = RoundedCornerShape(10))
                    .padding(20.dp)
                ,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = name.value,
                    onValueChange = { name.value = it },
                    label = { Text("Name") },
                    singleLine = true
                )
                if (vehicleListViewModel.nameError.value.isNotEmpty()){
                    Text(text = vehicleListViewModel.nameError.value,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(top = 10.dp),
                        style = TextStyle(
                            color = Color.Red, fontFamily = montserratFamily, fontSize = 14.sp
                        )
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = vehicleModel.value,
                    onValueChange = { vehicleModel.value = it },
                    label = { Text("Vehicle Model") },
                    singleLine = true
                )
                if (vehicleListViewModel.modelError.value.isNotEmpty()){
                    Text(text = vehicleListViewModel.modelError.value,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(top = 10.dp),
                        style = TextStyle(
                            color = Color.Red, fontFamily = montserratFamily, fontSize = 14.sp
                        )
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = licensePlate.value,
                    onValueChange = { licensePlate.value = it },
                    label = { Text("License Plate") },
                    singleLine = true
                )
                if (vehicleListViewModel.plateError.value.isNotEmpty()){
                    Text(text = vehicleListViewModel.plateError.value,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(top = 10.dp),
                        style = TextStyle(
                            color = Color.Red, fontFamily = montserratFamily, fontSize = 14.sp
                        )
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = year.value ?:"",
                    onValueChange = { year.value = it },
                    label = { Text("Year") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(32.dp))
                PButton(
                    text = "Add Vehicle",
                    isWhite = false,
                    click = {
                        vehicleListViewModel.validateInput(model = vehicleModel.value,  plate = licensePlate.value, name = name.value)
                        if(vehicleListViewModel.nameError.value.isEmpty() &&
                            vehicleListViewModel.plateError.value.isEmpty()
                            ){
                            vehicleListViewModel.addVehicle(
                                Vehicle(
                                    id = null,
                                    name = name.value,
                                    model = vehicleModel.value,
                                    plate = licensePlate.value,
                                    year = year.value?.toInt()
                                )
                            )
                            if(vehicleListViewModel.addError.value.isEmpty()){
                                Toast.makeText(context, "Vehicle Added Successfully", Toast.LENGTH_LONG).show()
                                showDialog.value = false
                            }
                        }

                        name.value = ""
                        vehicleModel.value = ""
                        licensePlate.value = ""
                        year.value = null
                    }
                )
            }
        }

    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { PText("Vehicles", size = 15.sp) },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = PBlue,
                contentColor = PWhite,
                onClick = {
                    showDialog.value = true
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Vehicle")
            }
        }
    ) { paddingValue ->
        LazyColumn(
            modifier = Modifier.padding(paddingValue),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val vehicles = vehicleListViewModel.vehicleList.value
            val isEmpty = vehicles.isEmpty()
            val isNull = vehicles.all {
                it == null
            }

            items(if (isEmpty || isNull) 1 else vehicles.size) { index ->
                if (isEmpty) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        PText(text = "No Vehicles Registered Yet!", size = 15.sp)
                    }
                } else if(isNull){
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                else {
                    vehicles[index]?.let { VehicleListItem(it, vehicleListViewModel) }
                }
            }
        }
    }
}

