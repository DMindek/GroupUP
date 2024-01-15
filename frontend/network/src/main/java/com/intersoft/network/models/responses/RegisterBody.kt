package com.intersoft.network.models.responses

data class RegisterBody(
    val user : UserData
)

data class UserData(
    val username: String,
    val email: String,
    val location: String,
    val password: String? = null,
    val id: Int? = null,
)
