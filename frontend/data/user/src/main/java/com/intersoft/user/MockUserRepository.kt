package com.intersoft.user

class MockUserRepository : UserRepository {
    private val userList = mutableListOf<UserModel>(
        UserModel("test", "mail@mail", "abc", "here"),
        UserModel("me", "mail@email", "123", "there"),
    )
    override fun getUserByUsername(username: String): UserModel? {
        return userList.firstOrNull{user -> user.username == username}
    }

    override fun addUser(user: UserModel) {
        userList.add(user)
    }
}