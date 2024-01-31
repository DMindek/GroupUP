package com.intersoft.groupup_app.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.intersoft.auth.AuthContext
import com.intersoft.groupup_app.AppContext
import com.intersoft.location.LocationUtils
import com.intersoft.ui.PrimaryButton

enum class UserProfileFields{
    USERNAME, EMAIL, LOCATION
}

@Composable
fun UserProfilePage(onEditPress: () -> Unit){

    var username by remember { mutableStateOf("John Smith") }
    var email by remember { mutableStateOf("test123@gmail.com") }
    var latitude by remember { mutableStateOf<Double?>(null) }
    var longitude by remember { mutableStateOf<Double?>(null) }

    if(AuthContext.token != null){
        username = AuthContext.username!!
        email = AuthContext.email!!
        val coordinates = LocationUtils.coordinatesFromString(AuthContext.location!!)
        if(coordinates != null){
            latitude = coordinates.first
            longitude = coordinates.second
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),

    ){
        item{
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = username,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )

                PrimaryButton(buttonText ="Edit") {
                    onEditPress()
                }
            }
        }
        item {
            UserTextInformation(UserProfileFields.EMAIL, email)
        }
        item {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, start = 32.dp, end = 32.dp)
            ) {
                Text(
                    text = "Location",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                )
                AppContext.getLocationService().LocationDisplay(
                    latitude = latitude,
                    longitude = longitude
                )
            }
        }
    }
}

@Composable
fun UserTextInformation(field : UserProfileFields , value: String) {
    //transform field into string
    val fieldString = when(field){
        UserProfileFields.USERNAME -> "Username"
        UserProfileFields.EMAIL -> "Email"
        UserProfileFields.LOCATION -> "Location"
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 16.dp, start = 32.dp)
    )
    {
        Text(
            text = fieldString,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(bottom = 4.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(bottom = 16.dp)
        )
    }

}

@Preview
@Composable
fun UserProfilePagePreview(){
    UserProfilePage({})
}

