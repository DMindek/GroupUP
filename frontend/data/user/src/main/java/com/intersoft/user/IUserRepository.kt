package com.intersoft.user

interface IUserRepository {
    fun addUser(newUser: UserModel, onRegistrationError: (String) -> Unit)
    fun logIn(email: String, password: String, onLoginSuccess: (LoginSuccessResponse) -> Unit, onLoginError: (String) -> Unit)
}