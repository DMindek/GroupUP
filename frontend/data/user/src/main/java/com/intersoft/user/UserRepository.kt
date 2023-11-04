package com.intersoft.user

interface UserRepository {
    fun getUserByUsername(searchUsername: String): UserModel?

    fun addUser(newUser: UserModel, onRegistrationError: (String) -> Unit)
}