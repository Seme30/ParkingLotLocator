package com.gebeya.parking_lot.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gebeya.parking_lot.R
import com.gebeya.parking_lot.data.network.model.Vehicle


@Composable
fun VehicleListItem2 (
    vehicle: Vehicle,
    isSelected: Boolean,
    onVehicleSelected: (Vehicle) -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp).clickable {
                onVehicleSelected(vehicle)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = "Vehicle Image",
            modifier = Modifier.size(64.dp)
        )

        Column(
            modifier = Modifier
                .padding(start = 16.dp, )
        ) {
            PText(text = vehicle.name, size = 15.sp)
            Spacer(modifier = Modifier.height(10.dp))
            PText(text = vehicle.plate, size = 16.sp)
            Spacer(modifier = Modifier.height(10.dp))
            PText(text = vehicle.year.toString(), size = 16.sp)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onVehicleSelected(vehicle)
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val image = if (isSelected)
                Icons.Filled.CheckBox
            else Icons.Filled.CheckBoxOutlineBlank

            val description = if (isSelected) "Checked" else "Unchecked"

            Icon(imageVector  = image, description)
        }
    }
}