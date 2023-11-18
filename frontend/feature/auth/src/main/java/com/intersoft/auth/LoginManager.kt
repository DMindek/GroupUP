package com.intersoft.auth

import com.intersoft.user.IUserRepository
import com.intersoft.user.UserRepository

object LoginManager {
    private var userRepository: IUserRepository = UserRepository()

    fun setRepository(repo: IUserRepository){
        userRepository = repo
    }

    fun logIn(email: String, password: String, onLoginSuccess: () -> Unit, onLoginFail: (String) -> Unit){
        val error = validateInput(email, password)

        if(error != null)onLoginFail(error)
        else UserRepository().logIn(email, password, onLoginSuccess, onLoginFail)
    }

    private fun validateInput(email: String, password: String): String?{
        if(email.isEmpty()) return "Please enter an e-mail"
        if(password.isEmpty()) return "Please enter a password"
        return null
    }
}