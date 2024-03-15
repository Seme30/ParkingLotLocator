package com.gebeya.parking_lot.ui.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.gebeya.parking_lot.R
import com.gebeya.parking_lot.data.network.model.Vehicle
import com.gebeya.parking_lot.ui.theme.PWhite
import com.gebeya.parking_lot.ui.util.Screen
import com.gebeya.parking_lot.viewmodel.VehicleListViewModel


@Composable
fun VehicleListItem(
    vehicle: Vehicle,
    vehicleListViewModel: VehicleListViewModel
) {

    val context = LocalContext.current

    val isDeleteVisible = remember {
        mutableStateOf(false)
    }

    val editDialog = remember {
        mutableStateOf(false)
    }

    if(editDialog.value){
        EditVehicleDialog(context = context, editDialog = editDialog, vehicle = vehicle, vehicleListViewModel = vehicleListViewModel)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = R.drawable.img), // Replace with your vehicle image resource
            contentDescription = "Vehicle Image",
            modifier = Modifier.size(64.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            PText(text = vehicle.name, size = 15.sp)
            Spacer(modifier = Modifier.height(10.dp))
            PText(text = vehicle.plate, size = 16.sp)
            Spacer(modifier = Modifier.height(10.dp))
            PText(text = vehicle.year.toString(), size = 16.sp)
        }

        IconButton(onClick = {
            editDialog.value = true
        }) {
            Icon(Icons.Filled.Edit, contentDescription = "Edit")
        }
        Spacer(modifier = Modifier.width(10.dp))

        IconButton(onClick = {
            isDeleteVisible.value = true
        }) {
            Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Color.Red)
        }
    }

    Divider(
        modifier = Modifier.fillMaxWidth()
    )

    if (isDeleteVisible.value) {
        PAlertDialog(
            onDismissRequest = {
                isDeleteVisible.value = false
            },
            onConfirmation = {
                vehicle.id?.let { vehicleListViewModel.deleteVehicle(it.toInt()) }
                isDeleteVisible.value = false
            },
            dialogTitle = "Delete Confirmation",
            dialogText = "Are you sure you want to delete this item?",
            icon = Icons.Filled.Warning
        )
    }
}

@Composable
fun EditVehicleDialog(context: Context, editDialog: MutableState<Boolean>, vehicle: Vehicle, vehicleListViewModel: VehicleListViewModel){

    val id = remember {
        mutableStateOf(vehicle.id)
    }
    val name = remember {
        mutableStateOf(vehicle.name)
    }
    val vehicleModel = remember { mutableStateOf(vehicle.model) }
    val licensePlate = remember { mutableStateOf(vehicle.plate) }
    val year = remember { mutableStateOf(vehicle.year.toString()) }


    Dialog(onDismissRequest = { editDialog.value = false }) {
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
                text = "Update Vehicle",
                isWhite = false,
                click = {
                    vehicleListViewModel.validateInput(model = vehicleModel.value,  plate = licensePlate.value, name = name.value)
                    if(vehicleListViewModel.nameError.value.isEmpty() &&
                        vehicleListViewModel.plateError.value.isEmpty()
                    ){
                        vehicleListViewModel.updateVehicle(
                            Vehicle(
                                id = id.value,
                                name = name.value,
                                model = vehicleModel.value,
                                plate = licensePlate.value,
                                year = year.value.toInt()
                            )
                        )
                        if(vehicleListViewModel.editError.value.isEmpty()){
                            Toast.makeText(context, "Vehicle Updated Successfully", Toast.LENGTH_LONG).show()
                            editDialog.value = false
                        }
                    }

                    name.value = ""
                    vehicleModel.value = ""
                    licensePlate.value = ""
                    year.value = ""
                }
            )
        }
    }


}
