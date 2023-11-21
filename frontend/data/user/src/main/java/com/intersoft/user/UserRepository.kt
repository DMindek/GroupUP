package com.intersoft.user

import android.util.Log
import com.google.gson.Gson
import com.intersoft.network.NetworkManager
import com.intersoft.network.RequestListener
import com.intersoft.network.models.responses.RegisterBody
import com.intersoft.network.models.responses.UserData
import org.json.JSONObject

class UserRepository: IUserRepository {

    override fun addUser(
        newUser: UserModel,
        onRegisterSucceed: () -> Unit,
        onRegistrationError: (String) -> Unit
    ) {
        Log.d("UserRepository", newUser.toString())
        val user = UserData(newUser.username, newUser.email, newUser.password, newUser.location)
        val res = NetworkManager.registerUser(RegisterBody(user), object: RequestListener {
            override fun <T> onSuccess(data: T) {
                Log.d("UserRepository", "User added successfully")
                onRegisterSucceed()
            }

            override fun onError(error: String) {
                Log.d("UserRepository", "Error occurred: $error")
                if(error != null){
                    if(error[0] != '{') {
                        onRegistrationError(error)
                        return
                    }

                    val errorFinal: RegistrationErrorResponse
                    try {
                        errorFinal = Gson().fromJson(error, RegistrationErrorResponse::class.java)
                    }catch (e: Exception){
                        onRegistrationError("Server returned unknown error")
                        return
                    }

                    if(errorFinal.email != null)
                        onRegistrationError(errorFinal.email[0])
                    else if(errorFinal.username != null) onRegistrationError(errorFinal.username[0])
                    else if(errorFinal.password != null) onRegistrationError(errorFinal.password[0])
                }
            }

        })
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
