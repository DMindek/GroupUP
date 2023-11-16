package com.intersoft.auth

import com.intersoft.user.IUserRepository
import com.intersoft.user.UserRepository

object LoginManager {
    private var userRepository: IUserRepository = UserRepository()

    fun setRepository(repo: IUserRepository){
        userRepository = repo
    }

    fun logIn(username: String, password: String, onLoginSuccess: () -> Unit, onLoginFail: (String) -> Unit){
        UserRepository().logIn(username, password, onLoginSuccess, onLoginFail)
    }
}