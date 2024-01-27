package com.intersoft.user

interface IUserRepository {
    fun addUser(newUser: UserModel, onRegistrationSucceed: ()->Unit ,onRegistrationError: (String) -> Unit)
    fun logIn(email: String, password: String, onLoginSuccess: (LoginSuccessResponse) -> Unit, onLoginError: (String) -> Unit)
    fun editUser(user: UserModel, onEditSuccess: (UserModel) -> Unit, onEditError: (String) -> Unit)
    fun getHostname(hostId: Int, authToken: String,onGetHostnameError: (String?) -> Unit ,onGetHostNameSuccess: (String) -> Unit){}

    fun getUsersByUsername(username: String, authToken: String, onGetUsersByUsernameSuccess: (UserModel) -> Unit, onGetUsersByUsernameError: (String) -> Unit){}

}