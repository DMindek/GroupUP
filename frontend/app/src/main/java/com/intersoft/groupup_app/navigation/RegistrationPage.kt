package com.intersoft.groupup_app.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.intersoft.auth.RegistrationManager
import com.intersoft.groupup_app.AppContext
import com.intersoft.ui.ErrorText
import com.intersoft.ui.PrimaryButton
import com.intersoft.ui.TextInputField
import com.intersoft.user.UserModel


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
    var locationName by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var passwordRetype by remember {
        mutableStateOf("")
    }
    var errorText by remember {
        mutableStateOf("")
    }

    Column (
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 50.dp, end = 50.dp, top = 0.dp, bottom = 50.dp)
    ){
        TextInputField(label = "email") { email = it }
        TextInputField(label = "username") {username = it}
        Text(text = "Location")
        AppContext.getLocationService().LocationPicker(
            onLocationChanged = {lat,lon -> location = "$lat,$lon" },
            latitude = null,
            longitude = null,
            false
        )
        TextInputField(label = "location name") {locationName = it}
        TextInputField(label = "password", PasswordVisualTransformation()) {password = it}
        TextInputField(label = "confirm password", PasswordVisualTransformation()) {passwordRetype = it}
        ErrorText(text = errorText)
        PrimaryButton(buttonText = "Sign up",
            modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
            RegistrationManager.registerUser(UserModel(username, email, password, location, locationName), passwordRetype,
                onRegisterSuccess = onRegister,
                onRegisterFail = {
                    errorText = it
            })
        }
    }
}
