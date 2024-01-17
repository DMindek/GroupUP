package com.intersoft.event

interface IEventRepository {
    fun createEvent(newEvent: EventModel, onCreateEventError: (String) -> Unit, onCreateEventSuccess: (String) -> Unit){
    }

    fun editEvent(eventId: Int,newEvent: EventModel,authToken: String ,onCreateEventError: (String) -> Unit, onCreateEventSuccess: (String) -> Unit){
    }
}