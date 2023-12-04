package com.intersoft.groupup_app.navigation

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomePage(onUserInformationPressed: () -> Unit) {
    Text("Home page")
    Button(onClick = {
        onUserInformationPressed()
    }) {
        Text("User information")
    }
}

@Preview
@Composable
fun HomePagePreview() {
    HomePage(onUserInformationPressed = {})
}