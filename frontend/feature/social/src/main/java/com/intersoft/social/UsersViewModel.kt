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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow

class UsersViewModel () : ViewModel() {

    private var userRepository : IUserRepository = UserRepository()

    private val _searchText = MutableStateFlow("")

    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)

    val isSearching = _isSearching.asStateFlow()

    //private val _authToken = MutableStateFlow("")

    //val authToken = _authToken.asStateFlow()

    //val _userList = getUsersByUsername(_searchText.value, _authToken.value)
    //private var userList = listOf<UserModel>()
    var _users = MutableStateFlow(listOf<UserModel>())

    private val _error : MutableLiveData<String> = MutableLiveData("")

    val error: LiveData<String> = _error

    /*
    @OptIn(FlowPreview::class)
    val users = _users
        .debounce(1000L)
        .onEach { _isSearching.update { true } }
        .onEach { _isSearching.update { false } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = _users
        )
*/

    fun onSearchTextChange(text: String, authToken: String) {
        _searchText.value = text
        getUsersByUsername(text, authToken)
    }



   /* fun setAuthToken(text : String){
        Log.d("DOSTAVA", text)
        _authToken.value = text
    }
    */

     private fun getUsersByUsername(username: String, authToken: String){
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
                    _users.value = listOfUsers
                    //Log.d("DELAM", "Trenutno stanje userlist: $userList")
                    Log.d("DELAM", "Trenutno stanje users: $_users")
                }
                Log.d("DELAM", "A ovo je bilo poslano od servera: $users")
            },
            onGetUsersByUsernameError = {
                _error.value = it
                Log.d("DELAM2", it)
            }
        )

    }
}