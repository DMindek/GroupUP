package com.intersoft.event

object EventCreationManager {

    fun createEvent(eventName: String,description: String,selectedDateInMillis: Long,durationInMillis:Long,maxNumberOfParticipants: Int,location: String , onCreateEventSuccess: () -> Unit, onCreateEventFailure: (String) -> Unit ){
        val event = EventModel(name= eventName, description = description, dateInMillis =  selectedDateInMillis, durationInMillis = durationInMillis , maxParticipants = maxNumberOfParticipants,location = location)

        val errorText = validateInput(event)

        if(errorText == "")
            onCreateEventSuccess()
        else{
            onCreateEventFailure(errorText)
        }

    }

    private fun validateInput(event: EventModel): String {
        if(event.name.isEmpty()) return "Please enter event name"
        if(event.name.length >= 20) return "Event name must contain less than 20 characters"
        if(event.description.isEmpty()) return "Please enter event description"
        if(event.description.length >= 256) return "Event description must contain less than 256 characters"
        if(event.dateInMillis <= 0) return "Please enter event date"
        if(event.location.isBlank()) return "Please enter event location"

        return ""
    }
}