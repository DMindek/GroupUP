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

    fun deleteEvent(eventId: Int, onDeleteEventError: (String) -> Unit, onDeleteEventSuccess: (String) -> Unit){}
    fun joinEvent(eventId: Int, userId: Int, authToken: String, onJoinEventError: (String) -> Unit, onJoinEventSuccess: (String) -> Unit)
    fun leaveEvent(eventId: Int, userId: Int, authToken: String, onLeaveEventError: (String) -> Unit, onLeaveEventSuccess: (String) -> Unit)
}