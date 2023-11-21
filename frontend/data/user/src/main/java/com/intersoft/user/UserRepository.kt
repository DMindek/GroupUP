package com.intersoft.user

import com.google.gson.Gson
import com.intersoft.network.NetworkManager
import org.json.JSONObject

class UserRepository: IUserRepository {
    override fun addUser(newUser: UserModel, onRegistrationError: (String) -> Unit) {
        val user = JSONObject().put("user", JSONObject()
            .put("username", newUser.username)
            .put("email", newUser.email)
            .put("password", newUser.password)
            .put("location", newUser.location)
        )

        val res = NetworkManager.registerUser(user.toString())
        if(res != null){
            if(res[0] != '{') {
                onRegistrationError(res)
                return
            }

            val error: RegistrationErrorResponse
            try {
                error = Gson().fromJson(res, RegistrationErrorResponse::class.java)
            }catch (e: Exception){
                onRegistrationError("Server returned unknown error")
                return
            }

            if(error.email != null)
                onRegistrationError(error.email[0])
            else if(error.username != null) onRegistrationError(error.username[0])
            else if(error.password != null) onRegistrationError(error.password[0])
        }
    }

    override fun logIn(email: String, password: String, onLoginSuccess: (LoginSuccessResponse) -> Unit, onLoginError: (String) -> Unit) {
        val data = JSONObject()
            .put("email", email)
            .put("password", password)

        NetworkManager.logInUser(data.toString(), onLoginSuccess = {
            val res: LoginSuccessResponse
            try {
                res = Gson().fromJson(it, LoginSuccessResponse::class.java)
            }catch (e: Exception){
                onLoginError("Could not parse server response")
                return@logInUser
            }

            onLoginSuccess(res)
        }){
            if(it != null){
                if(it[0] != '{') {
                    onLoginError(it)
                }
                else{
                    val error: SingleError
                    try {
                        error = Gson().fromJson(it, SingleError::class.java)
                    }catch (e: Exception){
                        onLoginError("Server returned unknown error")
                        return@logInUser
                    }
                    if(error.error != null){
                        onLoginError(error.error)
                    }
                }
            }
        }
    }
}

class RegistrationErrorResponse{
    val email: Array<String>? = null
    val username: Array<String>? = null
    val password: Array<String>? = null
}

class SingleError{
    val error: String? = null
}

class LoginSuccessResponse{
    val token: String? = null
    val email: String? = null
    val id: Int? = null
    val username: String? = null
    val location: String? = null
}
