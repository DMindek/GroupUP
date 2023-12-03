package com.intersoft.groupup_app

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.intersoft.groupup_app.navigation.LoginPage
import com.intersoft.groupup_app.navigation.RegistrationPage
import com.intersoft.groupup_app.ui.theme.GroupUP_appTheme
import com.intersoft.ui.NavBar
import com.intersoft.ui.NavBarItem

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
                        NavBarItem("registration", Icons.Filled.Place),
                        NavBarItem("login", Icons.Filled.DateRange),
                        NavBarItem("main", Icons.Filled.Person),
                        NavBarItem("settings", Icons.Filled.Settings),
                    )

                    Scaffold (topBar = {NavBar(navController = navController, navBarItems = navBarItems)}){
                        NavHost(navController = navController, startDestination = "login") {
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
                                    onLogin = { navController.navigate("main") },
                                    onRegisterClick = { navController.navigate("registration") }
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