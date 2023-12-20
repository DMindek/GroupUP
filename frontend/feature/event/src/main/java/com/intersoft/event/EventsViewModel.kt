package com.intersoft.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intersoft.ui.IIterableObject


class EventsViewModel : ViewModel(){

    private var eventRepository: IEventRepository = EventRepository()
    private val _events = MutableLiveData<List<IIterableObject>> (null)
    private val _error = MutableLiveData("")
    val events: LiveData<List<IIterableObject>> get() = _events
    val error: LiveData<String> get() = _error

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

}