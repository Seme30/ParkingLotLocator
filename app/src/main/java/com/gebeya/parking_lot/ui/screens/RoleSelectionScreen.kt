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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.gebeya.parking_lot.R
import com.gebeya.parking_lot.ui.components.PButton
import com.gebeya.parking_lot.ui.components.PText
import com.gebeya.parking_lot.ui.theme.PWhite
import com.gebeya.parking_lot.ui.util.Screen

@Composable
fun RoleSelectionScreen(
    navController: NavHostController
){

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = PWhite
            )
            .padding(20.dp),
    ){



        Column {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.45f),
                painter = painterResource(id = R.drawable.logo), contentDescription = "")
            PText(text = "Discover a New Way to Park with ParkNav",
                size = 24.sp, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(15.dp))
            PText(text = "Effortlessly find, secure, and enjoy parking like never before with ParkNav.", size = 18.sp, textAlign = TextAlign.Center)

        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxWidth(0.2f).padding(bottom = 30.dp)
        ) {
            PText(text = "Select Your Role",
                size = 24.sp, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(15.dp))

            PButton(text = "Continue as Driver", click = {
                navController.navigate(Screen.RegisterForm.route)
            })

            Spacer(modifier = Modifier.height(15.dp))

            PButton(text = "Continue as Provider", click = {
                navController.navigate(Screen.RegisterForm.route)
            })
        }
    }
}