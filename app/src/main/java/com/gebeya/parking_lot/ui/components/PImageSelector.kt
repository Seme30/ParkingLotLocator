package com.gebeya.parking_lot.ui.components

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.OpenableColumns
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
import com.gebeya.parking_lot.ui.theme.PBlue
import com.gebeya.parking_lot.ui.theme.PWhite
import com.gebeya.parking_lot.ui.theme.PWhite2
import com.training.gebeya.parkinglotlocator.ui.components.PText

@Composable
fun PImageSelector(
    modifier: Modifier = Modifier,
    imageState: MutableState<Bitmap?>,
    imageName: MutableState<String?>,
    label: String
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imageState.value = getBitmapFromUri(context, it)
            imageName.value = getFileName(context, it)
        }
    }

    Column(modifier = modifier
        .clickable {
            launcher.launch("image/*")
        }
        .padding(20.dp),) {

        Spacer(modifier = Modifier.height(10.dp))

        PText(text = label, size = 14.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clip(CircleShape)
                .background(
                    color = PWhite
                ),
        ){

            TextField(
                value = "",
                onValueChange = {

                },
                textStyle = TextStyle(
                    fontFamily = montserratFamily,
                ),
                leadingIcon = { Icon(Icons.Default.Image, "Image") },
                trailingIcon = { IconButton(onClick = {
                    launcher.launch("image/*")
                }){
                    Icon(Icons.Default.Folder, "")}
                },
                readOnly = true,
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
                    )
                    ,
                placeholder = {
                    if(imageName.value != null){
                        val trimmedText = if (imageName.value!!.length > 20) {
                            imageName.value!!.substring(0, 20) + "..."
                        } else {
                            imageName.value!!
                        }
                        PText(text = trimmedText , size = 14.sp, fontWeight = FontWeight.Thin)
                    }else{
                        PText(text = "Select Image" , size = 14.sp, fontWeight = FontWeight.Thin)
                    }
                }

            )
        }

    }
}

fun getBitmapFromUri(context: Context, uri: Uri): Bitmap {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, uri))
    } else {
        MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
    }
}

@SuppressLint("Range")
fun getFileName(context: Context, uri: Uri): String {
    var result: String? = null
    if (uri.scheme == "content") {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        try {
            if (cursor != null && cursor.moveToFirst()) {
                result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
        } finally {
            cursor?.close()
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
