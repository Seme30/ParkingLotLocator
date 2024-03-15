package com.gebeya.parking_lot.ui.util

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FireTruck
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material.icons.sharp.Dashboard
import androidx.compose.material.icons.sharp.Favorite
import androidx.compose.material.icons.sharp.Home
import androidx.compose.material.icons.sharp.LocalParking
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import com.gebeya.parking_lot.R


sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: @Composable () -> Unit) {
    object Welcome: Screen( route ="WelcomeScreen", R.string.welcome, {
        Icon(Icons.Sharp.Favorite, contentDescription = "Welcome Screen")
    })

    object Register: Screen(route = "RegisterScreen", R.string.register, {
        Icon(Icons.Sharp.Person, contentDescription = "Register Screen")
    })

    object RegisterForm: Screen(route = "RegisterForm", R.string.registerForm, {})

    object ProviderRegisterForm: Screen(route = "ProviderRegisterForm", R.string.registerForm, {})

    object Verify: Screen(route = "VerifyScreen", R.string.verify, {

    })

    object Detail: Screen(route = "DetailScreen" , R.string.detail, {})


    object RoleSelection: Screen(route = "RoleSelection", R.string.roleSelection, {

    })

    object MainScreen: Screen(route = "MainScreen", R.string.mainScreen, {})

    object SuccessScreen: Screen(route = "SuccessScreen", R.string.success, {})

    object SuccessBookingScreen: Screen(route = "SuccessBookingScreen", R.string.success, {})


    object ProviderMainScreen: Screen(route = "ParkingMainScreen", R.string.parkinglotprovider, {})

    object Dashboard: Screen(route="DashboardScreen", R.string.dashboard, {
        Icon(Icons.Sharp.Home, contentDescription = "Dashboard")
    })

    object LotList: Screen(route = "ParkingLot", R.string.ParkingLot, {
        Icon(Icons.Sharp.LocalParking, contentDescription = "Parking")
    })

    object PRegisterForm: Screen(route = "PRegisterForm", R.string.RegisterForm, {
        Icon(imageVector = Icons.Sharp.Add, contentDescription = "ParkingForm")
    })

    object ParkingProfileScreen: Screen(route = "ParkingProfile", R.string.ParkingProfile, {
        Icon(Icons.Sharp.Person, contentDescription = "")
    } )

    object LotInformation: Screen(route = "LotInformation", R.string.LotInformation, {
        Icon(imageVector = Icons.Sharp.LocalParking, contentDescription = "Lot")
    })


    object VehicleList: Screen(route = "VehicleList", R.string.VehicleList, {
        Icon(imageVector = Icons.Default.FireTruck, contentDescription = "VehicleList")
    })

    object AddOp: Screen(route = "AddOperation", R.string.ParkingLot, {
        Icon(imageVector = Icons.Default.FireTruck, contentDescription = "VehicleList")
    })

    object DirectionMap: Screen(route = "DirectionMap", R.string.ParkingLot, {
        Icon(imageVector = Icons.Default.FireTruck, contentDescription = "VehicleList")
    })

    object ProfileScreen: Screen(route = "Profile", R.string.ParkingProfile, {
        Icon(imageVector = Icons.Default.Person, contentDescription = "Profile")
    })


    object Reservation: Screen(route="Reservation", R.string.Reservation, {
        Icon(imageVector = Icons.Default.List, contentDescription = "Reservation")
    })

}