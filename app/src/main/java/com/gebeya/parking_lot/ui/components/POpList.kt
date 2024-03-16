package com.gebeya.parking_lot.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.gebeya.parking_lot.viewmodel.ParkingLotViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun POpList(
    parkingLotViewModel: ParkingLotViewModel
) {

    parkingLotViewModel.getOpTime()


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val timeItemList = parkingLotViewModel.opTimes.value
        if(timeItemList!=null){
            val isEmpty = timeItemList.isEmpty()
            items(if (isEmpty ) 1 else timeItemList.size) { index ->
                if (isEmpty) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        PText(text = "No Operation Time is Registered Yet!", size = 15.sp)
                    }
                }
                else {
                    Column(
                        modifier = Modifier.fillMaxHeight(
                            0.3f
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        POpHourItem(timeItem = timeItemList[index], parkingLotViewModel)
                    }

                }
            }
        } else {
            item {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }

}