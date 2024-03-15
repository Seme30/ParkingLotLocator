package com.gebeya.parking_lot.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gebeya.parking_lot.domain.repository.KeystoreRepository
import com.gebeya.parking_lot.data.keystore.Role
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoleViewModel @Inject constructor (private val keystoreRepository: KeystoreRepository) : ViewModel() {

    var userRole by mutableStateOf<Role?>(null)


    fun getRole(){
        viewModelScope.launch {
            userRole = keystoreRepository.getRole()
        }
    }

    fun storeRole(role: Role){
        viewModelScope.launch {
            keystoreRepository.setRole(
                role
            )
        }
    }



}
