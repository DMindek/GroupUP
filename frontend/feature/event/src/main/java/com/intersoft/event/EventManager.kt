package com.intersoft.event

import com.intersoft.user.IUserRepository
import com.intersoft.user.UserRepository
import java.sql.Timestamp

object EventManager {
    private var eventRepository: IEventRepository = EventRepository()

    fun setEventRepository(repo: IEventRepository){
        eventRepository = repo
    }

    fun createEvent(eventName: String,description: String,selectedDateInMillis: Long,durationInMillis:Long,startTimeInMillis:Long,maxNumberOfParticipants: Int,location: String,locationName: String,ownerId: Int,onCreateEventSuccess: () -> Unit, onCreateEventFailure: (String) -> Unit ){
        val newEvent = EventModel(
            name = eventName,
            description = description,
            dateInMillis =  selectedDateInMillis,
            durationInMillis = durationInMillis ,
            startTimeInMillis = startTimeInMillis,
            maxParticipants = maxNumberOfParticipants,
            location = location,
            locationName = locationName,
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

    fun editEvent(
        eventId: Int,
        eventName: String,
        description: String,
        selectedDateInMillis: Long,
        durationInMillis:Long,
        startTimeInMillis:Long,
        maxNumberOfParticipants: Int,
        location: String,
        locationName : String,
        ownerId: Int,
        authToken: String,
        onEditEventSuccess: () -> Unit,
        onEditEventFailure: (String) -> Unit
    ){
        val newEvent = EventModel(
            name = eventName,
            description = description,
            dateInMillis =  selectedDateInMillis,
            durationInMillis = durationInMillis ,
            startTimeInMillis = startTimeInMillis,
            maxParticipants = maxNumberOfParticipants,
            location = location,
            locationName = locationName,
            ownerId = ownerId
        )
        val errorText = validateInput(newEvent)

        if(errorText == ""){
            eventRepository.editEvent(eventId,newEvent,authToken,{error -> onEditEventFailure(error)}){
                onEditEventSuccess()
            }
        }
        else{
            onEditEventFailure(errorText)
        }
    }
    private fun validateInput(event: EventModel): String {
        if(event.name.isEmpty()) return "Please enter event name"
        if(event.name.length >= 20) return "Event name must contain less than 20 characters"
        if(event.description.isEmpty()) return "Please enter event description"
        if(event.description.length >= 256) return "Event description must contain less than 256 characters"
        if(event.dateInMillis <= 0) return "Please enter event date"
        if(event.location.isBlank()) return "Please enter event location"
        if(event.locationName.isBlank()) return "Please enter event location name"

        return ""
    }

     fun getEvent(eventId: Int, onGetEventError: (String?) -> Unit, onGetEventSuccess: (RecievedEventData) -> Unit){
        eventRepository.getEvent(eventId,
            onGetEventSuccess = {
                val eventData = RecievedEventData(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    date = it.date,
                    duration = it.duration,
                    max_participants = it.max_participants,
                    location = it.location,
                    locationName = it.locationName,
                    owner_id = it.owner_id,
                    participants = it.participants?.map {user ->
                        ReceivedUserData(
                            username = user.username,
                            email = user.email,
                            password = user.password,
                            location = user.location,
                            locationName = user.locationName,
                            id = user.id
                        )
                    }
                )

                onGetEventSuccess(eventData)
            },
            onGetEventError = {
                onGetEventError(it)
            }
        )
    }

    fun getHostname(hostId: Int, authToken: String, onGetHostnameError: (String?) -> Unit, onGetHostnameSuccess: (String) -> Unit) {
        val userRepository:IUserRepository = UserRepository()

        userRepository.getHostname(
            hostId,
            authToken,
            onGetHostNameSuccess = {onGetHostnameSuccess(it)},
            onGetHostnameError = {onGetHostnameError(it) }
        )
    }

    fun deleteEvent(eventId: Int, onDeleteSuccess: (String) -> Unit, onDeleteFail: (String) -> Unit){
        eventRepository.deleteEvent(eventId, onDeleteEventSuccess = onDeleteSuccess, onDeleteEventError = onDeleteFail)
    }

    data class RecievedEventData (
        val id : Int,
        val name: String,
        val description : String,
        val date : Timestamp,
        val duration : Int,
        val max_participants : Int,
        val location : String,
        val locationName : String,
        val owner_id : Int,
        val participants : List<ReceivedUserData>?
    )
    data class ReceivedUserData (
        val id: Int?,
        val email: String,
        val password: String,
        val username: String,
        val location: String,
        val locationName : String,
    )
}