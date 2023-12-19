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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.intersoft.auth.AuthContext
import com.intersoft.event.EventCreationManager
import com.intersoft.ui.DisabledTextField
import com.intersoft.ui.LabelText
import com.intersoft.ui.ParticipantNumberDisplayField
import com.intersoft.ui.PrimaryButton
import com.intersoft.ui.TitleText

@Composable
fun EventDetailsPage(onExitEventDetails: () -> Unit){
    val eventName by remember {
        mutableStateOf("Event name")
    }
    val description by remember {
        mutableStateOf("description")
    }

    val eventDate by remember{
        mutableLongStateOf(0)
    }

    val eventStartTime by remember {
        mutableLongStateOf(0)
    }

    val eventDuration by remember {
        mutableLongStateOf(0)
    }
    val maxNumberOfParticipants by remember {
        mutableStateOf(10)
    }

    val currentNumberOfParticipants = 0

    val location by remember {
        mutableStateOf("")
    }

    val host = "hostname"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TitleText(text = eventName)
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            LabelText(text = "Location:")
            Spacer(modifier = Modifier.width(45.dp))
            DisabledTextField(textvalue = location)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row{
            LabelText(text = "Event Date:")
            Spacer(modifier = Modifier.width(30.dp))
            DisabledTextField(textvalue = eventDate.toString())
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


        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp)
        ) {
            PrimaryButton(buttonText = "Create") {
                EventCreationManager.createEvent(
                    eventName = eventName,
                    description = description,
                    selectedDateInMillis = eventDate,
                    durationInMillis = eventDuration,
                    startTimeInMillis = eventStartTime,
                    maxNumberOfParticipants = maxNumberOfParticipants,
                    location = location,AuthContext.id!!,{}
                ){
                }
            }
            Spacer(modifier = Modifier.width(100.dp))
            PrimaryButton(buttonText = "Cancel") {
                onExitEventDetails()
            }
        }
    }
}