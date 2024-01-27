package com.intersoft.network.models.responses

data class RegisterBody(
    val user : UserData
)

data class UserData(
    val username: String,
    val email: String,
    val location: String,
    val location_name : String,
    val password: String? = null,
    val id: Int? = null,
)
