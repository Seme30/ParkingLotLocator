package com.gebeya.parking_lot.ui.screens.provider

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EventAvailable
import androidx.compose.material.icons.filled.EventBusy
import androidx.compose.material.icons.filled.LocalParking
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.gebeya.parking_lot.R
import com.gebeya.parking_lot.data.network.model.Lot
import com.gebeya.parking_lot.data.network.model.LotResponse
import com.gebeya.parking_lot.data.network.model.Provider
import com.gebeya.parking_lot.ui.components.PStatCard
import com.gebeya.parking_lot.ui.components.PText
import com.gebeya.parking_lot.ui.theme.PBlue
import com.gebeya.parking_lot.ui.theme.PDeepBlue
import com.gebeya.parking_lot.ui.theme.PWhite
import com.gebeya.parking_lot.ui.util.Screen
import com.gebeya.parking_lot.viewmodel.DashboardViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale

@Composable
fun DashboardScreen(
    navController: NavHostController
){


    val dashboardViewModel = hiltViewModel<DashboardViewModel>()
    dashboardViewModel.getLot()
    dashboardViewModel.getProvider()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                PWhite
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {


        if(dashboardViewModel.lotError.value == "parking lot id not found"){
            println(dashboardViewModel.lotError.value)
            navController.navigate(Screen.PRegisterForm.route)
        } else {
            if(dashboardViewModel.provider.value!=null){

                val provider = dashboardViewModel.provider.value
                val lot = dashboardViewModel.lot.value

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(PDeepBlue)
                            ) {
                                PText(
                                    text = provider?.firstName?.substring(0, 1)?.replaceFirstChar {
                                        if (it.isLowerCase()) it.titlecase(
                                            Locale.ROOT
                                        ) else it.toString()
                                    }
                                        ?:"U".capitalize(Locale.ENGLISH),
                                    textAlign = TextAlign.Center,
                                    size = 18.sp,
                                    textColor = Color.White,
                                    modifier = Modifier.padding(4.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Column(
                            ) {
                                PText(text = "Hello, ${provider?.firstName ?:"User"}", size = 16.sp)
                            }
                        }

                        IconButton(onClick = {

                        }) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notification",
                                tint = PBlue,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                if (lot != null) {
                    if (provider != null) {
                        ParkingLotStats(
                            lot = lot,
                            provider = provider
                        )

                        println("lot: $lot")
                        println("provider: $provider")
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    CircularProgressIndicator()
                }
            }


        }


    }

}



@Composable
fun ParkingLotStats(
    lot: LotResponse,
    provider: Provider
) {
    val stats = listOf(
        StatCardData(number = lot.availableSlot.toString(), type = "Available Spots", icon = {
            Icon(imageVector = Icons.Default.EventAvailable, contentDescription = "Available", tint = PBlue,)
        }),
        StatCardData(number = "${lot.capacity - lot.availableSlot}", type = "Reserved Spots", icon = {
            Icon(imageVector = Icons.Default.EventBusy, contentDescription = "Reserved", tint = PBlue,)
        }),
        StatCardData(number = "${lot.capacity}", type = "Total Spots", icon = {
            Icon(imageVector = Icons.Default.LocalParking, contentDescription = "Total", tint = PBlue,)
        })
    )

    Spacer(modifier = Modifier.height(16.dp))

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

            ParkingLotInfo(
        address = lot.address,
        provider = provider
    )

        Spacer(modifier = Modifier.height(20.dp))

        GridView(cards = stats)

    }
}


@OptIn(ExperimentalCoilApi::class)
@Composable
fun ParkingLotInfo(
    address: String,
    provider: Provider
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(10.dp)
                .clip(RoundedCornerShape(20.dp))
        ) {

            Image(
                painter = rememberImagePainter(data = provider.imageUrl),
                contentDescription = "User Avatar",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            PText(text = "${provider.firstName} ${provider.lastName}", size = 14.sp)
            PText(text = address, size = 14.sp)
        }
    }
}


data class StatCardData(
    val number: String,
    val type: String,
    val icon: (@Composable () -> Unit)
)



@Composable
fun GridView(cards: List<StatCardData>) {
    val rows = cards.chunked(3)
    LazyColumn {
        items(rows) { rowCards ->
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                for (card in rowCards) {
                    PStatCard(
                        number = card.number,
                        type = card.type,
                        icon = card.icon
                    )
                }
            }
        }
    }
}