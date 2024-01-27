package com.intersoft.groupup_app.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.intersoft.auth.AuthContext
import com.intersoft.groupup_app.AppContext
import com.intersoft.location.LocationUtils
import com.intersoft.ui.PrimaryButton

enum class UserProfileFields{
    USERNAME, EMAIL, LOCATION, LOCATION_NAME
}

@Composable
fun UserProfilePage(onEditPress: () -> Unit){

    var username by remember { mutableStateOf("John Smith") }
    var email by remember { mutableStateOf("test123@gmail.com") }
    var latitude by remember { mutableStateOf<Double?>(10.0) }
    var longitude by remember { mutableStateOf<Double?>(10.0) }
    var locationName by remember { mutableStateOf("J street 10") }
    var selectedModuleName by remember { mutableStateOf(AppContext.getLocationService().getName())}
    var selectedModule by remember { mutableStateOf(AppContext.getLocationService())}

    if(AuthContext.token != null){
        username = AuthContext.username!!
        email = AuthContext.email!!
        val coordinates = LocationUtils.coordinatesFromString(AuthContext.location!!)
        if(coordinates != null){
            latitude = coordinates.first
            longitude = coordinates.second
        }
        locationName = AuthContext.location_name!!
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
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
                selectedModule.LocationDisplay(
                    latitude = latitude,
                    longitude = longitude
                )
            }
        }
        item {
            UserTextInformation(field = UserProfileFields.LOCATION_NAME, value = locationName)
        }
        item{
            Text(
                text = "Location",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 4.dp, start = 32.dp, end = 32.dp)
            )
            Column(Modifier.padding(top = 20.dp)) {
                AppContext.getLocationServicesNames().forEach{module->
                    Surface(
                        onClick = {
                            AppContext.setLocationService(module)
                            selectedModuleName = module
                            selectedModule = AppContext.getLocationService()
                        },
                        modifier = Modifier.fillMaxWidth()
                            .padding(start = 32.dp, end = 32.dp)
                            .border(width = 2.dp, color = Color.Blue, shape = RoundedCornerShape(6.dp))
                            .background(
                                if(module == selectedModuleName) Color.LightGray
                                else Color.Transparent
                            )
                        )
                    {
                        Text(
                            text = module,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.background(
                                if(module == selectedModuleName) Color.LightGray
                                else Color.Transparent
                            )
                        )
                    }
                }
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
        UserProfileFields.LOCATION_NAME -> "Location name"
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

