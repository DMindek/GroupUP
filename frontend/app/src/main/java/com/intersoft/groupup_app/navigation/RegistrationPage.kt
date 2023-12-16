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
import com.intersoft.ui.TitleText
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
            .padding(50.dp)
    ){
        TitleText(text = "Register")
        TextInputField(label = "email") { email = it }
        TextInputField(label = "username") {username = it}
        Text(text = "Location")
        AppContext.LocationService.LocationPicker(
            onLocationChanged = {lat,lon -> location = "$lat,$lon" },
            latitude = null,
            longitude = null,
            false
        )
        TextInputField(label = "password", PasswordVisualTransformation()) {password = it}
        TextInputField(label = "confirm password", PasswordVisualTransformation()) {passwordRetype = it}
        ErrorText(text = errorText)
        PrimaryButton(buttonText = "Sign up",
            modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
            RegistrationManager.registerUser(UserModel(username, email, password, location), passwordRetype,
                onRegisterSuccess = onRegister,
                onRegisterFail = {
                    errorText = it
            })
        }
    }
}
