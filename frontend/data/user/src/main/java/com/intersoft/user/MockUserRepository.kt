package com.intersoft.user

class MockUserRepository : IUserRepository {
    private val userList = mutableListOf(
        UserModel("test", "mail@mail", "abc", "here"),
        UserModel("me", "mail@email", "123", "there"),
    )

    override fun addUser(newUser: UserModel, onRegistrationError: (String) -> Unit) {
        if(userList.find { user -> (user.username == newUser.username) || (user.email == newUser.email) } == null){
            userList.add(newUser)
        }
        else{
            onRegistrationError("User already exists")
        }
    }

    override fun logIn(username: String, password: String, onLoginSuccess: () -> Unit, onLoginError: (String) -> Unit) {
        if(userList.find { user -> (user.username == username) || (user.password == password) } == null){
            onLoginError("Incorrect credentials")
        }
        else onLoginSuccess()
    }
}