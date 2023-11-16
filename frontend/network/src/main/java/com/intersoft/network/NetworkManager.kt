package com.intersoft.network

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {
    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://localhost:3000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val serverService: ServerService = retrofit.create(ServerService::class.java)

    fun registerUser(user: String): String?{
        val res: Response<String>

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

    fun logIn(password: String, onLoginFail: (String?) -> Unit){
        serverService.logIn(password).enqueue(ResponseHandler(onLoginFail))
    }

    private class ResponseHandler(callback: (String?) -> Unit): retrofit2.Callback<String>{
        val onResult: (String?) -> Unit = callback
        override fun onResponse(call: Call<String>?, response: Response<String>?) {
            if(response == null) onResult("no response from server")
            else if (response.code() != 201) {
                if (response.code() == 422)
                    response.errorBody().string()
                else onResult("Unknown error occurred")
            }
            else onResult(null)
        }

        override fun onFailure(call: Call<String>?, t: Throwable?) {
            onResult("Could not reach server")
        }

    }
}