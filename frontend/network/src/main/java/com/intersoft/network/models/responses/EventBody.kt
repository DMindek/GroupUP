package com.intersoft.network.models.responses

import androidx.test.services.events.TimeStamp

data class EventBody (
    val event : EventData
)

data class EventData(
    val name: String,
    val description : String,
    val date : TimeStamp,
    val duration : Int,
    val maxParticipants : Int,
    val location : String,
    val ownerId : Int
)