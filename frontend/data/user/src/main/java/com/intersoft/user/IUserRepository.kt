package com.intersoft.user

interface IUserRepository {
    fun getUserByUsername(searchUsername: String): UserModel?

    fun addUser(newUser: UserModel, onRegistrationError: (String) -> Unit)
}