package com.gebeya.parking_lot.ui.screens.driver

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.gebeya.parking_lot.data.keystore.Role
import com.gebeya.parking_lot.ui.components.PText
import com.gebeya.parking_lot.ui.theme.PBlue
import com.gebeya.parking_lot.ui.theme.PWhite
import com.gebeya.parking_lot.ui.util.Screen
import com.gebeya.parking_lot.viewmodel.RoleViewModel
import com.gebeya.parking_lot.viewmodel.SuccessViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


@Composable
fun SuccessScreen(
    navController: NavHostController,
){

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    PBlue
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(
                        PWhite
                    )
            ) {
                Icon(
                    imageVector = Icons.Sharp.Check,
                    contentDescription = "Success",
                    modifier = Modifier.padding(15.dp),
                    tint = PBlue
                )
            }

            PText(text = "Success!", size = 18.sp, textColor = PWhite, fontWeight = FontWeight.SemiBold)

            Spacer(modifier = Modifier.height(15.dp))

            PText(text = "Congrats, your have\n" +
                    "successfully booked a spot", size = 14.sp, textColor = PWhite, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center, maxLines = 2)

        }
    }

    LaunchedEffect(Unit) {
        delay(3000)
        withContext(Dispatchers.Main){
            navController.navigate(Screen.DirectionMap.route){
                popUpTo(Screen.Detail.route) { inclusive = true }
            }
        }
    }


}