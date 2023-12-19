package com.intersoft.event

import java.sql.Timestamp

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

    fun getEvent(eventId: Int, onGetEventError: (String?) -> Unit, onGetEventSuccess: (StoredEventData) -> Unit){
        eventRepository.getEvent(eventId,
            onGetEventSuccess = {
                val eventData = StoredEventData(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    date = it.date,
                    duration = it.duration,
                    max_participants = it.max_participants,
                    location = it.location,
                    owner_id = it.owner_id,
                    participants = it.participants
                )

                onGetEventSuccess(eventData)
            },
            onGetEventError = {
                onGetEventError(it)
            }
        )
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


    data class StoredEventData (
        val id : Int,
        val name: String,
        val description : String,
        val date : Timestamp,
        val duration : Int,
        val max_participants : Int,
        val location : String,
        val owner_id : Int,
        val participants : String?
    )
}