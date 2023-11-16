package com.intersoft.user

interface IUserRepository {
    fun addUser(newUser: UserModel, onRegistrationError: (String) -> Unit)
    fun logIn(username: String, password: String, onLoginSuccess: () -> Unit, onLoginError: (String) -> Unit)
}