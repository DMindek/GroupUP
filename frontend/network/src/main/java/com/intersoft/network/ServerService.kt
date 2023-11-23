package com.intersoft.network

import com.intersoft.network.models.responses.EditBody
import com.intersoft.network.models.responses.LoginBody
import com.intersoft.network.models.responses.LoginResponse
import com.intersoft.network.models.responses.RegisterBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers

import retrofit2.http.POST
import retrofit2.http.Path

interface ServerService {
    @Headers("Content-Type: application/json")
    @POST("api/v1/register")
    fun createUser(@Body user: RegisterBody): Call<ResponseBody>

    @Headers("Content-Type: application/json")
    @POST("api/v1/login")
    fun logIn(@Body body: LoginBody): Call<LoginResponse>

    @Headers("Content-Type: application/json")
    @POST("api/v1/users/{id}/edit")
    fun editUser(
        @Path("id") id: Int,
        @Body user: EditBody,
        @Header("Authorization") authToken: String ) : Call<EditBody>
}