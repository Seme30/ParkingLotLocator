package com.gebeya.parking_lot.ui.screens.provider

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.gebeya.parking_lot.data.network.model.Driver
import com.gebeya.parking_lot.data.network.model.Provider
import com.gebeya.parking_lot.ui.components.EditableText
import com.gebeya.parking_lot.ui.components.PButton
import com.gebeya.parking_lot.ui.components.PImageSelector
import com.gebeya.parking_lot.ui.components.PText
import com.gebeya.parking_lot.ui.theme.PWhite
import com.gebeya.parking_lot.ui.util.Screen
import com.gebeya.parking_lot.viewmodel.ProfileViewModel


@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoilApi::class)
@Composable
fun ParkingProfileScreen(
    navController: NavHostController
) {
    val context = LocalContext.current

    val profileViewModel = hiltViewModel<ProfileViewModel>()
    profileViewModel.getParkingProfile()




    val phone = remember {
        mutableStateOf("")
    }



    val isNameEdited = remember {
        mutableStateOf(false)
    }
    val isLastNameEdited = remember { mutableStateOf(false) }
    val isEmailEdited = remember { mutableStateOf(false) }
    val isImageEdited = remember { mutableStateOf<Boolean?>(false) }

    val isAnyFieldEdited = isNameEdited.value || isLastNameEdited.value || isEmailEdited.value || isImageEdited.value == true
    val showDialog = remember {
        mutableStateOf(false)
    }

    val isEditDialog = remember {
        mutableStateOf(false)
    }

    val imageName: MutableState<String> = remember {
        mutableStateOf("Select Image")
    }

    val initialName = remember {
        mutableStateOf("name")
    }
    val initialLastName = remember {
        mutableStateOf("")
    }
    val initialId = remember {
        mutableStateOf(0)
    }

    val initialPhone = remember {
        mutableStateOf("")
    }
    val initialEmail = remember {
        mutableStateOf("")
    }
    val initialImageUrl: MutableState<String?> = remember {
        mutableStateOf(null)
    }

    val newProvider: MutableState<Provider?> = remember { mutableStateOf(null) }


    if(profileViewModel.provider.value != null){

        val provider = profileViewModel.provider.value
        println("provider $provider")

        if (provider != null) {
            initialName.value = provider.firstName
            initialEmail.value = provider.email
            initialLastName.value = provider.lastName
            initialPhone.value = provider.phoneNo
            initialId.value = provider.id!!
            initialImageUrl.value = provider.imageUrl
        }

        val name = remember {
            mutableStateOf(initialName.value)
        }
        val lastName = remember {
            mutableStateOf(initialLastName.value)
        }

        val email = remember {
            mutableStateOf(initialEmail.value)
        }

        val imageUrl: MutableState<String?> = remember {
            mutableStateOf(initialImageUrl.value)
        }

        LaunchedEffect(isNameEdited.value, isLastNameEdited.value, isEmailEdited.value, isImageEdited.value) {
            newProvider.value = Provider(
                id = initialId.value,
                email = email.value,
                phoneNo = initialPhone.value,
                firstName = name.value,
                lastName = lastName.value,
                imageUrl = imageUrl.value
            )
            println("newprovider: ${newProvider.value}")
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(
                    PWhite
                ),
            verticalArrangement = Arrangement.Top
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            if (provider != null) {
                Image(
                    painter = rememberImagePainter(data = initialImageUrl.value),
                    contentDescription = "User Avatar",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)

                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            PText(
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                text = phone.value,
                size = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
            )
            PImageSelector(imageUrl = initialImageUrl,
                onEditStateChange = {url ->
                    imageUrl.value = url
                },
                imageName = imageName , label = "",
                isImageEdited = isImageEdited)

            Spacer(modifier = Modifier.height(20.dp))

            EditableText(
                label = "First Name: ",
                initialValue = initialName,
                isEdited = isNameEdited.value) { bool, newText ->
                isNameEdited.value = bool
                name.value = newText ?:""
            }
            EditableText(
                label = "Last Name:",
                initialValue = initialLastName,
                isEdited = isLastNameEdited.value) { bool, newText ->
                isLastNameEdited.value = bool
                println("new last name: $newText")
                lastName.value = newText ?: ""
                println("new initialLast name: ${initialLastName.value}")
            }
            EditableText(
                label = "Email",
                initialValue = initialEmail,
                isEdited = isEmailEdited.value) {bool, newText ->
                isEmailEdited.value = bool
                email.value = newText ?: ""
            }
            Spacer(modifier = Modifier.height(20.dp))

            if (isAnyFieldEdited) PButton(text = "Save Changes", click = { isEditDialog.value = true })

            if (isEditDialog.value) {
                AlertDialog(
                    onDismissRequest = { isEditDialog.value = false },
                    title = { Text("Confirm Save") },
                    text = { Text("Are you sure you want to save the changes?") },
                    confirmButton = {
                        Button(
                            onClick = {

                                newProvider.value?.let {
                                    profileViewModel.updateProvider(
                                        it
                                    )
                                }
                                if(profileViewModel.updateProviderError.value.isEmpty()){
                                    Toast.makeText(context, "Profile Updated Successfully!", Toast.LENGTH_LONG).show()
                                    isEditDialog.value = false
                                }
                            }
                        ) {
                            PText("Yes", size = 16.sp)
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = { isEditDialog.value = false }
                        ) {
                            PText("No", size = 16.sp)
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Divider(color = Color.Gray, thickness = 1.dp)

            Spacer(modifier = Modifier.height(20.dp))
            Row (
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .clickable {
                        showDialog.value = true
                    }
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){

                PText(text = "Logout", size = 16.sp, textColor = Color.Red)

                Spacer(modifier = Modifier.width(20.dp))
                IconButton(
                    onClick = {
                    showDialog.value = true
                }) {
                    Icon(Icons.Default.Logout, contentDescription = "Logout", tint = Color.Red)
                }

                if (showDialog.value) {
                    AlertDialog(
                        onDismissRequest = { showDialog.value = false },
                        title = { Text("Confirm Logout") },
                        text = { Text("Are you sure you want to log out?") },
                        confirmButton = {
                            Button(
                                onClick = {
                                    profileViewModel.logout()
                                    showDialog.value = false
                                    navController.popBackStack(Screen.Register.route, true)
                                    navController.navigate(Screen.Register.route)
                                }
                            ) {
                                PText("Yes", size = 16.sp)
                            }
                        },
                        dismissButton = {
                            Button(
                                onClick = { showDialog.value = false }
                            ) {
                                PText("No", size = 16.sp)
                            }
                        }
                    )
                }
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            CircularProgressIndicator()

        }
    }

}


