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
    val max_participants : Int,
    val location : String,
    val owner_id : Int,
    val id: Int?,
    val participants : List<UserData>?
)
