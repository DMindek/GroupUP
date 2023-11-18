package com.intersoft.groupup_app.navigation

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.intersoft.ui.CounterElement
import com.intersoft.ui.DisabledTextField
import com.intersoft.ui.ErrorText
import com.intersoft.ui.GeneralDatePicker
import com.intersoft.ui.GeneralTimePicker
import com.intersoft.ui.LabelText
import com.intersoft.ui.MultiLineTextInputField
import com.intersoft.ui.PrimaryButton
import com.intersoft.ui.TextInputField
import com.intersoft.ui.TitleText

@Composable
fun CreateEventPage(onCreateEvent: () -> Unit, onCancelEventCreation: () -> Unit) {
    var name by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    var duration by remember {
        mutableStateOf("")
    }
    var numberOfParticipants by remember {
        mutableStateOf("")
    }
    var location by remember {
        mutableStateOf("")
    }

    var errorText by remember {
        mutableStateOf("")
    }

    val selectedStartTime by remember {
        mutableStateOf("")
    }

    val selectedEndTime by remember {
        mutableStateOf("")
    }



    val showStartTimePicker = remember {
        mutableStateOf(false)
    }

    val showEndTimePicker = remember {
        mutableStateOf(false)
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TitleText(text = "Create an event")
        TextInputField(label = "Name") { name = it }
        MultiLineTextInputField("Description") {description = it}
        GeneralDatePicker("Event Date")


        Column(modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
        ) {
            LabelText(text = "Duration",)
            Spacer(modifier = Modifier.padding(vertical = 20.dp))

            Column(verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row {
                    LabelText(text = "Start time:")
                    Spacer(modifier = Modifier.padding(end = 15.dp))
                    Column(horizontalAlignment = Alignment.End) {
                        DisabledTextField(selectedStartTime)
                        Spacer(modifier = Modifier.padding(vertical = 5.dp))
                        PrimaryButton(buttonText = "Choose start time") {
                            showStartTimePicker.value = true
                        }

                    }
                }

                Spacer(modifier = Modifier.padding(vertical = 10.dp))


                Row {
                    LabelText(text = "End time:")
                    Spacer(modifier = Modifier.padding(end = 15.dp))
                    Column(horizontalAlignment = Alignment.End) {
                        DisabledTextField(selectedEndTime)
                        Spacer(modifier = Modifier.padding(vertical = 5.dp))
                        PrimaryButton(buttonText = "Choose end time") {
                            showEndTimePicker.value = true
                        }
                    }
                }
            }

            if(showStartTimePicker.value)
            GeneralTimePicker(
                onConfirm = {/*TODO IMPLEMENT TIME FORMATING*/},
                onCancel =  {showStartTimePicker.value = false})

            if(showEndTimePicker.value)
            GeneralTimePicker(
                onConfirm = {/*TODO IMPLEMENT TIME FORMATING*/},
                onCancel =  {showEndTimePicker.value = false})
        }



        CounterElement(label = "Max Participants") {numberOfParticipants = it}
        TextInputField(label = "location") { location = it }
        ErrorText(text = errorText)
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp)
        ) {
            PrimaryButton(buttonText = "Create") {
                // TODO: Implement event creation
                onCreateEvent()
                errorText = ""
            }
            Spacer(modifier = Modifier.width(100.dp))
            PrimaryButton(buttonText = "Cancel") {
                onCancelEventCreation()
            }
        }
    }
}
