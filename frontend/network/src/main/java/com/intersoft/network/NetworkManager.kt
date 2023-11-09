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
        val res: retrofit2.Response<String>

        try{
            res = serverService.createUser(user).execute()
        }catch (e: Exception){
            return "error: Could not reach server"
        }

        return if(res.code() != 201){
            if(res.code() == 422)
                res.errorBody().string()
            else "Unknown error occurred"
        } else null
    }
}