package com.gebeya.parking_lot.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.gebeya.parking_lot.ui.components.montserratFamily
import com.gebeya.parking_lot.ui.theme.PDeepBlue

@Composable
fun PText(
    text: String,
    size: TextUnit,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign = TextAlign.Start,
    textColor: Color = PDeepBlue,
    maxLines: Int = 1
){


    Text(
        text = text,
        maxLines = maxLines,
        modifier = modifier,
        color = textColor,
        fontFamily = montserratFamily,
        fontWeight = fontWeight,
        fontSize = size,
        letterSpacing = 0.1.sp,
        textAlign = textAlign
    )

}


