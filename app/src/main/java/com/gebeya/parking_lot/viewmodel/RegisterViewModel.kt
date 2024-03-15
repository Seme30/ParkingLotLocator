package com.gebeya.parking_lot.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gebeya.parking_lot.data.datapreference.DataStoreRepository
import com.gebeya.parking_lot.data.network.model.PhoneRequest
import com.gebeya.parking_lot.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    val userRepository: UserRepository,
    val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    var phoneError by mutableStateOf("")

    fun validatePhoneNumber(phone: String) {
        val pattern = "^\\+251[97][0-9]{8}$".toRegex()
        phoneError = when {
            phone.isNullOrEmpty() -> "Phone number is required"
            phone.isNullOrBlank() -> "Phone number cannot be blank"
            !phone.startsWith("+251") -> "Phone number must start with +251"
            !phone.substring(4).startsWith("9") && !phone.substring(4)
                .startsWith("7") -> "After +251, the next digit should be 9 or 7"

            !phone.matches(pattern) -> "Phone number should be 13 digits long, including +251"
            else -> ""
        }
    }


    fun authUser(
        phone: String
    ) {
        viewModelScope.launch {
            userRepository.authUser(PhoneRequest(phoneNo = phone))
            dataStoreRepository.savePhoneNumber(phoneNumber = phone)
        }
    }

}



