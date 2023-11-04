package com.intersoft.groupup_app.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.intersoft.ui.PrimaryButton
import com.intersoft.ui.TextInputField
import com.intersoft.ui.TitleText

@Composable
fun RegistrationPage(onRegister: () -> Unit){
    var email by remember {
        mutableStateOf("")
    }
    var username by remember {
        mutableStateOf("")
    }
    var location by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var passwordConfirm by remember {
        mutableStateOf("")
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
    ){
        TitleText(text = "Register")
        TextInputField(label = "email")
        TextInputField(label = "username")
        TextInputField(label = "location")
        TextInputField(label = "password")
        TextInputField(label = "confirm password")
        PrimaryButton(buttonText = "Sign up", modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 40.dp)) {

        }
    }
}

@Preview
@Composable
fun RegistrationPagePreview(){
    RegistrationPage{}
}