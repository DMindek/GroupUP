package com.intersoft.social

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intersoft.user.IUserRepository
import com.intersoft.user.UserModel
import com.intersoft.user.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.SharingStarted

class UsersViewModel () : ViewModel() {

    private var userRepository : IUserRepository = UserRepository()

    private val _searchText = MutableStateFlow("")

    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)

    val isSearching = _isSearching.asStateFlow()

    private val _authToken = MutableStateFlow("")

    val authToken = _authToken.asStateFlow()

    val _userList = getUsersByUsername(searchText.toString(), authToken.toString())
    private var _users = MutableStateFlow(_userList)

    private val _error : MutableLiveData<String> = MutableLiveData("")

    val error: LiveData<String> = _error


    @OptIn(FlowPreview::class)
    val users = searchText
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .combine(_users){text, users ->
            if(text.isBlank()) {
                ""
            }
            else {
                users
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _users
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun setAuthToken(text : String){
        _authToken.value = text
    }

    private fun getUsersByUsername(username: String, authToken: String) : List<UserModel>{
        var tempUsers = listOf<UserModel>()
        Log.d("CEKAM", "evo sad bu")
        userRepository.getUsersByUsername(
            username,
            authToken,
            onGetUsersByUsernameSuccess = { users ->
                if (users.isEmpty()){
                    _error.value = "There are no users with such a username"
                }
                else {
                    val listOfUsers = users.map {user ->
                       UserModel(
                           username = user.username,
                           email = user.email,
                           password = "",
                           location = user.location,
                           id = user.id,
                           token = null
                       )
                    }
                    tempUsers = listOfUsers
                }
                Log.d("DELAM", users.toString())
            },
            onGetUsersByUsernameError = {
                _error.value = it
                Log.d("DELAM2", it)
            }
        )
        return tempUsers
    }
}