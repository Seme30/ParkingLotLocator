package com.gebeya.parking_lot.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.gebeya.parking_lot.ui.theme.ParkingTheme
import com.gebeya.parking_lot.ui.util.SetupNavGraph
import com.gebeya.parking_lot.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().setKeepOnScreenCondition{
            !splashViewModel.isLoading.value
        }
        setContent {
            ParkingTheme {
                val navController = rememberNavController()
                val screen by splashViewModel.startDestination
                SetupNavGraph(navController = navController, startDestination = screen)
                }
            }
        }
    }



