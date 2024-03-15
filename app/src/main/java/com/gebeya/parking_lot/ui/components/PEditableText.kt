package com.gebeya.parking_lot.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gebeya.parking_lot.ui.theme.PWhite

@Composable
fun EditableText(
    label: String,
    initialValue: MutableState<String>,
    isEdited: Boolean,
    onEditStateChange: (Boolean, currentText: String?) -> Unit)
{
    var isEditing by remember { mutableStateOf(false) }
    var currentText by remember { mutableStateOf(initialValue.value) }


    Column(
        modifier = Modifier.fillMaxWidth(0.89f),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ){
            PText(
                text = label,
                size = 14.sp,
                fontWeight = FontWeight.SemiBold,

            )

            Spacer(modifier = Modifier.width(20.dp))

            if (isEditing) {
                TextField(
                    value = currentText,
                    onValueChange = { newText ->
                        currentText = newText
                        onEditStateChange(newText != initialValue.value, newText)
                    },
                    modifier = Modifier.fillMaxWidth(0.6f),
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = {
                            isEditing = false
                        }) {
                            Icon(Icons.Filled.Close, contentDescription = "Close")
                        }
                    },
                )
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PText(
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        text = currentText,
                        size = 14.sp,
                        modifier = Modifier
                            .clickable {  } // This line changes isEditing to true when the user clicks on the Text
                    )

                    IconButton(onClick = {
                        isEditing = true
                    }) {
                        Icon(imageVector = Icons.Filled.Edit, contentDescription = "EDIT")
                    }
                }
            }


        }
    }

}