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
fun DOpHourItem(
    timeItem: TimeItemResponse,
) {

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

    }

    Divider(
        modifier = Modifier.fillMaxWidth()
    )
}