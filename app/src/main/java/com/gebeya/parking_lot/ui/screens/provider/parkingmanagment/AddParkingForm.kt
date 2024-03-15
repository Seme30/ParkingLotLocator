package com.gebeya.parking_lot.ui.screens.provider.parkingmanagment

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material.icons.filled.LocalParking
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ReduceCapacity
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.gebeya.parking_lot.data.network.model.Driver
import com.gebeya.parking_lot.data.network.model.Lot
import com.gebeya.parking_lot.ui.components.PButton
import com.gebeya.parking_lot.ui.components.PDropDown
import com.gebeya.parking_lot.ui.components.PImageSelector
import com.gebeya.parking_lot.ui.components.PText
import com.gebeya.parking_lot.ui.components.PTextField
import com.gebeya.parking_lot.ui.components.montserratFamily
import com.gebeya.parking_lot.ui.theme.PWhite
import com.gebeya.parking_lot.ui.util.Screen
import com.gebeya.parking_lot.viewmodel.AddParkingViewModel
import com.gebeya.parking_lot.viewmodel.ProviderRegisterFromViewModel


@Composable
fun AddParkingForm(
    navController: NavHostController
){

    val context = LocalContext.current

    val addParkingViewModel = hiltViewModel<AddParkingViewModel>()

    val name = remember {
        mutableStateOf("")
    }

    val address = remember {
        mutableStateOf("")
    }

    val capacity = remember {
        mutableStateOf("")
    }

    val parkingType = remember {
        mutableStateOf("")
    }

    val parkingTypes = listOf(
            "SURFACE",
    "UNDERGROUND",
    "STRUCTURED"
    )

    val images = remember {
        mutableStateOf(listOf<String>())
    }

    val imageUrl1: MutableState<String?> = remember {
        mutableStateOf(null)
    }

    val imageUrl2: MutableState<String?> = remember {
        mutableStateOf(null)
    }

    val imageUrl3: MutableState<String?> = remember {
        mutableStateOf(null)
    }

    val imageName: MutableState<String> = remember {
        mutableStateOf("Select Image")
    }

    val imageName2: MutableState<String> = remember {
        mutableStateOf("Select Image 2")
    }

    val imageName3: MutableState<String> = remember {
        mutableStateOf("Select Image 3")
    }

    val location = addParkingViewModel.location.collectAsState()



    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
            .background(color = PWhite)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PText(
            text = "Add Parking Lot Information",
            size = 20.sp,
            fontWeight = FontWeight.SemiBold,

            )
        Spacer(modifier = Modifier.height(10.dp))

        PTextField(
            inputState = name,
            leadingIcon = { Icon(Icons.Default.LocalParking, contentDescription = "first name") },
            label = "Parking Name",
            onError = addParkingViewModel.nameError.value.isNotEmpty(),
            placeholder = { PText(text = "parking name" , size = 14.sp, fontWeight = FontWeight.Thin) },
        )

        if (addParkingViewModel.nameError.value.isNotEmpty()){
            Text(text = addParkingViewModel.nameError.value,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(top = 10.dp),
                style = TextStyle(
                    color = Color.Red, fontFamily = montserratFamily, fontSize = 14.sp
                )
            )
        }

        PTextField(
            inputState = address,
            leadingIcon = { Icon(Icons.Default.AddLocation, contentDescription = "last name") },
            label = "Address",
            onError = addParkingViewModel.addressError.value.isNotEmpty(),
            placeholder = { PText(text = "local name" , size = 14.sp, fontWeight = FontWeight.Thin) },
//                onError = registerViewModel.phoneError.isNotEmpty(),
        )

        if (addParkingViewModel.addressError.value.isNotEmpty()){
            Text(text = addParkingViewModel.addressError.value,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(top = 10.dp),
                style = TextStyle(
                    color = Color.Red, fontFamily = montserratFamily, fontSize = 14.sp
                )
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        PTextField(
            inputState = capacity,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            leadingIcon = { Icon(Icons.Default.ReduceCapacity, contentDescription = "Email",) },
            label = "Lot Capacity",
            onError = addParkingViewModel.capacityError.value.isNotEmpty(),
            placeholder = { PText(text = "capacity", size = 14.sp, fontWeight = FontWeight.Thin) },
//                onError = registerViewModel.phoneError.isNotEmpty(),
        )
        if (addParkingViewModel.capacityError.value.isNotEmpty()){
            Text(text = addParkingViewModel.capacityError.value,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(top = 10.dp),
                style = TextStyle(
                    color = Color.Red, fontFamily = montserratFamily, fontSize = 14.sp
                )
            )
        }

        PText(text = "Parking Types", size = 15.sp, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Start, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 10.dp, bottom = 10.dp))

        PDropDown(inputState = parkingType, placeholder = "Parking Types", types = parkingTypes)
        if (addParkingViewModel.parkingTypeError.value.isNotEmpty()){
            Text(text = addParkingViewModel.parkingTypeError.value,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(top = 10.dp),
                style = TextStyle(
                    color = Color.Red, fontFamily = montserratFamily, fontSize = 14.sp
                )
            )
        }

        PText(text = "Select 3 Images", size = 15.sp, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Start, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, top = 20.dp))
        if (addParkingViewModel.imageError.value.isNotEmpty()){
            Text(text = addParkingViewModel.imageError.value,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(top = 10.dp),
                style = TextStyle(
                    color = Color.Red, fontFamily = montserratFamily, fontSize = 14.sp
                )
            )
        }
        PImageSelector(imageUrl = imageUrl1, label = "", imageName = imageName)
        PImageSelector(imageUrl = imageUrl2, label = "", imageName = imageName2)
        PImageSelector(imageUrl = imageUrl3, label = "", imageName = imageName3)

        Spacer(modifier = Modifier.height(10.dp))

        PButton(text = "Continue", click = {
            imageUrl1.value?.let { images.value = images.value + it }
            imageUrl2.value?.let { images.value = images.value + it }
            imageUrl3.value?.let { images.value = images.value + it }

            addParkingViewModel.validateInput(
                name = name.value,
                address = address.value,
                capacity = capacity.value,
                parkingType = parkingType.value,
                images = images.value
            )
            if(
                addParkingViewModel.nameError.value.isEmpty() &&
                addParkingViewModel.addressError.value.isEmpty() &&
                addParkingViewModel.capacityError.value.isEmpty() &&
                addParkingViewModel.imageError.value.isEmpty() &&
                addParkingViewModel.parkingTypeError.value.isEmpty()
            ){
                location.value?.let {
                    addParkingViewModel.addLot(
                        Lot(
                            name = name.value,
                            address = address.value,
                            latitude = location.value!!.latitude,
                            longitude = location.value!!.longitude,
                            capacity = capacity.value.toInt(),
                            parkingType = parkingType.value,
                            imageUrl = images.value
                        )
                    )
                    println("Lot Error: ${addParkingViewModel.lotError.value}")
                    if(addParkingViewModel.lotError.value.isEmpty()){
                        Toast.makeText(context, "Lot Added Successfully!", Toast.LENGTH_LONG).show()
                        navController.navigate(Screen.AddOp.route)
                    }
                }
            }
        })
    }
}
