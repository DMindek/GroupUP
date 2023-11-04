package com.intersoft.user

interface UserRepository {
    fun getUserByUsername(username: String): UserModel?

    fun addUser(user: UserModel)
}