package com.intersoft.groupup_app.navigation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
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
import com.intersoft.auth.EditUserInfoManager
import com.intersoft.groupup_app.AppContext
import com.intersoft.location.LocationUtils
import com.intersoft.ui.ErrorText
import com.intersoft.ui.PrimaryButton
import com.intersoft.ui.SecondaryButton
import com.intersoft.ui.TextInputField
import com.intersoft.user.UserModel

@Composable
fun EditProfilePage(
    goBackForProfile: () -> Unit
) {

    var username by remember { mutableStateOf("John Smith") }
    var email by remember { mutableStateOf("test123@gmail.com") }
    var location by remember { mutableStateOf("Maia, 23th") }
    var error by remember { mutableStateOf("") }
    var coordinates: Pair<Double?,Double?> = Pair(null, null)

    if(AuthContext.token != null){
        username = AuthContext.username!!
        email = AuthContext.email!!
        location = AuthContext.location!!
        coordinates = LocationUtils.coordinatesFromString(location)
    }

    LazyColumn(modifier = Modifier
        .fillMaxSize())
    {
        item {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    text = "Edit Profile",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .padding(16.dp),
                )
            }
        }
        item {
            EditProfileField(field = UserProfileFields.USERNAME, value = username) {
                username = it
            }
        }
        item {
            EditProfileField(field = UserProfileFields.EMAIL, value = email) {
                email = it
            }
        }
        item {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, start = 32.dp, end = 32.dp)
            )
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween

                ){
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.90f)
                    ){
                        Text(text = "Location")
                        AppContext.LocationService.LocationPicker(
                            {lat,lon -> location = "$lat,$lon"},
                            latitude = coordinates.first!!,
                            longitude = coordinates.second!!,
                            true
                        )
                    }


                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        modifier = Modifier.padding(start = 8.dp, end= 4.dp)
                    )
                }


            }
        }

        item{
            ErrorText(text = error)
        }

        item {
            Row(modifier = Modifier
                .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center)
            {
                PrimaryButton(buttonText = "Apply Changes") {
                    EditUserInfoManager.editUser(
                        UserModel(
                            username,
                            email,
                            "",
                            location
                        ),
                        onEditSuccess = {
                            Log.d("UserInformationPage", "User edited successfully")
                            AuthContext.username = username
                            AuthContext.email = email
                            AuthContext.location = location

                            error = ""

                            goBackForProfile()

                        },
                        onEditFail = {
                            error = it
                        }
                    )
                }
            }

        }

        item {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center)
            {
                SecondaryButton(buttonText = "Cancel") {
                    goBackForProfile()
                }
            }

        }
    }

}

@Composable
fun EditProfileField(
    field: UserProfileFields,
    value: String,
    onValueChange: (String) -> Unit
) {
    val fieldString = when(field){
        UserProfileFields.USERNAME -> "Username"
        UserProfileFields.EMAIL -> "Email"
        UserProfileFields.LOCATION -> "Location"
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 16.dp, start = 32.dp, end = 32.dp)
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.90f)
            ){
                TextInputField(label = fieldString, placeholder = value) {
                    onValueChange(it)
                }
            }


            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit",
                modifier = Modifier.padding(start = 8.dp, end= 4.dp)
            )
        }


    }


}

@Preview
@Composable
fun EditProfilePagePreview() {
    EditProfilePage({})
}