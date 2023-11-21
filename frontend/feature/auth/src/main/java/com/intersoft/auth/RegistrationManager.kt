package com.intersoft.auth

import com.intersoft.user.IUserRepository
import com.intersoft.user.UserModel
import com.intersoft.user.UserRepository

object RegistrationManager {
    private var userRepository: IUserRepository = UserRepository()

    fun setRepository(repo: IUserRepository){
        userRepository = repo
    }

    fun registerUser(user: UserModel, passwordRetype: String, onRegisterSuccess: () -> Unit, onRegisterFail: (String) -> Unit){
        var errors: String = validateInput(user, passwordRetype)
        if(errors != ""){
            onRegisterFail(errors)
            return
        }

        userRepository.addUser(user, onRegisterSuccess){
            errors = it
            if(errors != ""){
                onRegisterFail(errors)
            }
        }

    }

    private fun validateInput(user: UserModel, passwordRetype: String): String{
        if(!isValidEmail(user.email)) return "Please enter a valid e-mail"
        if(user.username.length > 15) return "Username must have at most 15 characters"
        if("\\W".toRegex().containsMatchIn(user.username)) return "Username can only contain letters and numbers"
        if(user.username.isEmpty()) return "Please enter a username"
        if(user.location.isEmpty()) return "Please enter a location"
        if(!hasLetterDigitSymbol(user.password)) return "Password must contain at least one letter, number and symbol"
        if(user.password.length < 10) return "Password must have at least 10 characters"
        if(user.password.length > 20) return "Password can only have 20 characters at most"
        if(user.password != passwordRetype) return "Passwords do not match"
        return ""
    }

    private fun hasLetterDigitSymbol(text: String): Boolean{
        return "[a-zA-Z]".toRegex().containsMatchIn(text) &&
                "\\d".toRegex().containsMatchIn(text) &&
                "\\W".toRegex().containsMatchIn(text)
    }

    private fun isValidEmail(email: String): Boolean{
        return "^[\\w\\-.]+@([\\w-]+\\.)+[\\w-]{2,}\$".toRegex().containsMatchIn(email)
    }
}