package com.intersoft.event

import com.intersoft.ui.IIterableObject

data class EventModel(
    var name : String,
    var description : String,
    var dateInMillis : Long,
    var durationInMillis : Long,
    var startTimeInMillis : Long,
    var maxParticipants : Int,
    var location : String,
    var ownerId : Int,
    var id : Int = 0
): IIterableObject {
    override fun getId(): Int {
        return id
    }

    override fun getMainText(): String {
        return name;
    }

    override fun getSecondaryText(): String {
        return location;
    }
}

