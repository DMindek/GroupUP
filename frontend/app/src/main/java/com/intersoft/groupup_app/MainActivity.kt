package com.intersoft.groupup_app


import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.intersoft.groupup_app.navigation.AvailableEventsPage
import com.intersoft.groupup_app.navigation.CreateEventPage
import com.intersoft.groupup_app.navigation.EditEventPage
import com.intersoft.groupup_app.navigation.EditProfilePage
import com.intersoft.groupup_app.navigation.EventDetailsPage
import com.intersoft.groupup_app.navigation.FriendSearchPage
import com.intersoft.groupup_app.navigation.HomePage
import com.intersoft.groupup_app.navigation.JoinedEventsPage
import com.intersoft.groupup_app.navigation.LoginPage
import com.intersoft.groupup_app.navigation.RegistrationPage
import com.intersoft.groupup_app.navigation.UserCreatedEventsPage
import com.intersoft.groupup_app.navigation.UserProfilePage
import com.intersoft.groupup_app.ui.theme.GroupUP_appTheme
import com.intersoft.ui.NavBar
import com.intersoft.ui.NavBarItem
import com.intersoft.ui.TitleText

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        var tempEventId = 0
        var tempEventName = ""
        var tempDescription = ""
        var tempSelectedDateInMillis: Long = 0
        var tempStartTimeInMillis: Long = 0
        var tempDurationInMillis: Long = 0
        var tempMaxNumberOfParticipants = 0
        var tempLocation = ""
        var tempLocationName = ""
        var tempHostId = 0
        val sharedPrefs = applicationContext.getSharedPreferences("GroupUpAppPreferences", MODE_PRIVATE)

        initLocationModule(sharedPrefs, getString(R.string.location_module_sharedpref))

        super.onCreate(savedInstanceState)
        setContent {
            GroupUP_appTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val navController = rememberNavController()


                    val navBarItems = listOf(
                        NavBarItem("userCreatedEvents", Icons.Filled.DateRange),
                        NavBarItem("joinedEvents", Icons.Filled.Place),
                        NavBarItem("home", Icons.Filled.Home),
                        NavBarItem("searchForFriend", Icons.Filled.Person),
                        NavBarItem("user_information", Icons.Filled.Edit),
                    )

                    var showNavbar by remember {
                        mutableStateOf(false)
                    }

                    Column {

                        NavHost(
                            navController = navController,
                            startDestination = "login",
                        ) {
                            composable("registration") {
                                RegistrationPage(
                                    onRegister = {
                                        navController.navigate("login")
                                        Toast.makeText(
                                            applicationContext,
                                            "account has been registered",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                )
                            }
                            composable("login") {
                                LoginPage(
                                    context = LocalContext.current,
                                    onLogin = {
                                        navController.navigate("home")
                                        showNavbar = true
                                    },
                                    onRegisterClick = { navController.navigate("registration") }
                                )
                            }

                            composable("home") {
                                AvailableEventsPage(
                                    onEventClick = { navController.navigate("eventDetail/$it") }
                                )
                            }
                            composable("createEvent") {
                                CreateEventPage(
                                    onCreateEvent = {
                                        navController.navigate("userCreatedEvents")
                                    },
                                    onCancelEventCreation = {
                                        navController.navigate("goBack")
                                    }
                                )
                            }
                            composable("userCreatedEvents") {
                                UserCreatedEventsPage(
                                    onEventClick = { navController.navigate("eventDetail/$it") },
                                    onCreateEventButtonPress = { navController.navigate("createEvent") }
                                )
                            }
                            composable("joinedEvents") {
                                JoinedEventsPage(
                                    onEventClick = { navController.navigate("eventDetail/$it") }
                                )
                            }
                            composable("user_information") {
                                showNavbar = true
                                UserProfilePage (
                                  onEditPress = { navController.navigate("edit_profile") },
                                  onLocationModuleChanged = {
                                      val editor = sharedPrefs.edit()
                                      editor.putString(getString(R.string.location_module_sharedpref), AppContext.getLocationService().getName())
                                      editor.apply()
                                 }
                              )
                            }

                        composable("edit_profile") {
                            EditProfilePage(goBackForProfile = { navController.navigate("user_information") })
                        }
                        composable("editEvent"){
                            EditEventPage(
                                eventId = tempEventId,
                                eventName = tempEventName,
                                description = tempDescription,
                                selectedDateInMillis = tempSelectedDateInMillis,
                                startTimeInMillis = tempStartTimeInMillis,
                                durationInMillis = tempDurationInMillis,
                                maxNumberOfParticipants = tempMaxNumberOfParticipants,
                                location = tempLocation,
                                locationName = tempLocationName,
                                 tempHostId,
                                onEditEvent = { navController.navigate("eventDetail/$tempEventId") },
                                onCancelEditEvent = {navController.navigate("eventDetail/$tempEventId")}
                            )
                        }
                        composable(
                            "eventDetail/{eventId}",
                            arguments = listOf(navArgument("eventId") {
                                type = NavType.IntType
                            })
                        ) { backStackEntry ->
                            EventDetailsPage(
                                onGetEventFail = {
                                    Toast.makeText(applicationContext, "Event not found", Toast.LENGTH_SHORT).show()
                                    navController.navigate("home")
                                },
                                eventId = backStackEntry.arguments?.getInt("eventId") ?: 0,
                                onEditEventButtonPressed = {eventId,
                                    eventName,
                                    description,
                                    selectedDateInMillis,
                                    startTimeInMillis,
                                    durationInMillis,
                                    maxNumberOfParticipants,
                                    location,
                                    locationName,
                                    hostId ->

                                    tempEventId = eventId
                                    tempEventName = eventName
                                    tempDescription = description
                                    tempSelectedDateInMillis = selectedDateInMillis
                                    tempStartTimeInMillis = startTimeInMillis
                                    tempDurationInMillis = durationInMillis
                                    tempMaxNumberOfParticipants = maxNumberOfParticipants
                                    tempLocation = location
                                    tempLocationName = locationName
                                    tempHostId = hostId

                                    navController.navigate("editEvent")
                                },
                                onEventDeleted = {
                                    val destination = navController.previousBackStackEntry!!.destination.route!!
                                    Log.d("Navigation control", "destination is $destination")
                                    navController.navigate("goBack")
                                    navController.navigate("goBack")
                                    navController.navigate(destination)
                                }
                               )
                          }

                     
                            composable("goBack") {
                                navController.popBackStack()
                            }
                            composable("searchForFriend") {
                                FriendSearchPage()
                            }

                        }

                        Spacer(modifier = Modifier.weight(1f))

                        if (showNavbar) {
                            NavBar(navController = navController, navBarItems = navBarItems)
                        }
                    }
                }
            }
        }
    }

}

fun initLocationModule(sharedPrefs: SharedPreferences, saveLocation: String){
    val locationModule = sharedPrefs.getString(saveLocation, "none")
    if(locationModule == null || locationModule == "none"){
        val edit = sharedPrefs.edit()
        edit.putString(saveLocation, AppContext.getLocationService().getName())
        edit.apply()
    }
    else{
        AppContext.setLocationService(locationModule)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GroupUP_appTheme {
        Greeting("Android")

    }
}