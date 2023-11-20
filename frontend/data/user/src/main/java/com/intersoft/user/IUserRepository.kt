package com.intersoft.user

interface IUserRepository {
    fun addUser(newUser: UserModel, onRegistrationError: (String) -> Unit)
    fun editUser(user: UserModel, onEditError: (String) -> Unit)
}