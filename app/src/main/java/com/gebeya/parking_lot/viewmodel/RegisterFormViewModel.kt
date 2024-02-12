package com.gebeya.parking_lot.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.gebeya.parking_lot.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterFormViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository
): ViewModel() {

    val code = mutableStateOf(savedStateHandle.get<String>("code"))

    var firstNameError by mutableStateOf("")
    var lastNameError by mutableStateOf("")
    var emailError by mutableStateOf("")


    fun login(){}


    fun register(){}


    fun validateInput(firstName: String, lastName: String, email: String){

        firstNameError = if(firstName.isNullOrEmpty() || firstName.isNullOrBlank()){
            "First Name is required"
        }else{
            ""
        }

        lastNameError = if(lastName.isNullOrEmpty() || lastName.isNullOrBlank()){
            "Last Name is required"
        }else{
            ""
        }

        emailError = when {
            email.isNullOrEmpty() || email.isNullOrBlank() -> {
                "Email is required"
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                "Invalid email format"
            }
            else -> {
                ""
            }
        }


    }


}