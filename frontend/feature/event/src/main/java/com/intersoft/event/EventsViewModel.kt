package com.intersoft.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class EventsViewModel : ViewModel(){

    private var eventRepository: IEventRepository = EventRepository()
    private val _events = MutableLiveData<List<EventModel>> (emptyList())
    private val _error = MutableLiveData<String>("")
    val events: LiveData<List<EventModel>> get() = _events
    val error: LiveData<String> get() = _error

    fun fetchUserCurrentEvents(userId: Int){
        _error.value = ""
        _events.value = emptyList()

        eventRepository.getUserEvents(userId,{error ->
            _error.value = error
        }){events ->
            if(events.isEmpty()){
                _error.value = "NO_EVENTS"
            }

            _events.value = events
        }
    }

}