package com.gebeya.parking_lot.data.keystore

sealed class Role(val roleString: String) {
    object Driver : Role("driver")
    object Provider : Role("provider")
}