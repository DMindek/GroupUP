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
}

class RegistrationErrorResponse{
    val email: Array<String>? = null
    val username: Array<String>? = null
    val password: Array<String>? = null
}