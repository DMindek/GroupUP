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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.intersoft.event.EventManager
import com.intersoft.ui.DisabledTextField
import com.intersoft.ui.LabelText
import com.intersoft.ui.ParticipantNumberDisplayField
import com.intersoft.ui.PrimaryButton
import com.intersoft.ui.TitleText
import java.sql.Timestamp

@Composable
fun EventDetailsPage(onGetEventFail: () -> Unit){
    var eventName by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("description")
    }

    var eventDate by remember {
        mutableStateOf(Timestamp(0))
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

    var host = "hostname"
    var eventDataWasRecieved by remember{
        mutableStateOf(false)
    }

     EventManager.getEvent(7,{onGetEventFail()}){

         eventName = it.name
         description = it.description
         eventDate = it.date
         eventDuration = it.duration
         maxNumberOfParticipants = it.max_participants
         val participantsNumber = it.participants?.count()
         if(participantsNumber != null)
            currentNumberOfParticipants = participantsNumber
         location = it.location
         host = it.owner_id.toString()

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
                    .fillMaxWidth()
                    .padding(start = 20.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                PrimaryButton(buttonText = "Request to Join ") {}
            }
        }
    }

}