package com.gebeya.parking_lot.ui.screens.provider

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.gebeya.parking_lot.data.network.model.Driver
import com.gebeya.parking_lot.data.network.model.Provider
import com.gebeya.parking_lot.ui.components.PButton
import com.gebeya.parking_lot.ui.components.PText
import com.gebeya.parking_lot.ui.components.PTextField
import com.gebeya.parking_lot.ui.components.montserratFamily
import com.gebeya.parking_lot.ui.theme.PWhite
import com.gebeya.parking_lot.ui.util.Screen
import com.gebeya.parking_lot.viewmodel.ProviderRegisterFromViewModel

@Composable
fun ProviderRegisterForm(
    navController: NavHostController
){

    val firstName = remember {
        mutableStateOf("")
    }

    val lastName = remember {
        mutableStateOf("")
    }

    val email = remember {
        mutableStateOf("")
    }

    val registerFormViewModel = hiltViewModel<ProviderRegisterFromViewModel>()
    val context = LocalContext.current
    var isTermsAgread by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = PWhite)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Column(
            modifier = Modifier
                .weight(0.1f)
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.Start
        ) {
//            IconButton(onClick = {
//                navController.navigate(Screen.Register.route)
//            },
//            ) {
//                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Go Back")
//            }
        }

        Column(
            modifier = Modifier
                .weight(0.9f)
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            PText(
                text = "Welcome to",
                size = 24.sp,
                fontWeight = FontWeight.SemiBold,

                )
            PText(
                text = "ParkNav",
                size = 24.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(
                    bottom = 50.dp
                )
            )

//            Spacer(modifier = Modifier.height(20.dp))

            PTextField(
                inputState = firstName,
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = "first name") },
                label = "First Name",
                onError = registerFormViewModel.firstNameError.isNotEmpty(),
                placeholder = { PText(text = "Abebe" , size = 14.sp, fontWeight = FontWeight.Thin) },
//                onError = registerViewModel.phoneError.isNotEmpty(),
            )

            if (registerFormViewModel.firstNameError.isNotEmpty()){
                Text(text = registerFormViewModel.firstNameError,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(top = 10.dp),
                    style = TextStyle(
                        color = Color.Red, fontFamily = montserratFamily, fontSize = 14.sp
                    )
                )
            }

            PTextField(
                inputState = lastName,
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = "last name") },
                label = "Last Name",
                onError = registerFormViewModel.lastNameError.isNotEmpty(),
                placeholder = { PText(text = "Kebede" , size = 14.sp, fontWeight = FontWeight.Thin) },
//                onError = registerViewModel.phoneError.isNotEmpty(),
            )

            if (registerFormViewModel.lastNameError.isNotEmpty()){
                Text(text = registerFormViewModel.lastNameError,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(top = 10.dp),
                    style = TextStyle(
                        color = Color.Red, fontFamily = montserratFamily, fontSize = 14.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            PTextField(
                inputState = email,
                leadingIcon = { Icon(Icons.Default.MailOutline, contentDescription = "Email",) },
                label = "Email",
                onError = registerFormViewModel.emailError.isNotEmpty(),
                placeholder = { PText(text = "Abebekebede@abc.com", size = 14.sp, fontWeight = FontWeight.Thin) },
//                onError = registerViewModel.phoneError.isNotEmpty(),
            )
            if (registerFormViewModel.emailError.isNotEmpty()){
                Text(text = registerFormViewModel.emailError,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(top = 10.dp),
                    style = TextStyle(
                        color = Color.Red, fontFamily = montserratFamily, fontSize = 14.sp
                    )
                )
            }

//            PImageSelector(imageState = imageState, label = "Profile", imageName = imageName)

            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        isTermsAgread = !isTermsAgread
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                val image = if (isTermsAgread)
                    Icons.Filled.CheckBox
                else Icons.Filled.CheckBoxOutlineBlank

                val description = if (isTermsAgread) "Checked" else "Unchecked"

                IconButton(onClick = {isTermsAgread = !isTermsAgread}){
                    Icon(imageVector  = image, description)
                }

                PText(text = "I agree to terms and conditions", size = 15.sp, fontWeight = FontWeight.SemiBold)

            }
            Spacer(modifier = Modifier.height(30.dp))
            PButton(text = "Create account", click = {
                registerFormViewModel.validateInput(firstName.value, lastName.value, email.value)
                if(isTermsAgread){

                    if(
                        registerFormViewModel.firstNameError.isEmpty() &&
                        registerFormViewModel.lastNameError.isEmpty() &&
                        registerFormViewModel.emailError.isEmpty()
                    ){
                        registerFormViewModel.phoneNumber.value?.let { phone ->
                            Provider(
                                id = null,
                                firstName.value,
                                lastName.value,
                                email.value,
                                phone,
                                imageUrl = null
                            ).let {
                                registerFormViewModel.createProvider(it)
                                navController.navigate(Screen.SuccessScreen.route)
                            }
                        }
                        navController.navigate(Screen.SuccessScreen.route)
                    }


                } else {
                    Toast.makeText(context, "First Agree to Terms and Conditions", Toast.LENGTH_LONG).show()
                }

            })
        }



    }
}