package com.intersoft.event

import android.util.Log
import com.intersoft.network.models.responses.EventData

class EventRepository: IEventRepository {

    override fun createEvent(newEvent: EventModel, onCreateEventError: (String) -> Unit, onCreateEventSuccess: () -> Unit) {
        Log.d("EventRepository", newEvent.toString())
        val dateTimestamp = java.sql.Timestamp(newEvent.dateInMillis)
        Log.d("Event TimeStamp", dateTimestamp.toString())
        val durationInMinutes = (newEvent.durationInMillis / 60000).toInt()

        val event = EventData(
            name= newEvent.name,
            description = newEvent.description,
            date = dateTimestamp,
            duration =durationInMinutes,
            maxParticipants = newEvent.maxParticipants,
            location = newEvent.location,
            ownerId =  newEvent.ownerId
        )
    }
}