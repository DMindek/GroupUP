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
import com.intersoft.ui.CounterElement
import com.intersoft.ui.DurationSelectionElement
import com.intersoft.ui.ErrorText
import com.intersoft.ui.GeneralDatePicker
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TitleText(text = "Create an event")
        TextInputField(label = "Name") { name = it }
        MultiLineTextInputField("Description") {description = it}
        GeneralDatePicker("Event Date"){duration = it}
        DurationSelectionElement(){duration = it}
        CounterElement(label = "Max Participants") {maxNumberOfParticipants = it}
        TextInputField(label = "location") { location = it }
        Spacer(modifier = Modifier.height(20.dp))
        ErrorText(text = errorText)

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
