package com.intersoft.network.models.responses

import java.sql.Timestamp

data class EventBody (
    val event : NewEventData
)
data class EditEventBody(
    val event: EventDetails
)

data class NewEventData(
    val name: String,
    val description : String,
    val date : Timestamp,
    val duration : Int,
    val max_participants : Int,
    val location : String,
    val location_name : String?,
    val owner_id : Int,
    val id: Int?,
    val participants : List<UserData>?
)

data class StoredEventData(
    val id : Int,
    val name: String,
    val description : String,
    val date : Timestamp,
    val duration : Int,
    val max_participants : Int,
    val location : String,
    val location_name : String?,
    val owner_id : Int,
    val participants : List<UserData>?
)

data class EventDetails(
    val name: String,
    val description : String,
    val date : Timestamp,
    val duration : Int,
    val max_participants : Int,
    val location : String,
    val location_name : String?,
    val owner_id : Int,
    val participants : List<UserData>?
)
