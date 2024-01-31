package com.intersoft.groupup_app.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.intersoft.ui.HomePagePrimaryButton
import com.intersoft.ui.PrimaryButton
import com.intersoft.ui.TitleText

@Composable
fun HomePage(
    onUserInformationPressed: () -> Unit,
    onCreateEventButtonPress: () -> Unit,
    onAvailableEventsButtonPressed: () -> Unit,
    onSearchButtonPressed: () -> Unit,
    onJoinedEventsButtonPressed: () -> Unit
    ) {

    Column (modifier = Modifier
        .fillMaxSize()
        .padding(30.dp)
        .verticalScroll(rememberScrollState())
    ){
        TitleText("Home page")
        HomePagePrimaryButton(
            buttonText = "Create new event",
            Modifier.align(Alignment.CenterHorizontally).fillMaxWidth().padding(20.dp).height(90.dp),
            25
        ){
            onCreateEventButtonPress()
        }
        HomePagePrimaryButton("User information",
            Modifier.align(Alignment.CenterHorizontally).fillMaxWidth().padding(20.dp).height(90.dp),
            25
        ){
            onUserInformationPressed()
        }
        HomePagePrimaryButton("Available Events",
            Modifier.align(Alignment.CenterHorizontally).fillMaxWidth().padding(20.dp).height(90.dp),
            25
        ){
            onAvailableEventsButtonPressed()
        }
        HomePagePrimaryButton("Joined events",
            Modifier.align(Alignment.CenterHorizontally).fillMaxWidth().padding(20.dp).height(90.dp),
            25
        ){
            onJoinedEventsButtonPressed()
        }
        HomePagePrimaryButton("Add new friend",
            Modifier.align(Alignment.CenterHorizontally).fillMaxWidth().padding(20.dp).height(90.dp),
            25
        ){
            onSearchButtonPressed()
        }

    }
}

@Preview
@Composable
fun HomePagePreview() {
    HomePage(
        onUserInformationPressed = {},
        onCreateEventButtonPress = {},
        onAvailableEventsButtonPressed = {},
        onSearchButtonPressed = {},
        onJoinedEventsButtonPressed = {}
    )
}