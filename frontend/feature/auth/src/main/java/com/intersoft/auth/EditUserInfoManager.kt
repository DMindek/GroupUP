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
        var errors = ""

        /* TODO Edit user integration
        userRepository.editUser(user){s
            errors = it
        }
        */

        if(errors != ""){
            onEditFail(errors)
            return
        }

        onEditSuccess()
    }
}