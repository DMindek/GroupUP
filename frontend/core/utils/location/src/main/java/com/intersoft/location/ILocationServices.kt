package com.intersoft.location

import androidx.compose.runtime.Composable
interface ILocationServices {
    @Composable
    fun LocationPicker(onLocationChanged: (longitude: Float, latitude: Float) -> Unit)

    @Composable
    fun LocationDisplay()

    fun calculateDistance(longitude: Float, latitude: Float): Float
}