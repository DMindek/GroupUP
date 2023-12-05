package com.intersoft.event

import android.util.Log

class EventRepository: IEventRepository {

    override fun createEvent(newEvent: EventModel, onCreateEventError: (String) -> Unit, onCreateEventSuccess: () -> Unit) {
        Log.d("EventRepository", newEvent.toString())
    }
}