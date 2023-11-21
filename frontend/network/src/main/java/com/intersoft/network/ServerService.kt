package com.intersoft.network

import com.intersoft.network.models.responses.RegisterBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers

import retrofit2.http.POST



interface ServerService {
    @Headers("Content-Type: application/json")
    @POST("api/v1/register")
    fun createUser(@Body user: String): Call<String>

    @Headers("Content-Type: application/json")
    @POST("api/v1/login")
    fun logIn(@Body password: String): Call<String>
}