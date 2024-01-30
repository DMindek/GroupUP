package com.intersoft.osmservice

import android.location.Location
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.intersoft.location.ILocationServices
import com.utsman.osmandcompose.Marker
import com.utsman.osmandcompose.OpenStreetMap
import com.utsman.osmandcompose.rememberCameraState
import com.utsman.osmandcompose.rememberMarkerState
import org.osmdroid.util.GeoPoint

object OSMLocationServices: ILocationServices {
    override fun getName(): String{
        return "OpenStreetMaps"
    }

    @Composable
    override fun LocationPicker(onLocationChanged: (latitude: Double, longitude: Double) -> Unit, latitude: Double?, longitude: Double?, isEdit: Boolean) {
        val currentLocation = if(longitude != null && latitude != null) GeoPoint(latitude, longitude)
        else GeoPoint(46.307833, 16.338050)

        val marker = rememberMarkerState()
        if(isEdit && latitude != null && longitude != null){
            marker.geoPoint = GeoPoint(latitude, longitude)
        }

        val cameraState = rememberCameraState{
            geoPoint = currentLocation
            zoom = 18.0
        }

        Surface(modifier = Modifier.fillMaxWidth().height(250.dp)){
            OpenStreetMap(
                modifier = Modifier.fillMaxSize(),
                cameraState = cameraState,
                onMapClick = {
                    marker.geoPoint = it
                    onLocationChanged(it.latitude, it.longitude)
                }
            ){
                Marker(state = marker)
            }
        }
    }

    @Composable
    override fun LocationDisplay(latitude: Double?, longitude: Double?) {
        if(latitude != null && longitude != null) {
            val location = GeoPoint(latitude, longitude)

            val markerState = rememberMarkerState(
                geoPoint = location
            )

            val cameraState = rememberCameraState {
                geoPoint = location
                zoom = 18.0
            }

            Surface(modifier = Modifier.fillMaxWidth().height(250.dp)) {
                OpenStreetMap(
                    modifier = Modifier.fillMaxWidth(),
                    cameraState = cameraState,
                ) {
                    Marker(state = markerState)
                }
            }
        }
        else{
            Text(text = "Location not found", color = Color.Red)
        }
    }

    override fun calculateDistance(myLatitude: Double, myLongitude: Double, latitude: Double, longitude: Double): Float {
        val results = FloatArray(size = 3)
        Location.distanceBetween(myLatitude, myLongitude, latitude, longitude, results)
        return results[0] / 1000
    }
}