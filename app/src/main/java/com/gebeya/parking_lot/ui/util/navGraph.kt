package com.gebeya.parking_lot.ui.util

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gebeya.parking_lot.ui.screens.HomeScreen
import com.gebeya.parking_lot.ui.screens.MainScreen
import com.gebeya.parking_lot.ui.screens.RegisterForm
import com.gebeya.parking_lot.ui.screens.RegisterScreen
import com.gebeya.parking_lot.ui.screens.RoleSelectionScreen
import com.gebeya.parking_lot.ui.screens.SuccessScreen
import com.gebeya.parking_lot.ui.screens.VerifyScreen
import com.gebeya.parking_lot.ui.screens.WelcomeScreen


@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String
){
    NavHost(navController = navController,
        startDestination = startDestination
    ){
        composable(route = Screen.Welcome.route){
            WelcomeScreen(navController = navController)
        }

        composable(route = Screen.Home.route){
            HomeScreen(navController = navController)
        }
//
        composable(route= Screen.Register.route,
            ){
            RegisterScreen(
               navController = navController
            )
        }

        composable(route = "${Screen.Verify.route}/{phone}",
            arguments = listOf(
                navArgument("phone"){
                    type = NavType.StringType
                }
            )){
            val phone = it.arguments?.getString("phone")
            println("Phone in composable $phone")
            VerifyScreen(navController = navController)
        }

        composable(route = Screen.RegisterForm.route){
            RegisterForm(navController = navController)
        }

        composable(route = Screen.MainScreen.route){
            MainScreen(navController = navController)
        }

        composable(route = Screen.SuccessScreen.route){
            SuccessScreen(navController = navController)
        }

        composable(route= Screen.RoleSelection.route,
        ){
            RoleSelectionScreen(
                navController = navController
            )
        }
    }

}