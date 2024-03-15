package com.gebeya.parking_lot.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gebeya.parking_lot.data.datapreference.DataStoreRepository
import com.gebeya.parking_lot.data.keystore.Role
import com.gebeya.parking_lot.domain.repository.KeystoreRepository
import com.gebeya.parking_lot.ui.util.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val repository: DataStoreRepository,
    private val keystoreRepository: KeystoreRepository
) : ViewModel() {

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _startDestination: MutableState<String> = mutableStateOf(Screen.Welcome.route)
    val startDestination: State<String> = _startDestination

    init {
        viewModelScope.launch {
            repository.readOnBoardingState().collect { completed ->
                if (completed) {
                    repository.getAuthenticationToken().collect { authenticationToken ->
                        when(authenticationToken != null){
                            true -> {
                                println("Authentication exists")
                                val role = keystoreRepository.getRole()
                                _startDestination.value = when (role) {
                                    Role.Driver -> {
                                        println(role.roleString)
                                        Screen.MainScreen.route
                                    }
                                    Role.Provider -> {
                                        println(role.roleString)
                                        Screen.ProviderMainScreen.route
                                    }
                                    else -> {
                                        if (role != null) {
                                            println(role.roleString)
                                        }else{
                                            println("no role")
                                        }
                                        Screen.Register.route
                                    }
                                }
                                _isLoading.value = false
                            }
                            false -> {
                                println("Authentication does not exists")
                                _startDestination.value = Screen.Register.route
                                _isLoading.value  = false
                            }
                        }
                    }
                } else {
                    _startDestination.value = Screen.Welcome.route
                    _isLoading.value  = false
                }
            }
            _isLoading.value = false
        }
    }
}
