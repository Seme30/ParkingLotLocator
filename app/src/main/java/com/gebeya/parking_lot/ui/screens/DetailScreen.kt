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
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.gebeya.parking_lot.R
import com.gebeya.parking_lot.data.network.model.Reserve
import com.gebeya.parking_lot.data.network.model.Vehicle
import com.gebeya.parking_lot.ui.components.DOpList
import com.gebeya.parking_lot.ui.components.PButton
import com.gebeya.parking_lot.ui.components.PText
import com.gebeya.parking_lot.ui.components.VehicleListItem
import com.gebeya.parking_lot.ui.components.VehicleListItem2
import com.gebeya.parking_lot.ui.components.montserratFamily
import com.gebeya.parking_lot.ui.theme.PBlue
import com.gebeya.parking_lot.ui.theme.PDeepBlue
import com.gebeya.parking_lot.ui.theme.PWhite
import com.gebeya.parking_lot.ui.util.Screen
import com.gebeya.parking_lot.viewmodel.DetailViewModel
import com.google.accompanist.pager.HorizontalPagerIndicator
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.LocalTime


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
    detailViewModel.getVehicles()
    detailViewModel.getOpTime()
    val showDialog = remember {
        mutableStateOf(false)
    }

    var sliderPosition by remember { mutableStateOf(0f) }
    var selectedVehicleId by remember {
        mutableStateOf("")
    }




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

                   if (showDialog.value){
                       Dialog(onDismissRequest = { showDialog.value = false }) {
                           Column(
                               modifier = Modifier
                                   .background(PWhite, shape = RoundedCornerShape(10))
                                   .padding(20.dp)
                               ,
                               verticalArrangement = Arrangement.Center,
                               horizontalAlignment = Alignment.CenterHorizontally
                           ) {
                               LazyColumn(
                                   modifier = Modifier.padding(20.dp),
                                   horizontalAlignment = Alignment.CenterHorizontally
                               ) {
                                   val vehicles = detailViewModel.vehicleList.value
                                   val isEmpty = vehicles.isEmpty()
                                   val isNull = vehicles.all {
                                       it == null
                                   }


                                   items(if (isEmpty || isNull) 1 else vehicles.size) { index ->
                                       if (isEmpty) {
                                           Column(
                                               modifier = Modifier.fillMaxSize(),
                                               horizontalAlignment = Alignment.CenterHorizontally,
                                               verticalArrangement = Arrangement.Center
                                           ) {
                                               PText(text = "No Vehicles Registered Yet!", size = 15.sp)
                                               PText(text = "Add Vehicles First!", size = 15.sp)
                                           }
                                       } else if(isNull){
                                           Column(
                                               modifier = Modifier.fillMaxSize(),
                                               horizontalAlignment = Alignment.CenterHorizontally,
                                               verticalArrangement = Arrangement.Center
                                           ) {
                                               CircularProgressIndicator()
                                           }
                                       }
                                       else {
                                           vehicles[index]?.let { vehicle ->
                                               VehicleListItem2(
                                                   vehicle = vehicle,
                                                   isSelected = vehicle.id == selectedVehicleId,
                                                   onVehicleSelected = { selectedVehicle ->
                                                       selectedVehicleId = selectedVehicle.id?:""
                                                   }
                                               )
                                           }

                                           Row (
                                               modifier = Modifier.fillMaxWidth().padding(10.dp),
                                               verticalAlignment = Alignment.CenterVertically,
                                               horizontalArrangement = Arrangement.SpaceEvenly
                                           ){
                                               Slider(
                                                   value = sliderPosition,
                                                   onValueChange = { sliderPosition = it },
                                                   valueRange = 0f..24f
                                               )
                                           }
                                           PText(text = "${sliderPosition.toInt()} hours", size = 15.sp)
                                       }
                                   }
                               }

                               if (detailViewModel.durationError.value.isNotEmpty()){
                                   Text(text = detailViewModel.durationError.value,
                                       modifier = Modifier
                                           .fillMaxWidth(0.8f)
                                           .padding(top = 10.dp),
                                       style = TextStyle(
                                           color = Color.Red, fontFamily = montserratFamily, fontSize = 14.sp
                                       )
                                   )
                               }

                               PButton(
                                   text = "Reserve",
                                   isWhite = false,
                                   click = {
                                       detailViewModel.validateInput(duration = sliderPosition.toString())
                                       if(detailViewModel.durationError.value.isEmpty() && selectedVehicleId.isNotEmpty()){
                                           detailViewModel.addReservation(
                                               Reserve(
                                                   stayingDuration = LocalTime.of(
                                                       sliderPosition.toInt(),
                                                       0
                                                   ).toString(),
                                                   vehicleId = selectedVehicleId.toInt()
                                               )
                                           )
                                           Toast.makeText(context, "Successfully Reserved!", Toast.LENGTH_LONG).show()
                                           navController.navigate("${Screen.DirectionMap.route}/${lotResponse.latitude}/${lotResponse.longitude}")
                                       }
                                   }
                               )
                           }
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

                           DOpList(
                               timeItemList = detailViewModel.opTimes.value
                           )

                           Spacer(modifier = Modifier.height(10.dp))

                           PButton(text = "Reserve Now", click = {
                               showDialog.value = true
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
