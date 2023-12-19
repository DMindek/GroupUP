package com.intersoft.event

import android.util.Log
import com.google.gson.Gson
import com.intersoft.network.NetworkManager
import com.intersoft.network.models.responses.EventBody
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
            owner_id =  newEvent.ownerId
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

    override fun getEvent(eventId: Int,onGetEventError: (String?) -> Unit, onGetEventSuccess: (GetEventResponse) -> Unit){

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
                    owner_id = it.owner_id,
                    participants = it.participants
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

data class GetEventResponse(
    val id : Int,
    val name: String,
    val description : String,
    val date : Timestamp,
    val duration : Int,
    val max_participants : Int,
    val location : String,
    val owner_id : Int,
    val participants : List<String>?
)
