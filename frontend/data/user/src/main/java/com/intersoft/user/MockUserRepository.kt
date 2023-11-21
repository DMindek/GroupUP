package com.intersoft.user

class MockUserRepository : IUserRepository {
    private val userList = mutableListOf(
        UserModel("test", "mail@mail", "abc", "here"),
        UserModel("me", "mail@email", "123", "there"),
    )

    override fun addUser(
        newUser: UserModel,
        onRegisterSucceed: () -> Unit,
        onRegistrationError: (String) -> Unit
    ) {
        if(userList.find { user -> (user.username == newUser.username) || (user.email == newUser.email) } == null){
            userList.add(newUser)
            onRegisterSucceed()
        }
        else{
            onRegistrationError("User already exists")
        }
    }
}