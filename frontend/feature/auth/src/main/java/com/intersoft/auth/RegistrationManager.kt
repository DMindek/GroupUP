package com.intersoft.auth

import com.intersoft.user.MockUserRepository
import com.intersoft.user.UserModel

object RegistrationManager {
    private val userRepository = MockUserRepository()
    fun registerUser(user: UserModel, passwordRetype: String, onRegisterSuccess: () -> Unit, onRegisterFail: (String) -> Unit){
        var errors: String = validateInput(user, passwordRetype)
        if(errors != ""){
            onRegisterFail(errors)
            return
        }

        userRepository.addUser(user){
            errors = it
        }
        if(errors != ""){
            onRegisterFail(errors)
            return
        }

        onRegisterSuccess()
    }
    private fun validateInput(user: UserModel, passwordRetype: String): String{
        if(user.username.length > 15) return "Username must have at most 15 characters"
        if(user.username.isEmpty()) return "Please enter a username"
        if(user.location.isEmpty()) return "Please enter a location"
        if("\\W".toRegex().containsMatchIn(user.username)) return "Username can only contain letters and numbers"
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
}