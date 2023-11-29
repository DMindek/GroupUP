package com.intersoft.event


object EventCreationManager{

    fun createEvent(event: EventModel, onCreateEventSuccess: () -> Unit, onCreateEventFailure: (String) -> Unit ){

        onCreateEventFailure("Error text")
    }
}
