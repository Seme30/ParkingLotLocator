package com.gebeya.parking_lot.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.gebeya.parking_lot.ui.components.PText
import com.gebeya.parking_lot.ui.components.PTextField
import com.gebeya.parking_lot.ui.util.Screen
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoilApi::class)
@Composable
fun MainScreen(
    navController: NavHostController,
){
    

    val drawerItems = listOf(Screen.Verify, Screen.Register)
//    val currentScreen = remember { mutableStateOf<Screen>(Screen.Home) }

//    val navBackStackEntry by navController.currentBackStackEntryAsState()
//    val currentDestination = navBackStackEntry?.destination
//    val currentRoute = navBackStackEntry?.destination?.route
    val context = LocalContext.current
    val topBarState = remember { mutableStateOf("Home") }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val selectedItem = remember { mutableStateOf(drawerItems[0]) }


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Avatar
                    Image(
                        painter = rememberImagePainter(data = "https://www.w3schools.com/howto/img_avatar.png"), // replace with actual image URL
                        contentDescription = "User Avatar",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .clickable {
                            }
                    )

                    Spacer(Modifier.height(16.dp))

                    // Name
                    Text(text = "UserName")

                    Spacer(Modifier.height(8.dp))

                    // Phone number
                    Text(text = "09102037218")
                }

                Spacer(Modifier.height(24.dp))

                // Drawer items
                drawerItems.forEach { item ->
                    NavigationDrawerItem(
                        icon = { item.icon },
                        label = { Text(context.getString(item.resourceId)) },
                        selected = item == selectedItem.value,
                        onClick = {
                            scope.launch { drawerState.close() }
                            selectedItem.value = item
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = topBarState.value)
                                },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Rounded.Menu, contentDescription = "Open drawer")
                            }
                        }
                    )
                },
                bottomBar = {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(20.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        PTextField(placeholder = { PText(text = "Search", size = 16.sp)} , trailingIcon = {Icon(Icons.Default.Search, contentDescription = "Search")})
                    }
                }

            ) { paddingValues ->
                Surface(
                    modifier = Modifier.padding(paddingValues)
                ) {
                    if (drawerState.isOpen) {
                        BackHandler(onBack = { scope.launch { drawerState.close() } })
                    }

//                    MapScreen()
                }
            }
        }
    )
}