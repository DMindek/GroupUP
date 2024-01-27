package com.intersoft.user

import android.util.Log
import com.google.gson.Gson
import com.intersoft.network.NetworkManager
import com.intersoft.network.RequestListener
import com.intersoft.network.models.responses.EditBody
import com.intersoft.network.models.responses.LoginBody
import com.intersoft.network.models.responses.RegisterBody
import com.intersoft.network.models.responses.UserData

class UserRepository: IUserRepository {

    override fun addUser(
        newUser: UserModel,
        onRegisterSucceed: () -> Unit,
        onRegistrationError: (String) -> Unit
    ) {
        Log.d("UserRepository", newUser.toString())
        val user = UserData(newUser.username, newUser.email, newUser.location, newUser.password,)
        val res = NetworkManager.registerUser(RegisterBody(user), object: RequestListener {
            override fun <T> onSuccess(data: T) {
                Log.d("UserRepository", "User added successfully")
                onRegisterSucceed()
            }

            override fun onError(error: String?) {
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
        NetworkManager.logInUser(LoginBody(email, password), onLoginSuccess = {
            val res =  LoginSuccessResponse(it.token, it.email, it.id, it.username, it.location)

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

    override fun editUser(
        user: UserModel,
        onEditSuccess: (UserModel) -> Unit,
        onEditError: (String) -> Unit
    )
    {
        val userData = UserData(user.username, user.email, user.location)
        val res = NetworkManager.editUser(EditBody(userData), user.id!!, user.token!!, onEditSuccess = {
            if(it == null){
                onEditError("Server returned o body")
                return@editUser
            }
            val userModel = UserModel(it.username, it.email, "", it.location)
            onEditSuccess(userModel)
        }, onEditError = {
            Log.d("UserRepository", "Error occurred: $it")
            if(!it.isNullOrEmpty()){
                if(it[0] != '{') {
                    onEditError(it)
                }
                else{
                    val error: RegistrationErrorResponse
                    try {
                        error = Gson().fromJson(it, RegistrationErrorResponse::class.java)
                        if(error.email != null)
                            onEditError(error.email[0])
                        else if(error.username != null) onEditError(error.username[0])

                    }catch (e: Exception){
                        onEditError("Server returned unknown error")
                    }

                }
            } else onEditError("Server returned unknown error")
        })
    }

    override fun getHostname(hostId: Int, authToken: String,onGetHostnameError: (String?) -> Unit, onGetHostNameSuccess: (String) -> Unit) {
        NetworkManager.getUser(
            hostId,
            authToken,
            onGetUserSuccess = {
                val hostName = it.username
                Log.d("EventRepository", "Recieved hostname: $hostName")
                onGetHostNameSuccess(hostName)
            },
            onGetUserError = {
                Log.d("EventRepository", "Error occurred: $it")
                if(!it.isNullOrEmpty())if(it[0] != '{') {
                    onGetHostnameError(it)
                }
                else{
                    val error: GetHostnameFailResponse
                    try {
                        error = Gson().fromJson(it, GetHostnameFailResponse::class.java)
                        Log.d("EventRepository", "Error occurred $error")
                    }catch (e: Exception){
                        onGetHostnameError("Server returned unknown error")
                        return@getUser
                    }
                }
            }
        )
    }

    override fun getUsersByUsername(
        username: String,
        authToken: String,
        onGetUsersByUsernameSuccess: (List<UserModel>) -> Unit,
        onGetUsersByUsernameError: (String) -> Unit
    ) {
        NetworkManager.getUserByUsername(
            username,
            authToken,
            onGetUsersByUsernameSuccess = { userData ->
                val receivedUserData = userData.map{ user ->
                    UserModel(
                        username = user.username,
                        email = user.email,
                        password = "",
                        location = user.location,
                        id = user.id,
                        token = null
                    )
                }
                Log.d("EventRepository", "Received user data: $receivedUserData")
                onGetUsersByUsernameSuccess(receivedUserData)
            },
            onGetUsersByUsernameError = {
                Log.d("EventRepository", "Error occurred: $it")
                if(!it.isNullOrEmpty())if(it[0] != '{') {
                    onGetUsersByUsernameError(it)
                }
                else {
                    val error: SingleError
                    try {
                        error = Gson().fromJson(it, SingleError::class.java)
                        Log.d("EventRepository", "Error occurred $error")
                    } catch (e: Exception) {
                        onGetUsersByUsernameError("Server returned unknown error")
                        return@getUserByUsername
                    }
                }
            }
        )
    }

}

class RegistrationErrorResponse {
    val email: Array<String>? = null
    val username: Array<String>? = null
    val password: Array<String>? = null
}

class SingleError{
    val error: String? = null
}

data class LoginSuccessResponse(
    val token: String? = null,
    val email: String? = null,
    val id: Int? = null,
    val username: String? = null,
    val location: String? = null
)

data class GetHostnameFailResponse(
    val message: String?
)
