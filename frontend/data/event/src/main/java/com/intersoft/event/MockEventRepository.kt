package com.intersoft.event

import java.time.Instant

class MockEventRepository : IEventRepository {
    private val eventList = mutableListOf(
        EventModel("Test1","Test", Instant.now().toEpochMilli() + 1800000,3600000,3600000,5, "10.0,10.0", "location", 0),
        EventModel("Test2","Test2", Instant.now().toEpochMilli() + 3600000,3600000,3600000,3,"10.0,10.0", "location2", 0),
    )

    override fun createEvent(newEvent: EventModel, onCreateEventError: (String) -> Unit, onCreateEventSuccess: (String) -> Unit) {
        if(eventList.find { event -> (event.name == newEvent.name) && (event.location == newEvent.location) } == null){
            eventList.add(newEvent)
            onCreateEventSuccess("Successfully created event")
        }
        else{
            onCreateEventError("Event already exists")
        }
    }

    override fun getUserEvents(
        userId: Int,
        authtoken: String,
        onGetUserEventsError: (String) -> Unit,
        onGetUserEventsSuccess: (List<EventModel>) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun getAvailableEvents(
        authtoken: String,
        onGetUserEventsError: (String) -> Unit,
        onGetUserEventsSuccess: (List<EventModel>) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun getJoinedEvents(
        userId: Int,
        authToken: String,
        onGetJoinedEventsError: (String) -> Unit,
        onGetJoinedEventsSuccess: (List<EventModel>) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun deleteEvent(
        eventId: Int,
        onDeleteEventError: (String) -> Unit,
        onDeleteEventSuccess: (String) -> Unit
    ) {
        eventList.removeIf { it.id == eventId }
    }

    override fun joinEvent(
        eventId: Int,
        userId: Int,
        authToken: String,
        onJoinEventError: (String) -> Unit,
        onJoinEventSuccess: (String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun leaveEvent(
        eventId: Int,
        userId: Int,
        authToken: String,
        onLeaveEventError: (String) -> Unit,
        onLeaveEventSuccess: (String) -> Unit
    ) {
        TODO("Not yet implemented")
    }
}