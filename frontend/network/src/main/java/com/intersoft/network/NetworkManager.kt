package com.intersoft.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {
    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://localhost:3000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val serverService: ServerService = retrofit.create(ServerService::class.java)

    fun registerUser(user: String): String?{
        val res = serverService.createUser(user).execute()
        return if(res.code() != 201){
            res.errorBody().string()
        } else null
    }
}