package com.intersoft.groupup_app.navigation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.intersoft.auth.AuthContext
import com.intersoft.auth.EditUserInfoManager
import com.intersoft.groupup_app.R
import com.intersoft.ui.ErrorText
import com.intersoft.ui.IconInformationText
import com.intersoft.ui.PrimaryButton
import com.intersoft.ui.TextInputField
import com.intersoft.user.UserModel

data class UserData(
    var userName: String,
    var userEmail: String,
    var userLocation: String,
    var userImage: Int
)
// Depreciated code ----------------------------------

@Composable
fun UserInformationPage(){

    val userData = UserData(
        userName = "John Doe",
        userEmail = "johnDoe@gmail.com",
        userLocation = "New York",
        userImage = R.drawable.icon
    )

    if(AuthContext.token == null) return
    else{
        userData.userName = AuthContext.username!!
        userData.userEmail = AuthContext.email!!
        userData.userLocation = AuthContext.location!!

    }

    var isEditDialogVisible by remember { mutableStateOf(false) }
    var editedUserName by remember { mutableStateOf(userData.userName) }
    var editedUserEmail by remember { mutableStateOf(userData.userEmail) }
    var editedUserLocation by remember { mutableStateOf(userData.userLocation) }
    var error by remember { mutableStateOf("") }


    val onEditButtonPressed = {
        isEditDialogVisible = true
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
    ) {
        item {
            UserHeader(editedUserName, userData.userImage)
        }
        item {
            UserEmailRow(editedUserEmail)
        }
        item {
            UserLocationRow(editedUserLocation)
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                PrimaryButton(buttonText = "Edit", modifier = Modifier
                    .padding(top = 40.dp)){
                    onEditButtonPressed()

                }
            }

        }
    }

    if(isEditDialogVisible){
        EditUserDialog(
            userName = editedUserName,
            userEmail = editedUserEmail,
            userLocation = editedUserLocation,
            error = error,
            showDialog = isEditDialogVisible,
            onDismiss = {
                error = ""
                isEditDialogVisible = false
            },
            onSave = {name, email, location ->
                EditUserInfoManager.editUser(
                    UserModel(
                        name,
                        email,
                        "",
                        location
                    ),
                    onEditSuccess = {
                        Log.d("UserInformationPage", "User edited successfully")
                        AuthContext.username = name
                        AuthContext.email = email
                        AuthContext.location = location
                        editedUserName = name
                        editedUserEmail = email
                        editedUserLocation = location

                        error = ""

                        isEditDialogVisible = false
                    },
                    onEditFail = {
                        error = it
                        isEditDialogVisible = true
                    }
                )

            }

        )
    }

}

@Composable
fun UserHeader(userName: String, userImage: Int){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = userImage),
            contentDescription = "User image",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )

        Text(text = userName,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(top = 8.dp)
        )
    }

}

@Composable
fun UserEmailRow(userEmail: String){
    IconInformationText(icon = Icons.Default.Email, text = userEmail)
}

@Composable
fun UserLocationRow(userLocation: String){
    IconInformationText(icon = Icons.Default.LocationOn, text = userLocation )
}


@Composable
fun EditUserDialog(
    userName: String,
    userEmail: String,
    userLocation: String,
    error: String,
    showDialog : Boolean ,
    onDismiss: () -> Unit,
    onSave: (String, String, String) -> Unit
) {
    val editedName = remember { mutableStateOf(userName) }
    val editedEmail = remember { mutableStateOf(userEmail) }
    val editedLocation = remember { mutableStateOf(userLocation) }

    if(!showDialog) return
    Dialog(
        onDismissRequest = {
            onDismiss()
        }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ){
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Edit user information", style = MaterialTheme.typography.headlineSmall)

                TextInputField(
                    label = "Name",
                    placeholder = editedName.value,
                    action = { editedName.value = it },
                )
                TextInputField(
                    label = "Email",
                    placeholder = editedEmail.value,
                    action = { editedEmail.value = it }
                )
                TextInputField(
                    label = "Location",
                    placeholder = editedLocation.value,
                    action = { editedLocation.value = it }
                )
                Spacer(modifier = Modifier.height(16.dp))
                ErrorText(text = error)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = {
                            onDismiss()
                        },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(text = "Cancel")
                    }
                    Button(
                        onClick = {
                            // Perform your save operation here
                            onSave(editedName.value, editedEmail.value, editedLocation.value)
                        }
                    ) {
                        Text(text = "Save")
                    }
                }
            }
        }

    }
}

@Preview
@Composable
fun UserInformationPagePreview() {
    UserInformationPage()
}

@Preview
@Composable
fun EditUserDialogPreview() {
    EditUserDialog(
        userName = "John Doe",
        userEmail = "johnDoe@gmail.com",
        userLocation = "New York",
        error = "",
        showDialog = true,
        onDismiss = {},
        onSave = { _, _, _ -> }
    )
}
