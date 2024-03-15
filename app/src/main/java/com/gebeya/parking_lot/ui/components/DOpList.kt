package com.gebeya.parking_lot.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gebeya.parking_lot.data.network.model.TimeItem
import com.gebeya.parking_lot.data.network.model.TimeItemResponse

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DOpList(
) {


    LazyColumn(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val timeItemList = listOf(
            TimeItemResponse(1, "08:00", "10:00", 15.0),
            TimeItemResponse(2, "10:00", "12:00", 20.0),
            TimeItemResponse(3, "12:00", "14:00", 25.0)
        )

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
                    DOpHourItem(timeItem = timeItemList[index])
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