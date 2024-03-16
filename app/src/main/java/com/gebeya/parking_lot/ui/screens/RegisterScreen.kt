package com.gebeya.parking_lot.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.gebeya.parking_lot.R
import com.gebeya.parking_lot.ui.components.PButton
import com.gebeya.parking_lot.ui.components.PText
import com.gebeya.parking_lot.ui.components.PTextField
import com.gebeya.parking_lot.ui.components.montserratFamily
import com.gebeya.parking_lot.ui.theme.PWhite
import com.gebeya.parking_lot.ui.util.Screen
import com.gebeya.parking_lot.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(
    navController: NavHostController
){

    val registerViewModel = hiltViewModel<RegisterViewModel>()

    val phone = remember {
        mutableStateOf("")
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = PWhite
            )
            .padding(20.dp),
    ){

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.45f),
            painter = painterResource(id = R.drawable.logo), contentDescription = "")


        PText(text = "Continue with a ParkNav Account",
            size = 24.sp, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        PTextField(
            inputState = phone,
            leadingIcon = {Icon(Icons.Default.Phone, contentDescription = "Phone Number")},
            label = "Phone Number",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            placeholder = {Text(text = "+2510000000")},
            onError = registerViewModel.phoneError.isNotEmpty(),
        )

        if (registerViewModel.phoneError.isNotEmpty()){
            Text(text = registerViewModel.phoneError,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(top = 10.dp),
                style = TextStyle(
                    color = Color.Red, fontFamily = montserratFamily, fontSize = 14.sp
                )
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        PButton(text = "Continue", click = {
            registerViewModel.validatePhoneNumber(phone.value)
            if(registerViewModel.phoneError.isEmpty()) {
               println("register screen")
               registerViewModel.authUser(phone.value)
                navController.navigate("${Screen.Verify.route}/${phone.value}")
            }
        })


    }
}