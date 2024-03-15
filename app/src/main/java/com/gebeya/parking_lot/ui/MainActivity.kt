package com.gebeya.parking_lot.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.gebeya.parking_lot.ui.screens.driver.VehicleListScreen
import com.gebeya.parking_lot.ui.screens.provider.DashboardScreen
import com.gebeya.parking_lot.ui.screens.provider.ParkingProfileScreen
import com.gebeya.parking_lot.ui.screens.provider.parkingmanagment.ParkingLot
import com.gebeya.parking_lot.ui.theme.ParkingTheme
import com.gebeya.parking_lot.ui.util.SetupNavGraph
import com.gebeya.parking_lot.viewmodel.RoleViewModel
import com.gebeya.parking_lot.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            ||
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                1
            )
        }

        installSplashScreen().setKeepOnScreenCondition {
            !splashViewModel.isLoading.value
        }

        setContent {
            ParkingTheme {
//                ParkingProfileScreen()

                val navController = rememberNavController()
                
//                ParkingLot(navController = navController)
                val roleViewModel = hiltViewModel<RoleViewModel>()
                roleViewModel.getRole()

                val loadingState by splashViewModel.isLoading

                if (loadingState) {
                    // Show loading screen
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                        ) {
                        CircularProgressIndicator(
                        )
                    }
                } else {
                    // Show main content
                    SetupNavGraph(navController = navController, startDestination = splashViewModel.startDestination.value, roleViewModel = roleViewModel)
                }
            }
        }
    }
}




