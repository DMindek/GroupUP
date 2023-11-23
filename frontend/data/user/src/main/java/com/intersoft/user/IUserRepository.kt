package com.intersoft.user

import com.intersoft.network.models.responses.EditBody

interface IUserRepository {
    fun addUser(newUser: UserModel, onRegistrationSucceed: ()->Unit ,onRegistrationError: (String) -> Unit)
    fun logIn(email: String, password: String, onLoginSuccess: (LoginSuccessResponse) -> Unit, onLoginError: (String) -> Unit)
    fun editUser(user: UserModel, onEditSuccess: (UserModel) -> Unit, onEditError: (String) -> Unit)
}