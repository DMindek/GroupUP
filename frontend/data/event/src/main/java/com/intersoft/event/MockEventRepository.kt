package com.intersoft.event

import java.time.Instant

class MockEventRepository : IEventRepository {
    private val eventList = mutableListOf(
        EventModel("Test1","Test", Instant.now().toEpochMilli() + 1800000,3600000,5,"location"),
        EventModel("Test2","Test2", Instant.now().toEpochMilli() + 3600000,3600000,3,"location2"),
    )

    override fun createEvent(newEvent: EventModel, onCreateEventError: (String) -> Unit, onCreateEventSuccess: () -> Unit) {
        if(eventList.find { event -> (event.name == newEvent.name) && (event.location == newEvent.location) } == null){
            eventList.add(newEvent)
            onCreateEventSuccess()
        }
        else{
            onCreateEventError("Event already exists")
        }
    }
}