package com.intersoft.groupup_app

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.intersoft.groupup_app.navigation.LoginPage
import com.intersoft.groupup_app.navigation.RegistrationPage
import com.intersoft.groupup_app.ui.theme.GroupUP_appTheme
import com.intersoft.ui.NavBar
import com.intersoft.ui.NavBarItem
import com.intersoft.ui.TitleText

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
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
                        NavBarItem("login", Icons.Filled.DateRange),
                        NavBarItem("registration", Icons.Filled.Place),
                        NavBarItem("main", Icons.Filled.Person),
                        NavBarItem("settings", Icons.Filled.Settings),
                    )

                    var currentTitle by remember {
                        mutableStateOf("")
                    }

                    var showNavbar by remember {
                        mutableStateOf(false)
                    }
                    val topPadding = if(showNavbar) 50.dp
                    else 0.dp

                    Column {
                        TitleText(
                            text = currentTitle,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp, start = 30.dp, end = 30.dp, bottom = 0.dp)
                                .height(55.dp)
                        )
                        if (showNavbar) {
                            NavBar(navController = navController, navBarItems = navBarItems)
                        }

                        NavHost(
                            navController = navController,
                            startDestination = "login",
                            modifier = Modifier.padding(top = topPadding)
                        ) {
                            composable("registration") {
                                currentTitle = "Register"
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
                                currentTitle = "Welcome"
                                LoginPage(
                                    context = LocalContext.current,
                                    onLogin = {
                                        navController.navigate("main")
                                        showNavbar = true
                                    },
                                    onRegisterClick = {
                                        navController.navigate("registration")
                                        showNavbar = true
                                    }
                                )
                            }
                            composable("main") {
                            }
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