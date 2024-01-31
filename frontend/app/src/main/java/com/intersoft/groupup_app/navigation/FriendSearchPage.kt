package com.intersoft.groupup_app.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.intersoft.auth.AuthContext
import com.intersoft.social.UsersViewModel
import com.intersoft.ui.ConfirmationDialog
import com.intersoft.ui.ErrorText
import com.intersoft.ui.LoadingScreen
import com.intersoft.ui.TextSearchField
import com.intersoft.ui.UserListItem


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FriendSearchPage() {
    val viewModel = viewModel<UsersViewModel>()
    val users by viewModel._users.collectAsState()
    val error by viewModel.error.observeAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    val searchAttempted by viewModel.searchAttempted.collectAsState()
    val pageTitle = "Add new friend"

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
    var tempErrorText by remember {
        mutableStateOf("")
    }

    val keyboardController = LocalSoftwareKeyboardController.current


    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        item {
            TextSearchField(
                pageTitle = pageTitle,
                onTextChanged = { searchText ->
                    viewModel.onSearchTextChange(searchText)
                },
                onIconClicked = {
                    viewModel.searchForUser(AuthContext.token!!, AuthContext.id!!)
                    clearPage = viewModel.searchTextIsBlank()
                    if(clearPage){
                        tempErrorText = ""
                        viewModel.clearSearchAttempt()
                    }
                    keyboardController?.hide()
                },
                errorText = ""
            )
            ErrorText(text = getErrorText(users.size, error!!,searchAttempted, clearPage))
        }
        items(
            count = users.size,
            key = { index -> users[index].id!! },
            itemContent = { index ->
                val user = users[index]

                @Suppress("ControlFlowWithEmptyBody")
                if(clearPage){

                }
                else {
                    if (isSearching) {
                        LoadingScreen()
                    } else {
                            UserListItem(user.username) {
                                dialogUsername = user.username
                                dialogEmail = user.email
                                showUserCard = true
                            }
                    }
                }
            }
        )
    }

    if (showUserCard)
        ConfirmationDialog(
            title = "Add friend?",
            dialogText = "Username: $dialogUsername \nEmail: $dialogEmail",
            onDismissButton = { showUserCard = false },
            onConfirmButton = { showUserCard = false }
        )

}

private fun getErrorText(size: Int, error: String, searchAtempted : Boolean, clearPage: Boolean): String {

    return if(!searchAtempted || clearPage){
        ""
    } else if (size == 0 && error != "") {
        error
    }
    else if (size == 0){
        "No users found with such a username"
    }
    else
        ""

}



