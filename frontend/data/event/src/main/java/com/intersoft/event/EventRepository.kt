package com.intersoft.event

import android.util.Log
import com.google.gson.Gson
import com.intersoft.network.NetworkManager
import com.intersoft.network.models.responses.EventBody
import com.intersoft.network.models.responses.EventData

class EventRepository: IEventRepository {

    override fun createEvent(newEvent: EventModel, onCreateEventError: (String) -> Unit, onCreateEventSuccess: (String) -> Unit) {
        Log.d("EventRepository", newEvent.toString())
        // Timestamp automatically adjusts to local time which is +1 offset for us, so we have to decrease the time by one hour in millis "3600000 ms" because it does not make sense to adjust starting time by 1 hour offset
        val dateTimestamp = java.sql.Timestamp(newEvent.dateInMillis + newEvent.startTimeInMillis - 3600000 )
        Log.d("EventRepository", dateTimestamp.toString())
        val durationInMinutes = (newEvent.durationInMillis / 60000).toInt()
        Log.d("EventRepository", durationInMinutes.toString())

        val event = EventData(
            name= newEvent.name,
            description = newEvent.description,
            date = dateTimestamp,
            duration =durationInMinutes,
            max_participants = newEvent.maxParticipants,
            location = newEvent.location,
            owner_id =  newEvent.ownerId,
            id = newEvent.id
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

    override fun getUserEvents(
        userId: Int,
        onGetUserEventsError: (String) -> Unit,
        onGetUserEventsSuccess: (List<EventModel>) -> Unit
    ) {
        NetworkManager.getUserEvents(
            userId = userId,
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
                        ownerId = event.owner_id,
                        id = event.id!!
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
}




data class CreateEventFailResponse(
    val name: String?,
    val description : String?,
    val date : String?,
    val duration : String?,
    val maxParticipants : String?,
    val location : String?,
    val ownerId : String?
)