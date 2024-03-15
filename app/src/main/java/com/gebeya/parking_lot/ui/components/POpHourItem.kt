package com.gebeya.parking_lot.ui.components

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Watch
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.gebeya.parking_lot.data.network.model.TimeItem
import com.gebeya.parking_lot.data.network.model.TimeItemResponse
import com.gebeya.parking_lot.ui.theme.PWhite
import com.gebeya.parking_lot.viewmodel.ParkingLotViewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun POpHourItem(
    timeItem: TimeItemResponse,
    parkingLotViewModel: ParkingLotViewModel
) {

    val context = LocalContext.current

    val isDeleteVisible = remember {
        mutableStateOf(false)
    }

    val editDialog = remember {
        mutableStateOf(false)
    }

    if(editDialog.value){
        EditTimeItemDialog(context = context, editDialog = editDialog, timeItem , parkingLotViewModel = parkingLotViewModel)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Row {
                Icon(imageVector = Icons.Default.Watch, contentDescription = "OpHours")
                Spacer(modifier = Modifier.height(10.dp))
                PText(text = timeItem.startTime, size = 15.sp)
                Spacer(modifier = Modifier.height(10.dp))
                PText(text = " - ${timeItem.endTime}", size = 16.sp)
            }
            
            PText(text = "Price: ${timeItem.price}", size = 16.sp)
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
//                timeItem.id?.let { vehicleListViewModel.deleteVehicle(it.toInt()) }
                isDeleteVisible.value = false
            },
            dialogTitle = "Delete Confirmation",
            dialogText = "Are you sure you want to delete this item?",
            icon = Icons.Filled.Warning
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditTimeItemDialog(
    context: Context, 
    editDialog: MutableState<Boolean>, 
    timeItem: TimeItemResponse,
    parkingLotViewModel: ParkingLotViewModel
){

    val id = remember {
        mutableStateOf(timeItem.id)
    }

    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    val startTime = remember {
        mutableStateOf(LocalTime.parse(timeItem.startTime, formatter))
    }
    val endTime = remember {
        mutableStateOf(LocalTime.parse(timeItem.endTime, formatter))
    }
    val price = remember { mutableStateOf(timeItem.price.toString()) }


    Dialog(onDismissRequest = { editDialog.value = false }) {
        Column(
            modifier = Modifier
                .background(PWhite, shape = RoundedCornerShape(10))
                .padding(20.dp)
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PTimePicker(
                label = "Start Time",
                operationTime = { localtime ->
                startTime.value = localtime
            })
            Spacer(modifier = Modifier.height(16.dp))
            PTimePicker(
                label = "End Time",
                operationTime = { localtime ->
                endTime.value = localtime
            })
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = price.value,
                onValueChange = { price.value = it },
                label = { Text("Price") },
                singleLine = true
            )
//            if (vehicleListViewModel.plateError.value.isNotEmpty()){
//                Text(text = vehicleListViewModel.plateError.value,
//                    modifier = Modifier
//                        .fillMaxWidth(0.8f)
//                        .padding(top = 10.dp),
//                    style = TextStyle(
//                        color = Color.Red, fontFamily = montserratFamily, fontSize = 14.sp
//                    )
//                )
//            }
            Spacer(modifier = Modifier.height(32.dp))
            PButton(
                text = "Update Operation Hour",
                isWhite = false,
                click = {
//                    vehicleListViewModel.validateInput(model = vehicleModel.value,  plate = licensePlate.value, name = name.value)
//                    if(vehicleListViewModel.nameError.value.isEmpty() &&
//                        vehicleListViewModel.plateError.value.isEmpty()
//                    ){
//                        vehicleListViewModel.updateVehicle(
//                            Vehicle(
//                                id = id.value,
//                                name = name.value,
//                                model = vehicleModel.value,
//                                plate = licensePlate.value,
//                                year = year.value.toInt()
//                            )
//                        )
//                        if(vehicleListViewModel.editError.value.isEmpty()){
//                            Toast.makeText(context, "Vehicle Updated Successfully", Toast.LENGTH_LONG).show()
//                            editDialog.value = false
//                        }
//                    }

                    price.value = ""
                }
            )
        }
    }


}
