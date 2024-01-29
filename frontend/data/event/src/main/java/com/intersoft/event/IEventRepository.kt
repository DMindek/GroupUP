package com.intersoft.event

interface IEventRepository {
    fun createEvent(newEvent: EventModel, onCreateEventError: (String) -> Unit, onCreateEventSuccess: (String) -> Unit){
    }
    fun getUserEvents(
        userId: Int,
        authtoken: String,
        onGetUserEventsError: (String) -> Unit,
        onGetUserEventsSuccess: (List<EventModel>) -> Unit)
    fun getEvent(eventId: Int, onGetEventError: (String?) -> Unit, onGetEventSuccess: (GetEventResponse) -> Unit, ){}
    fun getAvailableEvents(
        authToken: String,
        onGetUserEventsError: (String) -> Unit,
        onGetUserEventsSuccess: (List<EventModel>) -> Unit
    )

    fun getJoinedEvents(
        userId: Int,
        authToken: String,
        onGetJoinedEventsError: (String) -> Unit,
        onGetJoinedEventsSuccess: (List<EventModel>) -> Unit
    )


    fun editEvent(eventId: Int,newEvent: EventModel,authToken: String ,onEditEventError: (String) -> Unit, onEditEventSuccess: (String) -> Unit){
    }
}