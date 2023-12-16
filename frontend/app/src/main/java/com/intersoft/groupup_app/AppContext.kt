package com.intersoft.groupup_app

import com.intersoft.location.ILocationServices
import com.intersoft.osmservice.OSMLocationServices

object AppContext {
    val LocationService: ILocationServices = OSMLocationServices
}