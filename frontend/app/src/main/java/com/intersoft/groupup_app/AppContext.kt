package com.intersoft.groupup_app

import com.intersoft.googlemapsservice.GoogleMapsService
import com.intersoft.location.ILocationServices
import com.intersoft.osmservice.OSMLocationServices

object AppContext {
    //val LocationService: ILocationServices = OSMLocationServices
    val LocationService: ILocationServices = GoogleMapsService
}