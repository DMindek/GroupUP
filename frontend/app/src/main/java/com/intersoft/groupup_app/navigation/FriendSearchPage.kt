package com.intersoft.groupup_app.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.intersoft.auth.AuthContext
import com.intersoft.social.UsersViewModel
import com.intersoft.ui.DisabledTextField
import com.intersoft.ui.LoadingScreen
import com.intersoft.ui.TextInputField
import com.intersoft.ui.TitleText
import com.intersoft.user.UserModel


@Composable
fun FriendSearchPage(
    onFriendElementClick: () -> Unit
){
    val viewModel = viewModel<UsersViewModel>()
    val searchText by viewModel.searchText.collectAsState()
    //val users by viewModel.users.collectAsState()
    val users by viewModel._users.collectAsState()
    val error by viewModel.error.observeAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    val pageTitle = "Add new friend"

    //viewModel.getUsersByUsername("admin",AuthContext.token!!)

    Column (
        modifier = Modifier
            .padding(16.dp)
            .background(color = Color.White)
            .verticalScroll(rememberScrollState())
            .fillMaxHeight(),
    ) {
        TitleText(text = pageTitle)
        TextInputField(label = "Search") { username ->
            viewModel.onSearchTextChange(username, AuthContext.token!!)
        }

        if (isSearching) {
            LoadingScreen()
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp)
            ) {
                items(
                    count = users.size,
                    key = { index -> users[index].id!! },
                    itemContent = { index ->
                        val user = users[index]
                        UserListItem(user = user)
                    }
                )

            }
        }
    }
}


@Composable
fun UserListItem(user: UserModel){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ){
        DisabledTextField(textvalue = user.username)
    }

}

/*
@Composable
fun UserDataScreen(data: List<IIterableObject>, onEventClick: () -> Unit) {
    Column(content = {

        for(user in data) {
            UserCard(
                user = user,
                onEventClick = onEventClick
            )
        }
    })
}

@Composable
fun UserCard(user: IIterableObject, onEventClick: () -> Unit) {
    ObjectCard(data = user, interaction = {
        onEventClick()
    })
}
*/