package com.intersoft.user

class MockUserRepository : IUserRepository {
    private val userList = mutableListOf(
        UserModel("test", "mail@mail", "abc", "10.0,10.0", "here"),
        UserModel("me", "mail@email", "123", "10.0,10.0", "there"),
    )

    override fun addUser(
        newUser: UserModel,
        onRegistrationSucceed: () -> Unit,
        onRegistrationError: (String) -> Unit
    ) {
        if(userList.find { user -> (user.username == newUser.username) || (user.email == newUser.email) } == null){
            userList.add(newUser)
            onRegistrationSucceed()
        }
        else{
            onRegistrationError("User already exists")
        }
    }

    
    override fun logIn(email: String, password: String, onLoginSuccess: (LoginSuccessResponse) -> Unit, onLoginError: (String) -> Unit) {
        if(userList.find { user -> (user.email == email) || (user.password == password) } == null){
            onLoginError("Incorrect credentials")
        }
        else onLoginSuccess(LoginSuccessResponse())
    }

    override fun editUser(
        user: UserModel,
        onEditSuccess: (UserModel) -> Unit,
        onEditError: (String) -> Unit
    ) {
        TODO("Not yet implemented")
    }
}