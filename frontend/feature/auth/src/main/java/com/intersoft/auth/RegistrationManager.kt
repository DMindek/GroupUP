package com.intersoft.auth

import com.intersoft.user.UserModel

object RegistrationManager {
    fun registerUser(user: UserModel, passwordRetype: String, onRegisterSuccess: () -> Unit, onRegisterFail: (String) -> Unit){
        val errors: String = validateInput(user, passwordRetype)
        if(errors != ""){
            onRegisterFail(errors)
            return
        }
    }
    private fun validateInput(user: UserModel, passwordRetype: String): String{
        if(user.username.length > 15) return "Username must have at most 15 characters"
        if("(\\D\\W)".toRegex().containsMatchIn(user.username)) return "Username can only contain letters and numbers"
        if(!"".toRegex().containsMatchIn(user.password)) return "Password must contain letters, numbers and symbols"
        if(user.password.length < 10) return "Password must have at least 10 characters"
        if(user.password.length > 20) return "Password can only have 20 characters at most"
        if(user.password != passwordRetype) return "Passwords do not match"
        return ""
    }
}