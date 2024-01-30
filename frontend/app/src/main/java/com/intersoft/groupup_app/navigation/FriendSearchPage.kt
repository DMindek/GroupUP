package com.intersoft.groupup_app.navigation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.intersoft.auth.AuthContext
import com.intersoft.social.UsersViewModel
import com.intersoft.ui.ErrorText
import com.intersoft.ui.LoadingScreen
import com.intersoft.ui.TextSearchField
import com.intersoft.ui.TitleText
import com.intersoft.ui.UserListItem


@Composable
fun FriendSearchPage(
    onFriendElementClick: () -> Unit
){
    val viewModel = viewModel<UsersViewModel>()
    val users by viewModel._users.collectAsState()
    val error by viewModel.error.observeAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    val pageTitle = "Add new friend"
    var searchAttempted by remember {
        mutableStateOf(false)
    }
    var clearPage by remember {
        mutableStateOf(true)
    }
    val noUsersFoundErrorText = "No users found with such a username"
    Column (
        modifier = Modifier
            .padding(16.dp)
            .background(color = Color.White)
            .verticalScroll(rememberScrollState())
            .fillMaxHeight(),
    ) {
        TitleText(text = pageTitle)
        TextSearchField(
            onTextChanged ={ searchText ->
                viewModel.onSearchTextChange(searchText)
            } ,
            onIconClicked = {
                viewModel.searchForUser(AuthContext.token!!)
                clearPage = viewModel.searchTextIsBlank()
                searchAttempted = true
            }
        )

        @Suppress("ControlFlowWithEmptyBody")
        if(clearPage){

        }
        else {
            if (isSearching) {
                LoadingScreen()
            } else {
                Log.d("DELAM I TU","Stanje: searchAttempted = $searchAttempted error = $error users = ${users.isNotEmpty()}")
                if(error == "" && users.isNotEmpty()) {
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
                                UserListItem(user.username)
                            }
                        )
                    }
                } else if (users.isEmpty() && searchAttempted && error == ""){
                    ErrorText(text = noUsersFoundErrorText)
                } else{
                    if(searchAttempted ) {
                        ErrorText(text = error.toString())
                    }
                }
            }
        }
    }
}



