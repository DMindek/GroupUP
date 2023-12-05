package com.intersoft.network.models.responses

import java.sql.Timestamp

data class EventBody (
    val event : EventData
)

data class EventData(
    val name: String,
    val description : String,
    val date : Timestamp,
    val duration : Int,
    val maxParticipants : Int,
    val location : String,
    val ownerId : Int
)