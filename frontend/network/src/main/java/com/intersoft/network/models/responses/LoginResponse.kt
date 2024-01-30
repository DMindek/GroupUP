package com.intersoft.network.models.responses

data class LoginResponse(
    val token : String,
    val email: String,
    val id: Int,
    val username: String,
    val location: String,
    val location_name : String,
)
