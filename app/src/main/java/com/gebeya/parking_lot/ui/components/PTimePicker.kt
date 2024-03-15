package com.gebeya.parking_lot.ui.components

import android.app.TimePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.mutableStateOf

import java.time.LocalTime
import kotlin.math.absoluteValue


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PTimePicker(
    label: String,
    operationTime: (LocalTime) -> Unit
) {

    val context = LocalContext.current

    val currentTime = LocalTime.now()
    val currentHour = remember { mutableStateOf(currentTime.hour) }
    val currentMinute = remember { mutableStateOf(currentTime.minute) }
    val selectedTime = remember { mutableStateOf(currentTime.toString()) }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState()

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute, ->
            val newTime = LocalTime.of(hourOfDay, minute)
            selectedTime.value = newTime.toString()
            currentHour.value = hourOfDay
            operationTime(newTime)
        },
        currentHour.value,
        currentMinute.value,
        true
    )

    PTextField(
        label = label,
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 10.dp),
        inputState = selectedTime,
        readOnly = true,
        interactionSource = interactionSource,
        trailingIcon = { Icon(imageVector = Icons.Default.AccessTime, contentDescription = "")}
    )

    if(isPressed.value){
        timePickerDialog.show()
    }

}
