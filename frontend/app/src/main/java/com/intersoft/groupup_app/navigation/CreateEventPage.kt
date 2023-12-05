package com.intersoft.groupup_app.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.intersoft.auth.AuthContext
import com.intersoft.event.EventCreationManager
import com.intersoft.ui.CounterElement
import com.intersoft.ui.DurationSelectionElement
import com.intersoft.ui.ErrorText
import com.intersoft.ui.GeneralDatePicker
import com.intersoft.ui.MultiLineTextInputField
import com.intersoft.ui.PrimaryButton
import com.intersoft.ui.TextInputField
import com.intersoft.ui.TitleText
import com.intersoft.ui.WarningText

@Composable
fun CreateEventPage(onCreateEvent: () -> Unit, onCancelEventCreation: () -> Unit) {
    var eventName by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }

    var selectedDateInMillis by remember{
        mutableLongStateOf(0)
    }

    var durationInMillis by remember {
        mutableLongStateOf(0)
    }
    var maxNumberOfParticipants by remember {
        mutableStateOf(0)
    }
    var location by remember {
        mutableStateOf("")
    }

    var errorText by remember {
        mutableStateOf("")
    }
    var warningText by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TitleText(text = "Create an event")
        TextInputField(label = "Name") { eventName = it }
        MultiLineTextInputField("Description") {description = it}
        GeneralDatePicker("Event Date"){selectedDateInMillis = it}
        DurationSelectionElement({warningText = it}){durationInMillis = it}
        WarningText(text = warningText)
        CounterElement(label = "Max Participants") {maxNumberOfParticipants = it}
        TextInputField(label = "location") { location = it }
        ErrorText(text = errorText)
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp)
        ) {
            PrimaryButton(buttonText = "Create") {
                EventCreationManager.createEvent(
                    eventName = eventName,
                    description = description,
                    selectedDateInMillis = selectedDateInMillis,
                    durationInMillis = durationInMillis,
                    maxNumberOfParticipants = maxNumberOfParticipants,
                    location = location,AuthContext.id!!,{onCreateEvent()}
                ){error ->
                    errorText = error
                }
            }
            Spacer(modifier = Modifier.width(100.dp))
            PrimaryButton(buttonText = "Cancel") {
                onCancelEventCreation()
            }
        }
    }
}
