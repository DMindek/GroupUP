package com.intersoft.event

import com.intersoft.network.models.responses.StoredEventData

interface IEventRepository {
    fun createEvent(newEvent: EventModel, onCreateEventError: (String) -> Unit, onCreateEventSuccess: (String) -> Unit){}
    fun getEvent(eventId: Int, onGetEventSuccess: (StoredEventData) -> Unit, onGetEventError: (String?) -> Unit){}
}