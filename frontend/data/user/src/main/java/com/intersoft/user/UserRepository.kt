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

    override fun editUser(user: UserModel, onEditError: (String) -> Unit) {
        val userJson = JSONObject().put("user", JSONObject()
            .put("username", user.username)
            .put("email", user.email)
            .put("location", user.location)
        )

        val res = NetworkManager.editUser(userJson.toString())
        if(res != null){
            if(res[0] != '{') {
                onEditError(res)
                return
            }

            val error: EditUserInfoErrorResponse
            try {
                error = Gson().fromJson(res, EditUserInfoErrorResponse::class.java)
            }catch (e: Exception){
                onEditError("Server returned unknown error")
                return
            }

            if(error.error != null)
                onEditError(error.error)
        }
    }
}

class RegistrationErrorResponse {
    val email: Array<String>? = null
    val username: Array<String>? = null
    val password: Array<String>? = null
}

class EditUserInfoErrorResponse {
    val error: String? = null
}