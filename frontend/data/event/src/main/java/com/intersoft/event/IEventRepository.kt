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
    fun getHostname(hostId: Int, authToken: String,onGetHostnameError: (String?) -> Unit ,onGetHostNameSuccess: (String) -> Unit){}
    fun getAvailableEvents(
        authToken: String,
        onGetUserEventsError: (String) -> Unit,
        onGetUserEventsSuccess: (List<EventModel>) -> Unit
    )

}