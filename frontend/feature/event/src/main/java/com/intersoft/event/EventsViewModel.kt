package com.intersoft.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intersoft.ui.IIterableObject


class EventsViewModel : ViewModel(){

    private var eventRepository: IEventRepository = EventRepository()

    private val _events: MutableLiveData<List<IIterableObject>> = MutableLiveData(null)
    private val _error : MutableLiveData<String> = MutableLiveData("")
    private val _canJoin: MutableLiveData<Boolean> = MutableLiveData(false)
    val events: LiveData<List<IIterableObject>> = _events
    val error: LiveData<String> = _error
    val canJoin: LiveData<Boolean> = _canJoin

    val CAN_JOIN = "Successfully joined the Event."

    fun fetchUserCurrentEvents(userId: Int, authtoken: String){
        eventRepository.getUserEvents(userId, authtoken, {error ->
            _error.value = error
        }){events ->
            if(events.isEmpty()){
                _error.value = "NO_EVENTS"
            }
            val listOfEvents = events.map { event ->
                event.processIntoIInterableObject()
            }
            
            _events.value = listOfEvents
        }
    }

    fun fetchAvailableEvents(authToken: String){
        eventRepository.getAvailableEvents( authToken, {error ->
            _error.value = error
        }){events ->
            if(events.isEmpty()){
                _error.value = "NO_EVENTS"
            }
            val listOfEvents = events.map { event ->
                event.processIntoIInterableObject()
            }

            _events.value = listOfEvents
        }
    }

    fun fetchJoinedEvents(userId: Int, authToken: String) {
        eventRepository.getJoinedEvents(userId, authToken, {error ->
            _error.value = error
        }){events ->
            if(events.isEmpty()){
                _error.value = "NO_EVENTS"
            }
            val listOfEvents = events.map { event ->
                event.processIntoIInterableObject()
            }

            _events.value = listOfEvents
        }
    }

    fun joinEvent(eventId: Int, userId: Int, authToken: String){
        eventRepository.joinEvent(eventId, userId, authToken, {error ->
            _canJoin.value = false
            _error.value = error
        }){
            _canJoin.value = true
        }
    }

}