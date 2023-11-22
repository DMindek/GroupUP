package com.intersoft.groupup_app.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.intersoft.auth.AuthContext
import com.intersoft.auth.EditUserInfoManager
import com.intersoft.groupup_app.R
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
            onDismiss = {
                isEditDialogVisible = false
            },
            onSave = {name, email, location ->
                editedUserName = name
                editedUserEmail = email
                editedUserLocation = location
                isEditDialogVisible = false
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
    onDismiss: () -> Unit,
    onSave: (String, String, String) -> Unit
) {
    val editedName = remember { mutableStateOf(userName) }
    val editedEmail = remember { mutableStateOf(userEmail) }
    val editedLocation = remember { mutableStateOf(userLocation) }

    AlertDialog(
        onDismissRequest = {
            onDismiss()
                           },
        title = { Text(text = "Edit user information") },
        text = {
            Column {
                TextInputField(label = "Name", placeholder = editedName.value) {
                    editedName.value = it
                }
                TextInputField(label = "Email", placeholder = editedEmail.value) {
                    editedEmail.value = it
                }
                TextInputField(label = "Location", placeholder = editedLocation.value) {
                    editedLocation.value = it
                }
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text(text = "Cancel")
            } },
        confirmButton = {
            PrimaryButton(buttonText ="Save") {
                EditUserInfoManager.editUser(
                    UserModel(
                        editedName.value,
                        editedEmail.value,
                        "",
                        editedLocation.value
                    ),
                    onEditSuccess = {  onSave(editedName.value, editedEmail.value, editedLocation.value) },
                    onEditFail = { onDismiss() }
                )

            } }

        )
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
        onDismiss = {},
        onSave = { _, _, _ -> }
    )
}
