package com.intersoft.groupup_app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.intersoft.groupup_app.navigation.CreateEventPage
import com.intersoft.groupup_app.navigation.HomePage
import com.intersoft.groupup_app.navigation.LoginPage
import com.intersoft.groupup_app.navigation.RegistrationPage
import com.intersoft.groupup_app.ui.theme.GroupUP_appTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GroupUP_appTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "CreateEvent"){ // TODO: MAKE REGISTRATION DEFAULT SCREEN AGAIN
                        composable("registration"){
                            RegistrationPage(
                                onRegister = {
                                    navController.navigate("login")
                                    Toast.makeText(applicationContext, "account has been registered", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                        composable("login"){
                            LoginPage()
                        }
                        composable("home"){
                            HomePage()
                        }
                        composable("createEvent"){
                            CreateEventPage(
                                onCreateEvent ={
                                    navController.navigate("home")
                                }
                            )
                        }
                    }
                }
            }
        }
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