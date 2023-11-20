package com.intersoft.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers

import retrofit2.http.POST
import retrofit2.http.Path


interface ServerService {
    @Headers("Content-Type: application/json")
    @POST("api/v1/register")
    fun createUser(@Body user: String): Call<String>

    @Headers("Content-Type: application/json")
    @POST("api/v1/users/{id}/edit")
    fun editUser(
        @Path("id") id: String,
        @Body user: String,
        @Header("Authorization") authToken: String ) : Call<String>
}