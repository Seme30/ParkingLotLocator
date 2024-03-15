package com.gebeya.parking_lot.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gebeya.parking_lot.ui.theme.PBlue
import com.gebeya.parking_lot.ui.theme.PWhite
import com.gebeya.parking_lot.ui.theme.PWhite2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PDropDown(
    inputState: MutableState<String>,
    placeholder: String,
    leadingIcon: (@Composable() () -> Unit)? = null,
    types: List<String>,
    onError: Boolean = false
){

    val isExpanded = remember{
        mutableStateOf(false)
    }

    ExposedDropdownMenuBox(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        expanded = isExpanded.value,
        onExpandedChange = {
            isExpanded.value = it
        }
    ) {

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
                readOnly = true,
                isError = onError,
                textStyle = TextStyle(
                    fontFamily = montserratFamily,
                ),
                leadingIcon = leadingIcon,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded.value) },
                placeholder = { Text(text = placeholder)},
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
                 modifier = Modifier
                    .menuAnchor()
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
            )
        }

        ExposedDropdownMenu(
            expanded = isExpanded.value,
            onDismissRequest = { isExpanded.value = false }
        ) {
            types.forEach{
                DropdownMenuItem(
                    text = { PText(text = it, size = 15.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        isExpanded.value = false
                        inputState.value = it
                    }
                )
            }
        }
    }
}