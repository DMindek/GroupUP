package com.intersoft.location

import androidx.compose.runtime.Composable
interface ILocationServices {
    @Composable
    fun LocationPicker(onLocationChanged: (latitude: Double, longitude: Double) -> Unit, latitude: Double?, longitude: Double?, isEdit: Boolean)

    @Composable
    fun LocationDisplay(latitude: Double?, longitude: Double?)

    fun calculateDistance(myLatitude: Double, myLongitude: Double, latitude: Double, longitude: Double): Float
}