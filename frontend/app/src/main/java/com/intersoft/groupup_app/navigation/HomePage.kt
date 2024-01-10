package com.intersoft.groupup_app.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.intersoft.ui.PrimaryButton

@Composable
fun HomePage(
    onUserInformationPressed: () -> Unit,
    onCreateEventButtonPress: () -> Unit,
    onEventDetailsPressed: () -> Unit,
    onAvailableEventsButtonPressed: () -> Unit) {
    Column (modifier = Modifier
        .fillMaxSize()
        .padding(30.dp)
        .verticalScroll(rememberScrollState())
    ){
        Text("Home page")
        PrimaryButton(
            buttonText = "Create new event",
            Modifier.align(Alignment.CenterHorizontally)
        ){
            onCreateEventButtonPress()
        }
        PrimaryButton("User information",
            Modifier.align(Alignment.CenterHorizontally)){
            onUserInformationPressed()
        }
        PrimaryButton("Event Details test page",
            Modifier.align(Alignment.CenterHorizontally)){
            onEventDetailsPressed()
        }
        PrimaryButton("Available Events test page",
            Modifier.align(Alignment.CenterHorizontally)){
            onAvailableEventsButtonPressed()
        }
    }
}

@Preview
@Composable
fun HomePagePreview() {
    HomePage(
        onUserInformationPressed = {},
        onCreateEventButtonPress = {},
        onEventDetailsPressed = {},
        onAvailableEventsButtonPressed = {})
}