package com.intersoft.network.models.responses

data class RegisterBody(
    val user : UserData
)

data class UserData(
    val username: String,
    val email: String,
    val password: String,
    val location: String
)