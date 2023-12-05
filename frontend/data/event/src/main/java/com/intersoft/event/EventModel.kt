package com.intersoft.event

data class EventModel(
    var name : String,
    var description : String,
    var dateInMillis : Long,
    var durationInMillis: Long,
    var maxParticipants : Int,
    var location : String,
    var ownerId : Int
)
