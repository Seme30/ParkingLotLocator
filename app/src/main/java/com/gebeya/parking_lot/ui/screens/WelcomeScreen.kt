package com.gebeya.parking_lot.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.gebeya.parking_lot.ui.components.PButton
import com.gebeya.parking_lot.ui.components.PText
import com.gebeya.parking_lot.ui.theme.PDeepBlue
import com.gebeya.parking_lot.ui.theme.PWhite
import com.gebeya.parking_lot.ui.util.Screen
import com.gebeya.parking_lot.viewmodel.WelcomeViewModel
import com.google.accompanist.pager.HorizontalPagerIndicator

@OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class)
@Composable
fun WelcomeScreen(
    navController: NavHostController
){

    val welcomeViewModel = hiltViewModel<WelcomeViewModel>()

    val pages = listOf(
        OnBoardingScreen.First,
        OnBoardingScreen.Second,
        OnBoardingScreen.Thrid
    )

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        pages.size
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(
            color = PWhite
        ),
    ) {
        HorizontalPager(
            state = pagerState,
            pageContent = { position ->
                PagerScreen(screen = pages[position])
            },
            modifier = Modifier.weight(10f),
            verticalAlignment = Alignment.Top
        )
        HorizontalPagerIndicator(
            pagerState = pagerState,
            pageCount = 3,
            modifier = Modifier
                .align(
                    Alignment.CenterHorizontally
                )
                .weight(1f),
            activeColor = PDeepBlue,
            inactiveColor = Color.Gray,
            indicatorShape = RoundedCornerShape(50f)
        )
        GetStartedButton(
            pagerState = pagerState, onClick = {
                welcomeViewModel.saveOnBoardingState(completed = true)
                navController.popBackStack()
                navController.navigate(Screen.Register.route)
            },
            modifier = Modifier
                .weight(2f)
                .padding(bottom = 15.dp)
        )
    }
}


@Composable
fun PagerScreen(screen : OnBoardingScreen){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = PWhite
            )
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Image(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.6f),
            painter = painterResource(id = screen.image), contentDescription = "")

        Column {
            PText(text = screen.title,
                size = 24.sp, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(15.dp))
            PText(text = screen.description, size = 18.sp, textAlign = TextAlign.Center)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GetStartedButton(
    pagerState: PagerState,
    onClick: ()-> Unit,
    modifier: Modifier = Modifier
){

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(15.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        AnimatedVisibility(visible = pagerState.currentPage == 2) {
            PButton(text = "Get Started", click = onClick)
        }
    }

}


//@Composable
//fun HomeScreen(navController: NavHostController){
//
//    PText(text = "Home Screen")
//}
