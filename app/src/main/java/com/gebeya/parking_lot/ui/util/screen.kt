package com.gebeya.parking_lot.ui.util

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Favorite
import androidx.compose.material.icons.sharp.Home
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import com.gebeya.parking_lot.R


sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: @Composable () -> Unit) {
    object Home : Screen(route = "homeScreen", R.string.home,
        { Icon(Icons.Sharp.Home , contentDescription = "Home Screen") })

    object Welcome: Screen( route ="FavoritesScreen", R.string.welcome, {
        Icon(Icons.Sharp.Favorite, contentDescription = "Welcome Screen")
    })

    object Register: Screen(route = "RegisterScreen", R.string.register, {
        Icon(Icons.Sharp.Person, contentDescription = "Register Screen")
    })

    object RegisterForm: Screen(route = "RegisterForm", R.string.registerForm, {})

    object Verify: Screen(route = "VerifyScreen", R.string.verify, {

    })

    object RoleSelection: Screen(route = "RoleSelection", R.string.roleSelection, {

    })

    object MainScreen: Screen(route = "MainScreen", R.string.mainScreen, {})

    object SuccessScreen: Screen(route = "SuccessScreen", R.string.success, {})

}