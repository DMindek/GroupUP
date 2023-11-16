package com.intersoft.groupup_app.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.intersoft.groupup_app.R
import com.intersoft.ui.PrimaryButton
import com.intersoft.ui.SecondaryButton
import com.intersoft.ui.TextInputField
import com.intersoft.ui.TitleText

@Composable
fun LoginPage(onLogin: () -> Unit, onRegisterClick: () -> Unit){
    var username by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 50.dp)
    ) {
        TitleText(text = "Welcome to GroupUP")
        Image(
            painter = painterResource(id = R.drawable.groupup_icon),
            contentDescription = "app logo",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        TextInputField(label = "username") { username = it }
        TextInputField(label = "password") { password = it }
        PrimaryButton(buttonText = "Sign in",
            modifier = Modifier
                .width(200.dp)
                .align(Alignment.CenterHorizontally)
        ) {

        }
        SecondaryButton (buttonText = "Sign up", modifier = Modifier.width(150.dp)) {
            onRegisterClick()
        }
    }
}