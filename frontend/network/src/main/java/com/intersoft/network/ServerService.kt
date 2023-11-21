package com.intersoft.network

import com.intersoft.network.models.responses.LoginBody
import com.intersoft.network.models.responses.LoginResponse
import com.intersoft.network.models.responses.RegisterBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers

import retrofit2.http.POST



interface ServerService {
    @Headers("Content-Type: application/json")
    @POST("api/v1/register")
    fun createUser(@Body user: RegisterBody): Call<ResponseBody>

    @Headers("Content-Type: application/json")
    @POST("api/v1/login")
    fun logIn(@Body body: LoginBody): Call<LoginResponse>
}