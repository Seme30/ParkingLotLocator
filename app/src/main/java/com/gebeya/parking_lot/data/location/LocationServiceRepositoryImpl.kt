package com.gebeya.parking_lot.data.location

import android.annotation.SuppressLint
import android.os.Looper
import com.gebeya.parking_lot.domain.repository.LocationServiceRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class LocationServiceRepositoryImpl(
    val locationProviderClient: FusedLocationProviderClient
): LocationServiceRepository {
    @SuppressLint("MissingPermission")
    override fun requestLocationUpdate(): Flow<LatLng?> {
        return callbackFlow {

            val locationSetting = LocationRequest.Builder(30000L)
                .setIntervalMillis(15000L)
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .build()
            val locationCallBack = object : LocationCallback(){
                override fun onLocationResult(locationResult: LocationResult) {
                    locationResult.locations.lastOrNull()?.let {
                        trySend(LatLng(it.latitude, it.longitude))
                    }
                }
            }

            locationProviderClient.requestLocationUpdates(
                locationSetting,
                locationCallBack,
                Looper.getMainLooper()
            )

            awaitClose {
                locationProviderClient.removeLocationUpdates(locationCallBack)
            }

        }
    }
}