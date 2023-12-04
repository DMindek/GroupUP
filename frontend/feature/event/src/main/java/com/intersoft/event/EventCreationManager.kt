package com.intersoft.event

object EventCreationManager {

    fun createEvent(eventName: String,description: String,selectedDateInMillis: Long,durationInMillis:Long,maxNumberOfParticipants: Int,location: String , onCreateEventSuccess: () -> Unit, onCreateEventFailure: (String) -> Unit ){
        val event = EventModel(name= eventName, description = description, dateInMillis =  selectedDateInMillis, durationInMillis = durationInMillis , maxParticipants = maxNumberOfParticipants,location = location)
        onCreateEventFailure("Error text")
    }
}