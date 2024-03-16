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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gebeya.parking_lot.data.network.model.TimeItemResponse
import com.gebeya.parking_lot.viewmodel.ParkingLotViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun POpHourItem(
    timeItem: TimeItemResponse,
    parkingLotViewModel: ParkingLotViewModel
) {

    val isDeleteVisible = remember {
        mutableStateOf(false)
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
                isDeleteVisible.value = false
            },
            dialogTitle = "Delete Confirmation",
            dialogText = "Are you sure you want to delete this item?",
            icon = Icons.Filled.Warning
        )
    }
}

