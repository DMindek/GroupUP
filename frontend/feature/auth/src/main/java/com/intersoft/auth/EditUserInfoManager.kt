package com.intersoft.auth

import com.intersoft.user.IUserRepository
import com.intersoft.user.UserModel
import com.intersoft.user.UserRepository

object EditUserInfoManager {
    private var userRepository: IUserRepository = UserRepository()

    fun setRepository(repo: IUserRepository){
        userRepository = repo
    }

    fun editUser(user: UserModel, onEditSuccess: () -> Unit, onEditFail: (String) -> Unit){
        user.id = AuthContext.id
        user.token = AuthContext.token

        userRepository.editUser(user, onEditSuccess = { res ->
            editAuth(res)
            onEditSuccess()
        }){
            onEditFail(it)
        }
    }

    private fun editAuth(user: UserModel){
        AuthContext.username = user.username
        AuthContext.email = user.email
        AuthContext.location = user.location
    }
}