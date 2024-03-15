package com.gebeya.parking_lot.ui.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gebeya.parking_lot.ui.theme.PBlue
import com.gebeya.parking_lot.ui.theme.PWhite
import com.gebeya.parking_lot.ui.theme.PWhite2

@Composable
fun PStatCard(
    number: String,
    type: String,
    icon: (@Composable() () -> Unit)? = null,
){

    Card(
        modifier = Modifier
            .width(120.dp)
            .height(130.dp)
            .padding(20.dp)
            .background(PWhite2)
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(10.dp),
                color = PBlue
            )
    ) {

        Column(
            modifier = Modifier.fillMaxSize().background(PWhite),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            if (icon != null) {
                icon()
            }
            Spacer(modifier = Modifier.height(15.dp))
            PText(text = number, size = 14.sp, textAlign = TextAlign.Center,)
            PText(text = type, size = 12.sp, textAlign = TextAlign.Center, fontWeight = FontWeight.Thin)


        }

    }
}