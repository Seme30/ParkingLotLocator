package com.gebeya.parking_lot.ui.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.sharp.Watch
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.gebeya.parking_lot.R
import com.gebeya.parking_lot.ui.components.DOpList
import com.gebeya.parking_lot.ui.components.PButton
import com.gebeya.parking_lot.ui.components.PText
import com.gebeya.parking_lot.ui.theme.PBlue
import com.gebeya.parking_lot.ui.theme.PDeepBlue
import com.gebeya.parking_lot.ui.theme.PWhite
import com.gebeya.parking_lot.ui.util.Screen
import com.gebeya.parking_lot.viewmodel.DetailViewModel
import com.google.accompanist.pager.HorizontalPagerIndicator
import dagger.hilt.android.lifecycle.HiltViewModel


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class, ExperimentalCoilApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun DetailScreen(
    navController: NavHostController
) {

    val detailViewModel = hiltViewModel<DetailViewModel>()
    val context = LocalContext.current
    detailViewModel.getLotById()

   if(detailViewModel.lot.value != null){
       detailViewModel.lot.value?.let { lotResponse ->
           Scaffold(
               topBar = {
                   TopAppBar(
                       title = {
                       PText(text = "Detail", size = 16.sp)
                   },
                       navigationIcon = {
                           IconButton(onClick = {
                               navController.navigateUp()
                           }) {
                               Icon(imageVector = Icons.Default.ArrowBack,
                                   contentDescription = "Arrow Back")
                           }
                       }
                       )
               }
           ) { paddingValues ->
               Column(
                   modifier = Modifier
                       .fillMaxSize()
                       .background(color = PWhite)
                       .padding(paddingValues)
                       .padding(20.dp),
                   horizontalAlignment = Alignment.CenterHorizontally,
                   verticalArrangement = Arrangement.Center
               ) {

                   Column(
                       modifier = Modifier
                           .weight(0.1f)
                           .fillMaxWidth(),
                       horizontalAlignment = Alignment.Start
                   ) {
                       IconButton(onClick = {
                           navController.navigateUp()
                       },
                       ) {
                           Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Go Back")
                       }
                   }

                   Column(
                       modifier = Modifier
                           .fillMaxSize()
                           .background(color = PWhite)
                           .padding(top = 20.dp),
                       horizontalAlignment = Alignment.CenterHorizontally,
                       verticalArrangement = Arrangement.Center
                   ) {

                       Column(
                           modifier = Modifier
                               .weight(0.95f)
                               .fillMaxWidth()
                               .padding(bottom = 20.dp),
                           horizontalAlignment = Alignment.CenterHorizontally,
                       ) {

                           Spacer(modifier = Modifier.height(10.dp))
                           Box(
                               contentAlignment = Alignment.Center,
                               modifier = Modifier
                                   .fillMaxWidth()
                                   .padding(10.dp)
                                   .clip(RoundedCornerShape(20.dp))
                           ) {
                               val pagerState = rememberPagerState(
                                   initialPage = 0,
                                   initialPageOffsetFraction = 0f
                               ) {
                                   lotResponse.images.size
                               }

                               HorizontalPager(state = pagerState) { page ->
                                   Image(
                                       painter = rememberImagePainter(data = lotResponse.images[page]),
                                       contentDescription = "Author Image",
                                       contentScale = ContentScale.Crop,
                                       modifier = Modifier
                                           .fillMaxWidth()
                                           .height(200.dp),

                                       )

                               }

                               HorizontalPagerIndicator(
                                   modifier = Modifier
                                       .align(Alignment.BottomCenter)
                                       .padding(bottom = 10.dp),
                                   pagerState = pagerState,
                                   pageCount = lotResponse.images.size,
                                   activeColor = PDeepBlue,
                                   inactiveColor = Color.White,
                                   indicatorShape = RoundedCornerShape(50f)
                               )
                           }

                           Spacer(modifier = Modifier.height(10.dp))

                           Row(
                               modifier = Modifier
                                   .fillMaxWidth()
                                   .padding(start = 15.dp),
                               horizontalArrangement = Arrangement.Center,
                               verticalAlignment = Alignment.CenterVertically
                           ) {
                               PText(
                                   text = lotResponse.name,
                                   size = 20.sp,
                                   textAlign = TextAlign.Center,
                               )
                           }

                           PText(
                               modifier = Modifier
                                   .fillMaxWidth()
                                   .padding(start = 16.dp, top = 5.dp),
                               text = lotResponse.address,
                               size = 15.sp,
                               textAlign = TextAlign.Center,
                           )

                           Spacer(modifier = Modifier.height(10.dp))

                           DOpList()

                           Spacer(modifier = Modifier.height(20.dp))

                           PButton(text = "Book Now", click = {
                               Toast.makeText(context, "Congrats you successfully booked a spot", Toast.LENGTH_LONG).show()
                               navController.navigate("${Screen.DirectionMap.route}/${lotResponse.latitude}/${lotResponse.longitude}")
                           })
                       }
                   }
               }
           }
       }
   } else {
       Column(
           modifier = Modifier
               .fillMaxSize()
               .background(color = PWhite)
               .padding(20.dp),
           horizontalAlignment = Alignment.CenterHorizontally,
           verticalArrangement = Arrangement.Center
       ){
           CircularProgressIndicator()
       }
   }


}
