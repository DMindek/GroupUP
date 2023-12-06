package com.intersoft.event

import android.util.Log
import com.intersoft.network.models.responses.EventData

class EventRepository: IEventRepository {

    override fun createEvent(newEvent: EventModel, onCreateEventError: (String) -> Unit, onCreateEventSuccess: () -> Unit) {
        Log.d("EventRepository", newEvent.toString())
        // Timestamp automatically adjusts to local time which is +1 offset for us, so we have to decrease the time by one hour in millis "3600000 ms" because it does not make sense to adjust starting time by 1 hour offset
        val dateTimestamp = java.sql.Timestamp(newEvent.dateInMillis + newEvent.startTimeInMillis - 3600000 )
        Log.d("EventRepository", dateTimestamp.toString())
        val durationInMinutes = (newEvent.durationInMillis / 60000).toInt()
        Log.d("EventRepository", durationInMinutes.toString())

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