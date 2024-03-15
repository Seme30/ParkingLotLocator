package com.gebeya.parking_lot.ui.screens.provider.parkingmanagment

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.gebeya.parking_lot.data.network.model.TimeItem
import com.gebeya.parking_lot.ui.components.PButton
import com.gebeya.parking_lot.ui.components.POpList
import com.gebeya.parking_lot.ui.components.PText
import com.gebeya.parking_lot.ui.components.PTimePicker
import com.gebeya.parking_lot.ui.components.montserratFamily
import com.gebeya.parking_lot.ui.theme.PDeepBlue
import com.gebeya.parking_lot.ui.theme.PWhite
import com.gebeya.parking_lot.viewmodel.ParkingLotViewModel
import com.google.accompanist.pager.HorizontalPagerIndicator
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class, ExperimentalCoilApi::class)
@Composable
fun ParkingLot(
    navController: NavHostController,
){

    val context = LocalContext.current

    val parkingLotViewModel = hiltViewModel<ParkingLotViewModel>()
    parkingLotViewModel.getLot()

    val showDialog = remember { mutableStateOf(false) }

    val startTime = remember {
        mutableStateOf<LocalTime>(LocalTime.now())
    }
    val endTime = remember {
        mutableStateOf<LocalTime>(LocalTime.now())
    }
    val price = remember { mutableStateOf("") }


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
                                showDialog.value = false
                            }
                        }


                    }
                )
            }
        }
    }


    Scaffold(
        floatingActionButton = {
            IconButton(onClick = {
                showDialog.value = true
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Operation Hours")
            }
        }
    ) { paddingValues ->

        parkingLotViewModel.lot.value?.let { lotResponse ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = PWhite)
                    .padding(paddingValues)
                    .padding(top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clip(RoundedCornerShape(20.dp))
                ) {
                    val pagerState = rememberPagerState(
                        initialPage = 0,
                        initialPageOffsetFraction = 0f
                    ) {
                        lotResponse.images.size
                    }

                    HorizontalPager(state = pagerState) { page ->
                        Image(
                            painter = rememberImagePainter(data = lotResponse.images[page]),
                            contentDescription = "Author Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),

                            )

                    }

                    HorizontalPagerIndicator(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 10.dp),
                        pagerState = pagerState,
                        pageCount = lotResponse.images.size,
                        activeColor = PDeepBlue,
                        inactiveColor = Color.White,
                        indicatorShape = RoundedCornerShape(50f)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PText(
                        text = lotResponse.name,
                        size = 20.sp,
                        textAlign = TextAlign.Center,
                    )
                }

                PText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 5.dp),
                    text = lotResponse.address,
                    size = 15.sp,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(10.dp))


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PText(
                        text = "Available Slot: ${lotResponse.availableSlot}",
                        size = 15.sp,
                        textAlign = TextAlign.Start,
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                POpList(navController = navController, parkingLotViewModel)

            }
        }



    }



}