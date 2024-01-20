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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.intersoft.auth.AuthContext
import com.intersoft.event.EventManager
import com.intersoft.ui.CounterElement
import com.intersoft.ui.DurationSelectionElement
import com.intersoft.ui.ErrorText
import com.intersoft.ui.GeneralDatePicker
import com.intersoft.ui.MultiLineTextInputField
import com.intersoft.ui.PrimaryButton
import com.intersoft.ui.TextInputField
import com.intersoft.ui.TitleText
import com.intersoft.ui.WarningText
import com.intersoft.utils.DateTimeManager

@Composable
fun EditEventPage(
    eventId: Int,
    eventName: String,
    description: String,
    selectedDateInMillis: Long,
    startTimeInMillis: Long,
    durationInMillis: Long,
    maxNumberOfParticipants: Int,
    location: String,
    hostId: Int,
    onEditEvent: () -> Unit,
    onCancelEditEvent: () -> Unit
){
    var editedEventName by remember {
        mutableStateOf(eventName)
    }
    var editedDescription by remember {
        mutableStateOf(description)
    }

    var editedSelectedDateInMillis by remember{
        mutableLongStateOf(selectedDateInMillis)
    }

    var editedStartTimeInMillis by remember {
        mutableLongStateOf(startTimeInMillis)
    }

    var editedDurationInMillis by remember {
        mutableLongStateOf(0)
    }
    var editedMaxNumberOfParticipants by remember {
        mutableIntStateOf(maxNumberOfParticipants)
    }
    var editedLocation by remember {
        mutableStateOf("")
    }

    var errorText by remember {
        mutableStateOf("")
    }
    var warningText by remember {
        mutableStateOf("")
    }

    val currentSelectedDate = DateTimeManager.formatMilisDatetoString(selectedDateInMillis)
    val currentDurationHours = DateTimeManager.calculateHoursFromMillis(durationInMillis)
    val currentDurationMinutes = DateTimeManager.calculateMinutesFromMillis(durationInMillis) - currentDurationHours * 60 // We deduct the amount of hours from the total time to get the minute duration
    val currentStartTimeHours = DateTimeManager.calculateHoursFromMillis(startTimeInMillis)
    val currentStartTimeMinutes = DateTimeManager.calculateMinutesFromMillis(startTimeInMillis) - currentStartTimeHours * 60 // We deduct the amount of hours from the total time to get the minute duration
    val currentStartTime: MutableList<Int> = mutableListOf(currentStartTimeHours.toInt(), currentStartTimeMinutes.toInt())
    val currentStartTimeText = DateTimeManager.formatStartTime(currentStartTime)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TitleText(text = "Editing: $eventName")
        TextInputField(label = "Name", placeholder = eventName) { editedEventName = it }
        MultiLineTextInputField("Description", placeholder = description) {editedDescription = it}
        GeneralDatePicker("Event Date", placeholder = currentSelectedDate){editedSelectedDateInMillis = it}
        DurationSelectionElement(
            placeholderStartTime = currentStartTimeText,
            placeholderHours = currentDurationHours.toString(),
            placeholderMinutes = currentDurationMinutes.toString(),
            {text -> warningText = text},
            {duration -> editedDurationInMillis = duration},
            {startTime-> editedStartTimeInMillis = startTime}
        )
        WarningText(text = warningText)
        CounterElement(label = "Max Participants", placeholder = maxNumberOfParticipants) {editedMaxNumberOfParticipants = it}
        TextInputField(label = "location") { editedLocation = it }
        ErrorText(text = errorText)
        Spacer(modifier = Modifier.height(20.dp))


        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp)
        ) {
            PrimaryButton(buttonText = "Apply changes") {
                EventCreationManager.editEvent(
                    eventId = eventId,
                    eventName = eventName,
                    description = description,
                    selectedDateInMillis = selectedDateInMillis,
                    durationInMillis = durationInMillis,
                    startTimeInMillis = startTimeInMillis,
                    maxNumberOfParticipants = maxNumberOfParticipants,
                    location = location,
                    hostId,
                    AuthContext.token!!,
                    {onEditEvent()}
                ){error ->
                    errorText = error
                }
            }
            Spacer(modifier = Modifier.width(100.dp))
            PrimaryButton(buttonText = "Cancel") {
                onCancelEditEvent()
            }
        }
    }
}