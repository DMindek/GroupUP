package com.intersoft.event

import android.util.Log
import com.google.gson.Gson
import com.intersoft.network.NetworkManager
import com.intersoft.network.models.responses.EditEventBody
import com.intersoft.network.models.responses.EventBody
import com.intersoft.network.models.responses.EventDetails
import com.intersoft.network.models.responses.NewEventData
import java.sql.Timestamp

class EventRepository: IEventRepository {

    override fun createEvent(newEvent: EventModel, onCreateEventError: (String) -> Unit, onCreateEventSuccess: (String) -> Unit) {
        Log.d("EventRepository", newEvent.toString())
        // Timestamp automatically adjusts to local time which is +1 offset for us, so we have to decrease the time by one hour in millis "3600000 ms" because it does not make sense to adjust starting time by 1 hour offset
        val dateTimestamp = java.sql.Timestamp(newEvent.dateInMillis + newEvent.startTimeInMillis - 3600000 )
        Log.d("EventRepository", dateTimestamp.toString())
        val durationInMinutes = (newEvent.durationInMillis / 60000).toInt()
        Log.d("EventRepository", durationInMinutes.toString())

        val event = NewEventData(
            name= newEvent.name,
            description = newEvent.description,
            date = dateTimestamp,
            duration =durationInMinutes,
            max_participants = newEvent.maxParticipants,
            location = newEvent.location,
            location_name = newEvent.locationName,
            owner_id =  newEvent.ownerId,
            id = newEvent.id,
            participants = emptyList()
        )

        NetworkManager.createEvent(
            eventData = EventBody(event),
            onCreateEventSuccess= {eventSuccessResponse ->
                onCreateEventSuccess(eventSuccessResponse.message)
            },
            onCreateEventFail = {
                Log.d("EventRepository", "Error occurred: $it")
                if(!it.isNullOrEmpty())if(it[0] != '{') {
                    onCreateEventError(it)
                }
                else{
                    val error: CreateEventFailResponse
                    try {
                        error = Gson().fromJson(it, CreateEventFailResponse::class.java)
                        Log.d("EventRepository", "Error occurred $error")
                    }catch (e: Exception){
                        onCreateEventError("Server returned unknown error")
                        return@createEvent
                    }
                }
            }
        )
    }

    override fun getEvent(eventId: Int,onGetEventError: (String?) -> Unit, onGetEventSuccess: (GetEventResponse) -> Unit) {

        NetworkManager.getEvent(
            eventId = eventId,
            onGetEventSuccess = {
               val response = GetEventResponse (
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    date = it.date,
                    duration = it.duration,
                    max_participants = it.max_participants,
                    location = it.location,
                    locationName = it.location_name,
                    owner_id = it.owner_id,
                   participants = it.participants?.map { participant ->
                       UserModel(
                           username = participant.username,
                           email = participant.email,
                           password = participant.password!!,
                           location = participant.location,
                           locationName = participant.location_name,
                           id = participant.id,
                       )
                   }
                )

                Log.d("EventRepository", "Recieved event: $response")

                onGetEventSuccess(response)
            },
            onGetEventFail = {
                Log.d("EventRepository", "Error occurred: $it")
                if(!it.isNullOrEmpty())if(it[0] != '{') {
                    onGetEventError(it)
                }
                else{
                    val error: GetEventResponse
                    try {
                        error = Gson().fromJson(it, GetEventResponse::class.java)
                        Log.d("EventRepository", "Error occurred $error")
                    }catch (e: Exception){
                        onGetEventError("Server returned unknown error")
                        return@getEvent
                    }
                }
            })
    }



    override fun getAvailableEvents(
        authToken: String,
        onGetUserEventsError: (String) -> Unit,
        onGetUserEventsSuccess: (List<EventModel>) -> Unit
    ) {
        NetworkManager.getAvailableEvents(
            authToken = authToken,
            onGetUserEventsSuccess = {events ->
                val eventModels = events.map { event ->
                    EventModel(
                        name = event.name,
                        description = event.description,
                        dateInMillis = event.date.time,
                        durationInMillis = event.duration.toLong() * 60000,
                        startTimeInMillis = event.date.time - event.duration.toLong() * 60000,
                        maxParticipants = event.max_participants,
                        location = event.location,
                        locationName = event.location_name,
                        ownerId = event.owner_id,
                        id = event.id!!,
                        participants = event.participants?.map { participant ->
                            UserModel(
                                username = participant.username,
                                email = participant.email,
                                password = participant.password!!,
                                location = participant.location,
                                locationName = participant.location_name,
                                id = participant.id,
                            )
                        }
                    )
                }
                onGetUserEventsSuccess(eventModels)
            },
            onGetUserEventsFail = {
                if(it!= null){
                    onGetUserEventsError(it)
                }
                else{
                    onGetUserEventsError("Unknown error occurred")
                }

            }
        )

    }

