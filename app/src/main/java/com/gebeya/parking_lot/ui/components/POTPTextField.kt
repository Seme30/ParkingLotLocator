package com.gebeya.parking_lot.ui.components

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gebeya.parking_lot.ui.theme.PBlue
import com.gebeya.parking_lot.ui.theme.PWhite2


@Composable
fun POtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 5,
    onOtpTextChange: (String, Boolean) -> Unit
) {

    PText(
        text = "Verification code",
        size = 18.sp,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Start,
        modifier = Modifier.fillMaxWidth(0.8f)
    )
    
    Spacer(modifier = Modifier.height(20.dp))

    LaunchedEffect(Unit) {
        if (otpText.length > otpCount) {
            throw IllegalArgumentException("Otp text value must not have more than otpCount: $otpCount characters")
        }
    }

    BasicTextField(
        modifier = modifier,
        value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
        onValueChange = {
            if (it.text.length <= otpCount) {
                onOtpTextChange.invoke(it.text, it.text.length == otpCount)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.Center) {
                repeat(otpCount) { index ->
                    CharView(
                        index = index,
                        text = otpText
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    )
}

@Composable
private fun CharView(
    index: Int,
    text: String
) {
    val isFocused = text.length == index
    val char = when {
        index == text.length -> "0"
        index > text.length -> ""
        else -> text[index].toString()
    }

    Text(
        modifier = Modifier
            .width(40.dp)
            .height(70.dp)
            .background(
                if (isFocused){
                    PBlue
                } else {
                    PWhite2
                }
            )
            .padding(2.dp),
        text = char,
        style = MaterialTheme.typography.titleMedium,
        color = if (isFocused) {
            PWhite2
        } else {
            PBlue
        },
        textAlign = TextAlign.Center
    )
}