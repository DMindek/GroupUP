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

    override fun editUser(user: UserModel, onEditError: (String) -> Unit) {
        val index = userList.indexOfFirst { it.username == user.username }
        if(index == -1){
            onEditError("User does not exist")
        }
        else{
            userList[index] = user
        }
    }
}