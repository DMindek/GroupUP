package com.intersoft.event

object EventManager {
    private var eventRepository: IEventRepository = EventRepository()

    fun setEventRepository(repo: IEventRepository){
        eventRepository = repo
    }
    fun createEvent(eventName: String,description: String,selectedDateInMillis: Long,durationInMillis:Long,startTimeInMillis:Long,maxNumberOfParticipants: Int,location: String,ownerId: Int,onCreateEventSuccess: () -> Unit, onCreateEventFailure: (String) -> Unit ){
        val newEvent = EventModel(
            name= eventName,
            description = description,
            dateInMillis =  selectedDateInMillis,
            durationInMillis = durationInMillis ,
            startTimeInMillis = startTimeInMillis,
            maxParticipants = maxNumberOfParticipants,
            location = location,
            ownerId = ownerId
        )

        val errorText = validateInput(newEvent)

        if(errorText == ""){
            eventRepository.createEvent(newEvent,{error -> onCreateEventFailure(error)}){
                onCreateEventSuccess()
            }
        }
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