    override fun getJoinedEvents(
        userId: Int,
        authToken: String,
        onGetJoinedEventsError: (String) -> Unit,
        onGetJoinedEventsSuccess: (List<EventModel>) -> Unit
    ) {

        NetworkManager.getJoinedEvents(
            userId = userId,
            authToken = authToken,
            onGetUserEventsSuccess = {events ->
                val eventModels = events.map { event ->
                    EventModel(
                        name = event.name,
                        description = event.description,
                        dateInMillis = event.date.time,
                        durationInMillis = event.duration.toLong() * 60000,
                        startTimeInMillis = event.date.time - event.duration.toLong() * 60000,
                        maxParticipants = event.max_participants,
                        location = event.location,
                        locationName = event.location_name,
                        ownerId = event.owner_id,
                        id = event.id!!,
                        participants = event.participants?.map { participant ->
                            UserModel(
                                username = participant.username,
                                email = participant.email,
                                password = participant.password!!,
                                location = participant.location,
                                locationName = participant.location_name,
                                id = participant.id,
                            )
                        }
                    )
                }
                onGetJoinedEventsSuccess(eventModels)
            },
            onGetUserEventsFail = {
                if(it!= null){
                    onGetJoinedEventsError(it)
                }
                else{
                    onGetJoinedEventsError("Unknown error occurred")
                }

            }
        )

    }


    override fun getUserEvents(
        userId: Int,
        authtoken: String,
        onGetUserEventsError: (String) -> Unit,
        onGetUserEventsSuccess: (List<EventModel>) -> Unit
    ) {
        NetworkManager.getUserEvents(
            userId = userId,
            authtoken = authtoken,
            onGetUserEventsSuccess = {events ->
                val eventModels = events.map { event ->
                    EventModel(
                        name = event.name,
                        description = event.description,
                        dateInMillis = event.date.time,
                        durationInMillis = event.duration.toLong() * 60000,
                        startTimeInMillis = event.date.time - event.duration.toLong() * 60000,
                        maxParticipants = event.max_participants,
                        location = event.location,
                        locationName = event.location_name,
                        ownerId = event.owner_id,
                        id = event.id!!,
                        participants = event.participants?.map { participant ->
                            UserModel(
                                username = participant.username,
                                email = participant.email,
                                password = participant.password!!,
                                location = participant.location,
                                locationName = participant.location_name,
                                id = participant.id,
                            )
                        }
                    )
                }
                onGetUserEventsSuccess(eventModels)
            },
            onGetUserEventsFail = {
                if(it!= null){
                    onGetUserEventsError(it)
                }
                else{
                    onGetUserEventsError("Unknown error occurred")
                }

            }
        )

    }

    override fun editEvent(
        eventId: Int,
        newEvent: EventModel,
        authToken: String,
        onEditEventError: (String) -> Unit,
        onEditEventSuccess: (String) -> Unit
    ) {
        Log.d("EventRepository", newEvent.toString())
        // Timestamp automatically adjusts to local time which is +1 offset for us, so we have to decrease the time by one hour in millis "3600000 ms" because it does not make sense to adjust starting time by 1 hour offset
        val dateTimestamp = java.sql.Timestamp(newEvent.dateInMillis + newEvent.startTimeInMillis - 3600000 )
        Log.d("EventRepository", dateTimestamp.toString())
        val durationInMinutes = (newEvent.durationInMillis / 60000).toInt()
        Log.d("EventRepository", durationInMinutes.toString())

        val event = EventDetails(
            name = newEvent.name,
            description = newEvent.description,
            date = dateTimestamp,
            duration =durationInMinutes,
            max_participants = newEvent.maxParticipants,
            location = newEvent.location,
            location_name = newEvent.locationName,
            owner_id =  newEvent.ownerId,
            participants = null
        )

        NetworkManager.editEvent(
            eventId = eventId,
            eventData = EditEventBody(event),
            authToken = authToken,
            onEditEventSuccess= {eventSuccessResponse ->
                onEditEventSuccess(eventSuccessResponse.toString())
            },
            onEditEventFail = {
                Log.d("EventRepository", "Error occurred: $it")
                if(!it.isNullOrEmpty())if(it[0] != '{') {
                    onEditEventError(it)
                }
                else{
                    val error: CreateEventFailResponse
                    try {
                        error = Gson().fromJson(it, CreateEventFailResponse::class.java)
                        Log.d("EventRepository", "Error occurred $error")
                    }catch (e: Exception){
                        onEditEventError("Server returned unknown error")
                        return@editEvent
                    }
                }
            }
        )
    }
}








data class CreateEventFailResponse(
    val name: String?,
    val description : String?,
    val date : String?,
    val duration : String?,
    val maxParticipants : String?,
    val location : String?,
    val locationName : String,
    val ownerId : String?
)

data class GetEventResponse(
    val id : Int,
    val name: String,
    val description : String,
    val date : Timestamp,
    val duration : Int,
    val max_participants : Int,
    val location : String,
    val locationName : String,
    val owner_id : Int,
    val participants : List<UserModel>?
)

data class UserModel(
    val id: Int?,
    val email: String,
    val password: String,
    val username: String,
    val location: String,
    val locationName : String,
)
