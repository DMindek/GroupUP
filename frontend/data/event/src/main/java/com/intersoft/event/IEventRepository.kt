package com.intersoft.event

interface IEventRepository {
    fun createEvent(newEvent: EventModel, onCreateEventError: (String) -> Unit, onCreateEventSuccess: (String) -> Unit){
    }
    fun getUserEvents(
        userId: Int,
        authtoken: String,
        onGetUserEventsError: (String) -> Unit,
        onGetUserEventsSuccess: (List<EventModel>) -> Unit)
}