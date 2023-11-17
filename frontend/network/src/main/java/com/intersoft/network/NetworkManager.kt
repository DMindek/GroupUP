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

    fun logIn(data: String, onLoginSuccess: (String) -> Unit, onLoginFail: (String?) -> Unit){
        serverService.logIn(data).enqueue(ResponseHandler(onLoginSuccess, onLoginFail))
    }

    private class ResponseHandler(onSuccess: (String) -> Unit, onFail: (String?) -> Unit): retrofit2.Callback<String>{
        val fail: (String?) -> Unit = onFail
        val success: (String) -> Unit = onSuccess

        override fun onResponse(call: Call<String>?, response: Response<String>?) {
            if(response == null) fail("no response from server")
            else if (response.code() != 201) {
                if (response.code() == 422)
                    response.errorBody().string()
                else fail("Unknown error occurred")
            }
            else success(response.body().toString())
        }

        override fun onFailure(call: Call<String>?, t: Throwable?) {
            fail("Could not reach server")
        }

    }
}