package com.gebeya.parking_lot.ui.screens.provider

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.gebeya.parking_lot.ui.components.PText
import com.gebeya.parking_lot.ui.components.montserratFamily
import com.gebeya.parking_lot.ui.screens.provider.parkingmanagment.AddParkingForm
import com.gebeya.parking_lot.ui.screens.provider.parkingmanagment.ParkingLot
import com.gebeya.parking_lot.ui.theme.PBlue
import com.gebeya.parking_lot.ui.theme.PWhite
import com.gebeya.parking_lot.ui.util.Screen


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProviderMainScreen(
    navController: NavHostController,
){
    val screenItems = listOf(
        Screen.Dashboard,
        Screen.LotInformation,
        Screen.Reservation,
        Screen.ParkingProfileScreen
    )
    val currentScreen = remember { mutableStateOf<Screen>(Screen.Dashboard) }
    val context = LocalContext.current

    Scaffold(
        bottomBar = {
            NavigationBar(
//                        modifier = Modifier.height(55.dp)
            ) {
                screenItems.forEach { screen ->
                    val isSelected = currentScreen.value == screen // Check if the current screen is selected
                    NavigationBarItem(
                        selected = isSelected,
                        label = {
                            PText(text = context.getString(screen.resourceId), size = 10.sp)
                        },
                        alwaysShowLabel = true,
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = if (isSelected) PBlue else Color.Black,
                            selectedIconColor = Color.White,
                            unselectedIconColor = Color.Black,
                            selectedTextColor = if (isSelected) PBlue else Color.Black,
                            unselectedTextColor = Color.Black,
                            disabledIconColor = Color.Black
                        ),
                        onClick = {
                            currentScreen.value = screen
                        },
                        icon = screen.icon,
                    )
                }
            }
        }

    ) { paddingValues ->
        Surface(
            modifier = Modifier.padding(paddingValues)
        ) {

            when (currentScreen.value) {
                Screen.Dashboard -> DashboardScreen(navController,
//                            onNavBack
                )
                Screen.LotInformation -> ParkingLot(navController = navController)
                Screen.Reservation -> ReservationScreen(navController = navController)
                Screen.ParkingProfileScreen -> ParkingProfileScreen(
                    navController
                )
                else -> { /* handle other cases here */ }
            }
        }
    }
}
