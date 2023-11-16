package com.intersoft.groupup_app.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.intersoft.ui.PrimaryButton

@Composable
fun HomePage(onCreateEventButtonPress: () -> Unit){
    Text("Home page")

    Column (modifier = Modifier
        .fillMaxSize()
        .padding(30.dp)
        .verticalScroll(rememberScrollState())
    ){
        PrimaryButton(
            buttonText = "Create new event",
            Modifier
                .align(Alignment.CenterHorizontally)
        ){
            onCreateEventButtonPress()
        }
    }

}
@Preview
@Composable
fun PreviewHomePage(){
    HomePage {

    }
}