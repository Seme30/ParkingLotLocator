package com.training.gebeya.parkinglotlocator.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.training.gebeya.parkinglotlocator.ui.theme.PBlue
import com.training.gebeya.parkinglotlocator.ui.theme.PWhite
import com.training.gebeya.parkinglotlocator.ui.theme.PWhite2


@Composable
fun PButton(text: String, click: ()-> Unit, isWhite: Boolean = false){

    Button(
        onClick = click,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = when(isWhite){
            true -> PWhite2
            false -> PBlue
        })
    ) {
        PText(
            text = text,
            size = 14.sp,
            textAlign = TextAlign.Center,
            textColor = when(isWhite){
                true -> PBlue
                false -> PWhite2
            }
        )
    }


}