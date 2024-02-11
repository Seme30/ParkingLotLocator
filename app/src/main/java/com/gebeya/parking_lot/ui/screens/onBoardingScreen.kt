package com.gebeya.parking_lot.ui.screens

import androidx.annotation.DrawableRes
import com.gebeya.parking_lot.R

sealed class OnBoardingScreen(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {

    object First: OnBoardingScreen(
        image = R.drawable.logo,
        title = "Discover a New Way to Park with ParkNav",
        description = "Effortlessly find, secure, and enjoy parking like never before with ParkNav."
    )

    object Second: OnBoardingScreen(
        image = R.drawable.logo,
        title = "Find Nearby Parking",
        description = "Easily find the nearby parking spot to you, or the location you are going to."
    )

    object Thrid: OnBoardingScreen(
        image = R.drawable.logo,
        title = "Book and Park",
        description = "Enjoy comfortable and spacious parking spaces for your vehicle."
    )

}