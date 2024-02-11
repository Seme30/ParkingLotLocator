package com.gebeya.parking_lot.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gebeya.parking_lot.ui.theme.PBlue
import com.gebeya.parking_lot.ui.theme.PWhite
import com.gebeya.parking_lot.ui.theme.PWhite2


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PTextField(
    modifier: Modifier = Modifier,
    inputState: MutableState<String> = mutableStateOf(""),
    leadingIcon: (@Composable() () -> Unit)? = null,
    trailingIcon: (@Composable() () -> Unit)? = null,
    label: String,
    placeholder: (@Composable() () -> Unit)? = null,
    onError: Boolean = false,
    supportingText: (@Composable() () -> Unit)? = null,
    keyboardOptions: KeyboardOptions =KeyboardOptions(keyboardType = KeyboardType.Text),
    readOnly: Boolean = false,
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {


    println("State: ${inputState.value}")

    Column(
        modifier = Modifier.fillMaxWidth(0.89f),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        PText(text = label, size = 14.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clip(CircleShape).background(
                    color = PWhite
                )
        ){

            TextField(
                value = inputState.value,
                onValueChange = {
                    inputState.value = it
                },
                readOnly = readOnly,
                interactionSource = interactionSource,
                isError = onError,
                supportingText = supportingText,
                textStyle = TextStyle(
                    fontFamily = montserratFamily,
                ),
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                singleLine = true,

                colors = TextFieldDefaults.colors(
                    focusedContainerColor = PWhite2,
                    unfocusedContainerColor = PWhite2,
                    cursorColor = PBlue,
                    disabledTextColor = Color.White,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                keyboardOptions = keyboardOptions,
                visualTransformation = visualTransformation,
                modifier = modifier
                    .fillMaxWidth()
                    .background(
                        color = PWhite2,
                        shape = CircleShape
                    )
                    .border(
                        width = 0.dp,
                        shape = CircleShape,
                        color = PWhite
                    ),
                placeholder = placeholder
            )
        }


    }
}