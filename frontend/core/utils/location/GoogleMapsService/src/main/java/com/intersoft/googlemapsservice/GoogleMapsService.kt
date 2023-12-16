package com.intersoft.googlemapsservice

import android.location.Location
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.intersoft.location.ILocationServices

object GoogleMapsService: ILocationServices {
    override fun getName(): String{
        return "Google maps"
    }

    @Composable
    override fun LocationPicker(
        onLocationChanged: (latitude: Double, longitude: Double) -> Unit,
        latitude: Double?,
        longitude: Double?,
        isEdit: Boolean
    ) {
        val startPos = if(latitude != null && longitude != null) LatLng(latitude,longitude)
        else LatLng(46.307833, 16.338050)

        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(startPos, 15f)
        }
        val markerState = rememberMarkerState()
        if(isEdit){
            markerState.position = startPos
        }

        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            cameraPositionState = cameraPositionState,
            onMapClick = {pos ->
                markerState.position = pos
                onLocationChanged(pos.latitude, pos.longitude)
            }
        ) {
            Marker(
                state = markerState,
            )
        }

    }

    @Composable
    override fun LocationDisplay(latitude: Double?, longitude: Double?) {
        if(latitude != null && longitude != null) {
            val pos = LatLng(latitude, longitude)

            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(pos, 15f)
            }
            val markerState = rememberMarkerState(position = pos)
            GoogleMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                cameraPositionState = cameraPositionState
            ) {
                Marker(
                    state = markerState,
                )
            }
        }
        else{
            Text(text = "Location not found", color = Color.Red)
        }
    }

    override fun calculateDistance(
        myLatitude: Double,
        myLongitude: Double,
        latitude: Double,
        longitude: Double
    ): Float {
        val results = FloatArray(size = 3)
        Location.distanceBetween(myLatitude, myLongitude, latitude, longitude, results)
        return results[0] / 1000
    }
}