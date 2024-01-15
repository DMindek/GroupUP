package com.intersoft.groupup_app.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.intersoft.auth.AuthContext
import com.intersoft.event.EventsViewModel
import com.intersoft.ui.ErrorText
import com.intersoft.ui.LoadingScreen
import com.intersoft.ui.TitleText


@SuppressLint("UnrememberedMutableState")
@Composable
fun AvailableEventsPage(
    onEventClick: (Int) -> Unit,
    viewModel: EventsViewModel = viewModel()
) {
    val events by viewModel.events.observeAsState()
    val error by viewModel.error.observeAsState()

    val PAGE_TITLE = "All Events"

    LaunchedEffect(
        key1 = events,
        block = {
            if (events == null) {
                viewModel.fetchAvailableEvents(AuthContext.token!!)
            }
}
    )


    if( events == null ) LoadingScreen()
    else{
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(color = Color.White)
                .verticalScroll(rememberScrollState())
                .fillMaxHeight(),
            content = {

                TitleText(text = PAGE_TITLE)

                // Check if data is loaded before displaying DataScreen

                DataScreen(
                    data = events!!,
                    onEventClick = onEventClick
                )

                when {
                    events?.isEmpty() == true && error == "NO_EVENTS" -> Text(text = "There are no available events")
                    events?.isEmpty() == true && error != "" -> ErrorText(text = error!!)
                }

                // Display loading screen, no events message, or error message based on state

            }
        )

    }


}


