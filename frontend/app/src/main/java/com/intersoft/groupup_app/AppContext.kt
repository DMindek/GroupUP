package com.intersoft.groupup_app

import com.intersoft.googlemapsservice.GoogleMapsService
import com.intersoft.location.ILocationServices
import com.intersoft.osmservice.OSMLocationServices

object AppContext {
    private var LocationService: ILocationServices = OSMLocationServices

    private val modules: Map<String, ILocationServices> = mapOf(
        OSMLocationServices.getName() to OSMLocationServices,
        GoogleMapsService.getName() to GoogleMapsService,
    )

    fun getLocationService(): ILocationServices{
        return LocationService
    }

    fun getLocationServicesNames(): Set<String>{
        return modules.keys
    }

    fun setLocationService(name: String){
        LocationService = modules[name]!!
    }
}