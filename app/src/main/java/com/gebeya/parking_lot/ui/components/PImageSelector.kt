package com.gebeya.parking_lot.ui.components

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.gebeya.parking_lot.ui.theme.PBlue
import com.gebeya.parking_lot.ui.theme.PWhite
import com.gebeya.parking_lot.ui.theme.PWhite2
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File


@Composable
fun PImageSelector(
    imageUrl: MutableState<String?>,
    onEditStateChange: (String?) -> Unit = {_ -> },
    isImageEdited: MutableState<Boolean?>? = null,
    imageName: MutableState<String>,
    label: String,
    type: String? = null,
    imageId: String? = null,
) {
    val context = LocalContext.current
    val storageRef = Firebase.storage.reference

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            val filename = "profile-image"
            val fileRef = storageRef.child("Parking-lot/$type/$filename-$imageId")

            imageName.value = "Uploading"

            val uploadTask = fileRef.putFile(uri)

            uploadTask.addOnSuccessListener {
                fileRef.downloadUrl.addOnSuccessListener { uri ->
                    imageUrl.value = uri.toString()
                    isImageEdited?.value = true
                    onEditStateChange(
                        imageUrl.value
                    )
                    imageName.value = filename
                    println("uploadTask ${imageUrl.value}")
                }
            }.addOnFailureListener { exception ->
                Toast.makeText(context, "Failed to upload image: ${exception.message}", Toast.LENGTH_SHORT).show()
                imageName.value = "Upload failed"
            }
        }
    }

    Column(modifier = Modifier.fillMaxWidth(0.89f)
        .clickable {
            launcher.launch("image/*")
        }, horizontalAlignment = Alignment.Start) {

        PText(text = label, size = 14.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clip(CircleShape)
                .background(
                    color = Color.White
                ),
        ){

            TextField(
                value = "",
                onValueChange = {

                },
                textStyle = TextStyle(
                    fontFamily = montserratFamily,
                ),
                trailingIcon = { IconButton(onClick = {
                    launcher.launch("image/*")
                }){
                    Icon(Icons.Default.Image, "", tint = PBlue)}
                },
                readOnly = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    cursorColor = PBlue,
                    disabledTextColor = Color.White,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White,
                        shape = CircleShape
                    )
                    .border(
                        width = 0.dp,
                        shape = CircleShape,
                        color = Color.White
                    )
                ,
                placeholder = {
                    val trimmedText = if (imageName.value.length > 20) {
                        imageName.value.substring(0, 20) + "..."
                    } else {
                        imageName.value
                    }
                    PText(text = trimmedText , size = 14.sp, fontWeight = FontWeight.Thin)
                }

            )
        }

    }
}



@SuppressLint("Range")
fun getFileName(context: Context, uri: Uri): String {
    var result: String? = null
    if (uri.scheme == "content") {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor.use {
            if (cursor != null && cursor.moveToFirst()) {
                result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
        }
    }
    if (result == null) {
        result = uri.path
        val cut = result?.lastIndexOf('/')
        if (cut != -1) {
            result = result?.substring(cut!! + 1)
        }
    }
    return result ?: "Unknown"
}