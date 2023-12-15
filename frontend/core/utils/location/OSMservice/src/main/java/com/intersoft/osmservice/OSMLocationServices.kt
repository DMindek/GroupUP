package com.intersoft.osmservice

import android.location.Location
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.intersoft.location.ILocationServices
import com.utsman.osmandcompose.Marker
import com.utsman.osmandcompose.OpenStreetMap
import com.utsman.osmandcompose.rememberCameraState
import com.utsman.osmandcompose.rememberMarkerState
import org.osmdroid.util.GeoPoint

class OSMLocationServices: ILocationServices {
    @Composable
    override fun LocationPicker(onLocationChanged: (latitude: Double, longitude: Double) -> Unit, latitude: Double?, longitude: Double?) {
        val currentLocation = if(longitude != null && latitude != null) GeoPoint(latitude, longitude)
        else GeoPoint(46.307833, 16.338050)

        val marker = rememberMarkerState()

        val cameraState = rememberCameraState{
            geoPoint = currentLocation
            zoom = 15.0
        }

        OpenStreetMap(
            modifier = Modifier.fillMaxWidth(),
            cameraState = cameraState,
            onMapClick = {}
        )
    }

    @Composable
    override fun LocationDisplay(latitude: Double, longitude: Double) {
        val location = GeoPoint(latitude, longitude)

        val markerState = rememberMarkerState(
            geoPoint = location
        )

        val cameraState = rememberCameraState{
            geoPoint = location
            zoom = 15.0
        }

        OpenStreetMap(
            modifier = Modifier.fillMaxWidth(),
            cameraState = cameraState,
        ){
            Marker(state = markerState)
        }
    }

    override fun calculateDistance(myLatitude: Double, myLongitude: Double, latitude: Double, longitude: Double): Float {
        val results = FloatArray(size = 3)
        Location.distanceBetween(myLatitude, myLongitude, latitude, longitude, results)
        return results[0]
    }
}