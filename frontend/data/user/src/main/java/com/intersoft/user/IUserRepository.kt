package com.intersoft.user

interface IUserRepository {
    fun addUser(newUser: UserModel, onRegistrationError: (String) -> Unit)
    fun logIn(email: String, password: String, onLoginSuccess: () -> Unit, onLoginError: (String) -> Unit)
}