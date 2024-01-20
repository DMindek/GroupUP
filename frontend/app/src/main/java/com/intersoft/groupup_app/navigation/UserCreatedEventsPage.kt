package com.intersoft.groupup_app.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.intersoft.auth.AuthContext
import com.intersoft.event.EventsViewModel
import com.intersoft.ui.ErrorText
import com.intersoft.ui.IIterableObject
import com.intersoft.ui.LoadingScreen
import com.intersoft.ui.ObjectCard
import com.intersoft.ui.PrimaryButton
import com.intersoft.ui.TitleText


@SuppressLint("UnrememberedMutableState")
@Composable
fun UserCreatedEventsPage(
    onEventClick: (Int) -> Unit,
    onCreateEventButtonPress: () -> Unit,
    viewModel: EventsViewModel = viewModel()
) {
    val events by viewModel.events.observeAsState()
    val error by viewModel.error.observeAsState()

    LaunchedEffect(
        key1 = events,
        block = {
            if (events == null) {
                viewModel.fetchUserCurrentEvents(AuthContext.id!!, AuthContext.token!!)
            }
        }
    )

    Log.d("Events", events.toString())


    if( events == null ) LoadingScreen()
    else{
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(color = Color.White)
                .verticalScroll(rememberScrollState())
                .fillMaxHeight(),
            content = {

                TitleText(text = "Your Events")

                // Check if data is loaded before displaying DataScreen

                DataScreen(
                    data = events!!,
                    onEventClick = onEventClick
                )

                when {
                    events?.isEmpty() == true && error == "NO_EVENTS" -> Text(text = "You have no events")
                    events?.isEmpty() == true && error != "" -> ErrorText(text = error!!)
                }

                // Display loading screen, no events message, or error message based on state


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    PrimaryButton(buttonText = "Create Event") {
                        onCreateEventButtonPress()
                    }
                }
            }
        )

    }


}


@Composable
fun DataScreen(data: List<IIterableObject>, onEventClick: (Int) -> Unit) {
    Column(content = {

        for(event in data) {
                EventCard(
                    event = event,
                    onEventClick = onEventClick
                )
        }
    })
}

@Composable
fun EventCard(event: IIterableObject, onEventClick: (Int) -> Unit) {
    ObjectCard(data = event, interaction = {
        onEventClick(event.getIdentifier())
    })
}


