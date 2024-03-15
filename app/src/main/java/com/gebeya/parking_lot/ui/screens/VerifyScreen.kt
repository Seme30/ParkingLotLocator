package com.gebeya.parking_lot.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.gebeya.parking_lot.ui.components.PButton
import com.gebeya.parking_lot.ui.components.POtpTextField
import com.gebeya.parking_lot.ui.components.PText
import com.gebeya.parking_lot.ui.components.montserratFamily
import com.gebeya.parking_lot.ui.theme.PWhite
import com.gebeya.parking_lot.ui.util.Screen
import com.gebeya.parking_lot.viewmodel.VerifyViewModel


@Composable
fun VerifyScreen(
    navController: NavHostController
){

    val verifyViewModel = hiltViewModel<VerifyViewModel>()
    val phone by verifyViewModel.phoneNumber

    var otpValue by remember{
        mutableStateOf("")
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = PWhite)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

       Column(
           modifier = Modifier
               .weight(0.2f)
               .fillMaxWidth()
               .padding(10.dp),
           horizontalAlignment = Alignment.Start
       ) {
           IconButton(onClick = {
               navController.navigate(Screen.Register.route)
           },
           ) {
               Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Go Back")
           }
       }

        Column(
            modifier = Modifier
                .weight(0.8f)
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            PText(
                text = "Enter",
                size = 24.sp,
                fontWeight = FontWeight.SemiBold,

                )
            PText(
                text = "verification code",
                size = 24.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(
                    bottom = 50.dp
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            PText(
                text = "Please enter the verification code we sent to your mobile $phone",
                size = 18.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Thin,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(30.dp))

            POtpTextField(
                otpText = otpValue,
                onOtpTextChange = { value, _ ->
                    otpValue = value
                }
            )

            if (verifyViewModel.incorrectCodeError.value.isNotEmpty()){
                Text(text = verifyViewModel.incorrectCodeError.value,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(top = 10.dp),
                    style = TextStyle(
                        color = Color.Red, fontFamily = montserratFamily, fontSize = 14.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
            
            PButton(text = "Resend Code", click = { /*TODO*/ }, isWhite = true)
            Spacer(modifier = Modifier.height(15.dp))
            PButton(text = "Verify", click = {
                println("otp $otpValue")
                println("phone $phone")

                verifyViewModel.codeError.value = ""
                verifyViewModel.incorrectCodeError.value=""

                verifyViewModel.verifyOTP(
                    phoneNo = phone?: "",
                    otp = otpValue
                )
                println("code ${verifyViewModel.phoneVerifyResponse.value}")
                if(verifyViewModel.codeError.value.isEmpty()
                    && verifyViewModel.incorrectCodeError.value.isEmpty()
                    ){
                    when (verifyViewModel.phoneVerifyResponse.value.code) {
                        "U100" -> {
                            // if the user is a new user
                            navController.navigate(Screen.RoleSelection.route){
                                popUpTo(Screen.Welcome.route) { inclusive = true }
                            }
                        }
                        "U101" -> {
                            // if the user is a driver
                            navController.navigate(Screen.MainScreen.route){
                                popUpTo(Screen.Welcome.route) { inclusive = true }
                            }
                        }
                        "U102" -> {
                            // if the user is a provider
                            navController.navigate(Screen.ProviderMainScreen.route){
                                popUpTo(Screen.Welcome.route) { inclusive = true }
                            }
                        }
                    }
                }
            })
        }



    }
}