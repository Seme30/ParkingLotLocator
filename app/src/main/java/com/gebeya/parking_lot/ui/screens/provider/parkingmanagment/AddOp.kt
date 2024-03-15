package com.gebeya.parking_lot.ui.screens.provider.parkingmanagment

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.gebeya.parking_lot.R
import com.gebeya.parking_lot.data.network.model.TimeItem
import com.gebeya.parking_lot.ui.components.PButton
import com.gebeya.parking_lot.ui.components.PText
import com.gebeya.parking_lot.ui.components.PTimePicker
import com.gebeya.parking_lot.ui.components.montserratFamily
import com.gebeya.parking_lot.ui.theme.PWhite
import com.gebeya.parking_lot.ui.util.Screen
import com.gebeya.parking_lot.viewmodel.ParkingLotViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddOp(
    navController: NavHostController
){

    val startTime = remember {
        mutableStateOf<LocalTime>(LocalTime.now())
    }
    val endTime = remember {
        mutableStateOf<LocalTime>(LocalTime.now())
    }
    val price = remember { mutableStateOf("") }

    val parkingLotViewModel = hiltViewModel<ParkingLotViewModel>()
    parkingLotViewModel.getLot()

    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = PWhite
            )

    ){
        

        Column(
            modifier = Modifier
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PText(text = "Add Operation Hours", size = 20.sp)
            Spacer(modifier = Modifier.height(20.dp))
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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = { price.value = it },
                label = { Text("Price") },
                singleLine = true
            )
            if (parkingLotViewModel.priceError.value.isNotEmpty()){
                Text(text = parkingLotViewModel.priceError.value,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(top = 10.dp),
                    style = TextStyle(
                        color = Color.Red, fontFamily = montserratFamily, fontSize = 14.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            PButton(
                text = "Add Operation Hour",
                isWhite = false,
                click = {
                    parkingLotViewModel.validateInput(price.value)
                    if(parkingLotViewModel.priceError.value.isEmpty()
                    ){
                        val timeList = listOf(
                            TimeItem(
                                startTime = startTime.value.toString(),
                                endTime = endTime.value.toString(),
                                price = price.value.toDouble()
                            )
                        )
                        println("Time list: $timeList")
                        parkingLotViewModel.addOpTime(
                            timeList
                        )
                        if(parkingLotViewModel.addError.value.isEmpty()){
                            Toast.makeText(context, "Operation time Added Successfully", Toast.LENGTH_LONG).show()
                            navController.navigate(Screen.ProviderMainScreen.route)
                        }
                    }
                }
            )
        }




//        OtpTextField(
//            otpText = otpValue,
//            onOtpTextChange = { value, otpInputFilled ->
//                otpValue = value
//            }
//        )

    }

}