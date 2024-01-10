package com.intersoft.groupup_app.navigation

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.intersoft.auth.AuthContext
import com.intersoft.event.EventManager
import com.intersoft.ui.DisabledTextField
import com.intersoft.ui.LabelText
import com.intersoft.ui.ParticipantNumberDisplayField
import com.intersoft.ui.PrimaryButton
import com.intersoft.ui.TitleText
import com.intersoft.utils.DateTimeManager

@Composable
fun EventDetailsPage(onGetEventFail: () -> Unit, eventId: Int){
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
        mutableStateOf(10)
    }

    var currentNumberOfParticipants = 0

    var location by remember {
        mutableStateOf("")
    }

    var host by remember {
        mutableStateOf("")
    }
    var eventDataWasRecieved by remember{
        mutableStateOf(false)
    }

     EventManager.getEvent(eventId ,{onGetEventFail()}){

         eventName = it.name
         description = it.description
         eventDate = DateTimeManager.formatMillisToDateTime(it.date.toInstant().toEpochMilli())
         eventDuration = it.duration
         maxNumberOfParticipants = it.max_participants
         val participantsNumber = it.participants?.count()
         if(participantsNumber != null)
            currentNumberOfParticipants = participantsNumber
         location = it.location
         EventManager.getHostname(
             it.owner_id,
             AuthContext.token!!,
             onGetHostnameError = {},
             onGetHostnameSuccess = {hostname -> host = hostname }
         )

         eventDataWasRecieved = true
    }

    if(eventDataWasRecieved){
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                PrimaryButton(buttonText = "Request to Join ") {}
            }
        }
    } else{
        Text(text = "Something went wrong and no event data was recieved. Try to create an event first before viewing details.")
    }

}