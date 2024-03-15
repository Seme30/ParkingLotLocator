package com.gebeya.parking_lot.ui.util

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.gebeya.parking_lot.ui.screens.DetailScreen
import com.gebeya.parking_lot.ui.screens.driver.MainScreen
import com.gebeya.parking_lot.ui.screens.driver.RegisterForm
import com.gebeya.parking_lot.ui.screens.RegisterScreen
import com.gebeya.parking_lot.ui.screens.RoleSelectionScreen
import com.gebeya.parking_lot.ui.screens.SuccessScreen
import com.gebeya.parking_lot.ui.screens.VerifyScreen
import com.gebeya.parking_lot.ui.screens.WelcomeScreen
import com.gebeya.parking_lot.ui.screens.driver.DirectionMapScreen
import com.gebeya.parking_lot.ui.screens.driver.ProfileScreen
import com.gebeya.parking_lot.ui.screens.driver.VehicleListScreen
import com.gebeya.parking_lot.ui.screens.provider.DashboardScreen
import com.gebeya.parking_lot.ui.screens.provider.ProviderMainScreen
import com.gebeya.parking_lot.ui.screens.provider.ProviderRegisterForm
import com.gebeya.parking_lot.ui.screens.provider.ParkingProfileScreen
import com.gebeya.parking_lot.ui.screens.provider.ReservationScreen
import com.gebeya.parking_lot.ui.screens.provider.parkingmanagment.AddOp
import com.gebeya.parking_lot.ui.screens.provider.parkingmanagment.AddParkingForm
import com.gebeya.parking_lot.ui.screens.provider.parkingmanagment.ParkingLot
import com.gebeya.parking_lot.viewmodel.RoleViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String,
    roleViewModel: RoleViewModel
){


    NavHost(navController = navController,
        startDestination = startDestination
    ){
        composable(route = Screen.Welcome.route){
            WelcomeScreen(navController = navController)
        }

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

        composable(route= Screen.RoleSelection.route,
        ){
            RoleSelectionScreen(
                navController = navController
            )
        }


        composable(route = Screen.RegisterForm.route){
            RegisterForm(navController = navController)
        }

        // Common
        composable(route = Screen.SuccessScreen.route){
            SuccessScreen(navController = navController, roleViewModel)
        }

        composable(route = "${Screen.DirectionMap.route}/{lat}/{lng}",
            arguments = listOf(
                navArgument("lat"){
                    type = NavType.StringType
                },
                navArgument("lng"){
                    type = NavType.StringType
                }
            )
        ){

            DirectionMapScreen(
                navController = navController
            )
        }

        // Driver
        composable(route = Screen.MainScreen.route){
            MainScreen(navController = navController)
        }

        composable(route = "${Screen.Detail.route}/{id}"
            , arguments = listOf(
                navArgument("id"){
                    type = NavType.IntType
                }
            )){
            DetailScreen(navController = navController)
        }


        // Provider
        composable(route = Screen.Dashboard.route){
            DashboardScreen(navController = navController)
        }
        composable(route = Screen.ProviderRegisterForm.route){
            ProviderRegisterForm(navController = navController)
        }

        composable(route = Screen.ProviderMainScreen.route){
            ProviderMainScreen(
                navController = navController
            )
        }

        composable(route = Screen.LotList.route){
            ParkingProfileScreen(
                navController
            )
        }


        composable(route = Screen.PRegisterForm.route){
            AddParkingForm(navController = navController)
        }

        composable(route = Screen.VehicleList.route){
            VehicleListScreen(
                navController = navController
            )
        }

        composable(route = Screen.LotInformation.route){
            ParkingLot(
                navController = navController
            )
        }

        composable(route = Screen.Reservation.route){
            ReservationScreen(
                navController = navController
            )
        }

        composable(route = Screen.AddOp.route){
            AddOp(
                navController = navController
            )
        }

        composable(route = Screen.ProfileScreen.route){
            ProfileScreen(
                navController
            )
        }

    }

}