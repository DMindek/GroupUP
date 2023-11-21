package com.intersoft.user

interface IUserRepository {
    fun addUser(newUser: UserModel,onRegisterSucceed: ()->Unit, onRegistrationError: (String) -> Unit)
}