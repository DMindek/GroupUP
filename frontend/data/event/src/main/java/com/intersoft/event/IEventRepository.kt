package com.intersoft.event

interface IEventRepository {
    fun createEvent(newEvent: EventModel, onCreateEventError: (String) -> Unit, onCreateEventSuccess: (String) -> Unit){}
    fun getEvent(eventId: Int, onGetEventError: (String?) -> Unit, onGetEventSuccess: (GetEventResponse) -> Unit, ){}

}