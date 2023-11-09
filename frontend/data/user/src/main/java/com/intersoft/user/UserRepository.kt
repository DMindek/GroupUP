package com.intersoft.user

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
            onRegistrationError(res)
        }
    }
}