package com.training.gebeya.parkinglotlocator.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.training.gebeya.parkinglotlocator.R
import com.training.gebeya.parkinglotlocator.ui.theme.PDeepBlue
import org.w3c.dom.Text

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


