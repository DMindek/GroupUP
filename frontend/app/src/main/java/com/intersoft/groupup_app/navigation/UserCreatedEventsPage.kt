package com.intersoft.groupup_app.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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


@Composable
fun UserCreatedEventsPage(
    onEventClick: (Int) -> Unit,
    onCreateEventButtonPress: () -> Unit,
    viewModel: EventsViewModel = EventsViewModel()
) {

    val events by viewModel.events.observeAsState()
    val error by viewModel.error.observeAsState()


    if (AuthContext.id != null) {
        viewModel.fetchUserCurrentEvents(AuthContext.id!!)
    }

    Column(
        modifier= Modifier
            .padding(16.dp)
            .background(color = Color.White)
            .verticalScroll(rememberScrollState()),
        content = {

            TitleText(text = "Your Events")

            if (events?.isEmpty() == true && error == "") {
                LoadingScreen()
            } else if (events?.isEmpty() == true && error == "NO_EVENTS") {
                Text(text = "You have no events")
            } else if (events?.isEmpty() == true && error != "") {
                ErrorText(text = error!!)
            } else {
                DataScreen(
                    data = events!!,
                    onEventClick = onEventClick
                )
            }

            PrimaryButton(buttonText = "Create Event") {
                onCreateEventButtonPress()
            }


    })
}


@Composable
fun DataScreen(data: List<IIterableObject>, onEventClick: (Int) -> Unit) {
    LazyColumn(content = {
        items(data.size) { index ->
            Box(content = {
                EventCard(
                    event = data[index],
                    onEventClick = onEventClick
                )
            })
        }
    })
}

@Composable
fun EventCard(event: IIterableObject, onEventClick: (Int) -> Unit) {
    ObjectCard(data = event, interaction = {
        onEventClick(event.getId())
    })
}


