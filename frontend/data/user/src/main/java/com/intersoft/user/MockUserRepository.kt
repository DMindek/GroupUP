package com.intersoft.user

class MockUserRepository : UserRepository {
    private val userList = mutableListOf(
        UserModel("test", "mail@mail", "abc", "here"),
        UserModel("me", "mail@email", "123", "there"),
    )
    override fun getUserByUsername(searchUsername: String): UserModel? {
        return userList.firstOrNull{user -> user.username == searchUsername}
    }

    override fun addUser(newUser: UserModel, onRegistrationError: (String) -> Unit) {
        if(userList.find { user -> (user.username == newUser.username) || (user.email == newUser.email) } == null){
            userList.add(newUser)
        }
        else{
            onRegistrationError("User already exists")
        }
    }
}