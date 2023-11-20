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
            return "Could not reach server"
        }

        return if(res.code() != 201){
            if(res.code() == 422)
                res.errorBody().string()
            else "Unknown error occurred"
        } else null
    }

    fun editUser(user: String): String?{
        val res: retrofit2.Response<String>
        //TODO get id and authToken from somewhere
        val id = "0"
        val authToken = ""

        try{
            res = serverService.editUser( id,user, authToken).execute()
        }catch (e: Exception){
            return "Could not reach server"
        }

        return if(res.code() != 200){
            if(res.code() == 422)
                res.errorBody().string()
            else "Unknown error occurred"
        } else null
    }
}