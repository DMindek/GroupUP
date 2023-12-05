package com.intersoft.auth

import android.content.Context
import androidx.core.content.ContextCompat.getString
import com.intersoft.user.IUserRepository
import com.intersoft.user.LoginSuccessResponse
import com.intersoft.user.UserRepository

object LoginManager {
    private var userRepository: IUserRepository = UserRepository()

    fun setRepository(repo: IUserRepository){
        userRepository = repo
    }

    fun logIn(email: String, password: String, context: Context, onLoginSuccess: () -> Unit, onLoginFail: (String) -> Unit){
        val error = validateInput(email, password)

        if(error != null)onLoginFail(error)
        else UserRepository().logIn(email, password, onLoginSuccess = {res ->
            if(res.token != null && res.username != null && res.location != null && res.id != null && res.email != null){
                storeAuth(res, context)
                onLoginSuccess()
            }
            else{
                onLoginFail("Server communication error")
            }
        }, onLoginFail)
    }

    private fun validateInput(email: String, password: String): String?{
        if(email.isEmpty()) return "Please enter an e-mail"
        if(password.isEmpty()) return "Please enter a password"
        return null
    }

    private fun storeAuth(res: LoginSuccessResponse, context: Context){
        AuthContext.id = res.id
        AuthContext.email = res.email
        AuthContext.token = res.token
        AuthContext.username = res.username
        AuthContext.location = res.location
        val sharedPref = context.getSharedPreferences(getString(context, R.string.shared_prefs_file), Context.MODE_PRIVATE)
        with(sharedPref.edit()){
            putString(getString(context, R.string.shared_prefs_user_token), AuthContext.token)
            putInt(getString(context, R.string.shared_prefs_user_id), AuthContext.id!!)
            putString(getString(context, R.string.shared_prefs_user_username), AuthContext.username)
            putString(getString(context, R.string.shared_prefs_user_email), AuthContext.email)
            putString(getString(context, R.string.shared_prefs_user_location), AuthContext.location)
            apply()
        }
    }
}