package com.intersoft.user

data class UserModel(
    var username: String,
    var email: String,
    var password: String,
    var location: String,
    var id: Int? = null,
    var token: String? = null
)
