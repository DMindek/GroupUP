package com.intersoft.groupup_app.navigation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.intersoft.auth.AuthContext
import com.intersoft.event.EventManager

import com.intersoft.event.EventsViewModel

import com.intersoft.groupup_app.AppContext

import com.intersoft.ui.ConfirmationDialog
import com.intersoft.ui.DisabledTextField
import com.intersoft.ui.ErrorText
import com.intersoft.ui.LabelText
import com.intersoft.ui.LoadingScreen
import com.intersoft.ui.ParticipantNumberDisplayField
import com.intersoft.ui.PrimaryButton
import com.intersoft.ui.TitleText
import com.intersoft.utils.DateTimeManager

@Composable
fun EventDetailsPage(
    onGetEventFail: () -> Unit,
    eventId: Int,
    onEditEventButtonPressed: (
        eventId: Int,
        eventName: String,
        description: String,
        selectedDateInMillis: Long,
        startTimeInMillis: Long,
        durationInMillis: Long,
        maxNumberOfParticipants: Int,
        location: String,
        locationName: String,
        hostId: Int,) -> Unit,
    onEventDeleted: () -> Unit,
    viewModel: EventsViewModel = viewModel()
){
    var eventName by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("description")
    }

    var eventDate by remember {
        mutableStateOf("")
    }

    var eventDuration by remember {
        mutableIntStateOf(0)
    }
    var maxNumberOfParticipants by remember {
        mutableIntStateOf(0)
    }

    var isParticipant by remember {
        mutableStateOf(false)
    }

    var currentNumberOfParticipants by remember {
        mutableIntStateOf(0)
    }

    var location by remember {
        mutableStateOf("")
    }

    var locationName by remember {
        mutableStateOf("")
    }

    var host by remember {
        mutableStateOf("")
    }

    var hostId by remember{
        mutableIntStateOf(-1)
    }

    var eventDataWasRecieved by remember{
        mutableStateOf(false)
    }

    var isShowingLeaveConfirmationDialog by remember{
        mutableStateOf(false)
    }

    var isShowingDeleteDialog by remember{
        mutableStateOf(false)
    }

    var selectedDateInMillis : Long by remember{
        mutableLongStateOf(0)
    }
    var startTimeInMillis : Long by remember{
        mutableLongStateOf(0)
    }
    var durationInMillis : Long by remember{
        mutableLongStateOf(0)
    }
    var errorText by remember{
        mutableStateOf("")
    }
    var isDeleted by remember{
        mutableStateOf(false)
    }

    val canJoin by viewModel.canJoin.observeAsState()

    if(canJoin == true ){
        isParticipant = true
    }

    if(!isDeleted) {
        EventManager.getEvent(eventId, { onGetEventFail() }) {

            val totalMillis = it.date.toInstant()
                .toEpochMilli() - 3600000 // subtract 1 hour because the epoch functions ads 1 hour to account for time offset, which does not make sense here
            eventName = it.name
            description = it.description
            eventDate = DateTimeManager.formatMillisToDateTime(totalMillis)
            selectedDateInMillis = totalMillis
            durationInMillis = DateTimeManager.calculateMillisFromMinutes(eventDuration)
            startTimeInMillis = DateTimeManager.calculateStartTimeFromString(
                DateTimeManager.formatMillisToDateTime(totalMillis)
            )
            eventDuration = it.duration
            maxNumberOfParticipants = it.max_participants
            hostId = it.owner_id

            val participantsNumber = it.participants?.count()
            Log.d("Participants", participantsNumber.toString())

            if (participantsNumber != null)
                currentNumberOfParticipants = participantsNumber
            location = it.location
            locationName = it.locationName ?: ""
            EventManager.getHostname(
                it.owner_id,
                AuthContext.token!!,
                onGetHostnameError = {},
                onGetHostnameSuccess = { hostname -> host = hostname }
            )

            isParticipant =
                it.participants!!.any { user -> user.username == AuthContext.username } &&
                        it.owner_id != AuthContext.id

            eventDataWasRecieved = true
        }
    }

    if(eventDataWasRecieved){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
                .verticalScroll(rememberScrollState())
        ) {
            TitleText(text = eventName)
            Spacer(modifier = Modifier.height(25.dp))

            if(location.contains(",".toRegex())){
                val coordinates = location.split(',')
                LabelText(text = "Location:")
                Spacer(modifier = Modifier.height(20.dp))
                AppContext.getLocationService().LocationDisplay(coordinates[0].toDouble(), coordinates[1].toDouble())
            }

            Spacer(modifier = Modifier.height(20.dp))
            Row{
                LabelText(text = "Location name:")
                Spacer(modifier = Modifier.width(30.dp))
                DisabledTextField(textvalue = locationName)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row{
                LabelText(text = "Event start:")
                Spacer(modifier = Modifier.width(30.dp))
                DisabledTextField(textvalue = eventDate)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row{
                LabelText(text = "Event duration:")
                Spacer(modifier = Modifier.width(30.dp))
                DisabledTextField(textvalue = "$eventDuration minutes")
            }
            Spacer(modifier = Modifier.height(20.dp))
            LabelText(text = "Description: ")
            DisabledTextField(textvalue = description, isMultiline = true)
            Spacer(modifier = Modifier.height(20.dp))
            ParticipantNumberDisplayField("Participants joined: ",currentNumber = currentNumberOfParticipants, maxNumber = maxNumberOfParticipants)
            Spacer(modifier = Modifier.height(20.dp))
            LabelText(text = "Host: ")
            DisabledTextField(textvalue = host)
            Spacer(modifier = Modifier.height(20.dp))

            ErrorText(text = errorText)
            Spacer(modifier = Modifier.height(20.dp))

            when{
                hostId != AuthContext.id &&
                currentNumberOfParticipants < maxNumberOfParticipants &&
                !isParticipant -> EventDetailsButton(buttonName = "Join Event") {
                    viewModel.joinEvent(eventId, AuthContext.id!!, AuthContext.token!!)

                }

                hostId != AuthContext.id &&
                        isParticipant-> EventDetailsButton(buttonName = "Leave Event") {
                    isShowingLeaveConfirmationDialog = true
                }

                hostId == AuthContext.id -> {
                    EventDetailsButton(buttonName = "Delete Event") {
                        isShowingDeleteDialog = true
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    EventDetailsButton(buttonName = "Edit Event") {
                        onEditEventButtonPressed(
                            eventId,
                            eventName,
                            description,
                            selectedDateInMillis,
                            startTimeInMillis,
                            durationInMillis,
                            maxNumberOfParticipants,
                            location,
                            locationName,
                            hostId
                        )

                    }
                }
            }

        }
    } else{
        LoadingScreen()
    }

    if(isShowingLeaveConfirmationDialog){
        ConfirmationDialog(
            title = "Leave Event",
            dialogText = "Are you sure you want to leave this event?",
            onConfirmButton = {
                viewModel.leaveEvent(eventId, AuthContext.id!!, AuthContext.token!!)
                isShowingLeaveConfirmationDialog = false
            },
            onDismissButton = {
                isShowingLeaveConfirmationDialog = false
            }

        )
    }

    if(isShowingDeleteDialog){
        ConfirmationDialog(
            title = "Deleting event",
            dialogText = "Are you sure you want to delete the event \"${eventName}\"?",
            onConfirmButton = {
                isShowingDeleteDialog = false
                EventManager.deleteEvent(
                    eventId,
                    {
                        isDeleted = true
                        onEventDeleted()
                    },
                    { errorText = it})
            },
            onDismissButton = {
                isShowingDeleteDialog = false
            },
            ButtonDefaults.buttonColors(
                containerColor = colorResource(com.intersoft.ui.R.color.inputField),
                contentColor = colorResource(com.intersoft.ui.R.color.errorColor),
                disabledContainerColor = colorResource(com.intersoft.ui.R.color.secondary),
                disabledContentColor = colorResource(com.intersoft.ui.R.color.secondaryText)
            )
        )
    }
}


@Composable
fun EventDetailsButton(
    buttonName: String,
    onButtonPress: () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        PrimaryButton(buttonText = buttonName) {onButtonPress()}
    }
}



