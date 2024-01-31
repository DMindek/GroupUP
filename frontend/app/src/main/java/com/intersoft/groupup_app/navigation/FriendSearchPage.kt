package com.intersoft.groupup_app.navigation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.intersoft.auth.AuthContext
import com.intersoft.social.UsersViewModel
import com.intersoft.ui.ConfirmationDialog
import com.intersoft.ui.ErrorText
import com.intersoft.ui.LoadingScreen
import com.intersoft.ui.TextSearchField
import com.intersoft.ui.TitleText
import com.intersoft.ui.UserListItem


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FriendSearchPage(){
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

    var showUserCard by remember {
        mutableStateOf(false)
    }
    var dialogUsername by remember {
        mutableStateOf("")
    }
    var dialogEmail by remember {
        mutableStateOf("")
    }
    val noUsersFoundErrorText = "No users found with such a username"
    val keyboardController = LocalSoftwareKeyboardController.current

   /* Column (
        modifier = Modifier
            .padding(16.dp)
            .background(color = Color.White)
            .verticalScroll(rememberScrollState())
            .fillMaxHeight(),
    ) {
        TitleText(text = pageTitle)
        TextSearchField(
            onTextChanged = { searchText ->
                viewModel.onSearchTextChange(searchText)
            },
            onIconClicked = {
                viewModel.searchForUser(AuthContext.token!!)
                clearPage = viewModel.searchTextIsBlank()
                searchAttempted = true
                keyboardController?.hide()

            }
        )

    */

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            item(){
                TextSearchField(
                    pageTitle = pageTitle,
                    onTextChanged = { searchText ->
                        viewModel.onSearchTextChange(searchText)
                    },
                    onIconClicked = {
                        viewModel.searchForUser(AuthContext.token!!)
                        clearPage = viewModel.searchTextIsBlank()
                        searchAttempted = true
                        keyboardController?.hide()

                    }
                )
            }
            items(
                count = 1,
                key = { 1 },
                itemContent = {
                    UserListItem("") {
                        dialogUsername = "user.username"
                        dialogEmail = "user.email"
                        showUserCard = true
                    }
                }
            )
        }


        

     /*   @Suppress("ControlFlowWithEmptyBody")
        if (clearPage) {

        } else {
            if (isSearching) {
                LoadingScreen()
            } else {
                if (error == "" && users.isNotEmpty()) {
                    Log.d("DELAM", "TU sam v lazycolumnu ispred, data: ${users[0].username}")
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
                                UserListItem(user.username) {
                                    dialogUsername = user.username
                                    dialogEmail = user.email
                                    showUserCard = true
                                }
                            }
                        )
                    }
                } else if (users.isEmpty() && searchAttempted && error == "") {
                    ErrorText(text = noUsersFoundErrorText)
                } else {
                    if (searchAttempted) {
                        ErrorText(text = error.toString())
                    }
                }
            }
        }

        if (showUserCard)
            ConfirmationDialog(
                title = "Add friend?",
                dialogText = "Username: $dialogUsername \nEmail: $dialogEmail",
                onDismissButton = { showUserCard = false },
                onConfirmButton = { showUserCard = false }
            )
    }

      */
}



