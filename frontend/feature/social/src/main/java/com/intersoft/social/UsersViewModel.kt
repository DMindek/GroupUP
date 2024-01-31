package com.intersoft.social

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.intersoft.user.IUserRepository
import com.intersoft.user.UserModel
import com.intersoft.user.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UsersViewModel () : ViewModel() {

    private var userRepository : IUserRepository = UserRepository()

    private val _searchText = MutableStateFlow("")

    private val _isSearching = MutableStateFlow(false)

    val isSearching = _isSearching.asStateFlow()

    private val _searchAttempted = MutableStateFlow(false)

    val searchAttempted = _searchAttempted.asStateFlow()

    var _users = MutableStateFlow(listOf<UserModel>())

    private val _error : MutableLiveData<String> = MutableLiveData("")

    val error: LiveData<String> = _error



    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun searchForUser(authToken: String, loggedInUserId: Int) {
        if(_searchText.value != ""){
            isSearchingForUsers()
            getUsersByUsername(_searchText.value,authToken, loggedInUserId)
        }
    }

    fun searchTextIsBlank() : Boolean {
        return _searchText.value == ""
    }

    fun clearSearchAttempt(){
        _searchAttempted.value = false
    }
    private fun isSearchingForUsers() {
        _isSearching.value = true
    }

    private fun searchForUsersComplete() {
        _isSearching.value = false
        _searchAttempted.value = true
    }
     private fun getUsersByUsername(username: String, authToken: String, loggedInUserId: Int){
        userRepository.getUsersByUsername(
            username,
            authToken,
            onGetUsersByUsernameSuccess = { users ->
                if (users.isEmpty()){
                    _error.value = "There are no users with such a username"
                    _users.value = listOf()
                }
                else {
                    val listOfUsers = users.map {user ->
                        UserModel(
                            username = user.username,
                            email = user.email,
                            password = "",
                            location = user.location,
                            id = user.id,
                            token = null,
                            locationName = user.locationName
                        )

                    }
                    _error.value = ""
                    _users.value = listOfUsers.filter { it.id != loggedInUserId }
                }
                searchForUsersComplete()
            },
            onGetUsersByUsernameError = {
                _error.value = it
                _users.value = listOf()
                searchForUsersComplete()
            }
        )

    }
}