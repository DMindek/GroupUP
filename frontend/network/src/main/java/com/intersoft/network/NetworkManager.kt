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

    fun logInUser(data: String, onLoginSuccess: (String) -> Unit, onLoginFail: (String?) -> Unit){
        serverService.logIn(data).enqueue(ResponseHandler(200, 401, onLoginSuccess, onLoginFail))
    }

    private class ResponseHandler(val successCode: Int, val errorCode: Int,
                                  val onSuccess: (String) -> Unit,
                                  val onFail: (String?) -> Unit): retrofit2.Callback<String>{

        override fun onResponse(call: Call<String>?, response: Response<String>?) {
            if(response == null) onFail("no response from server")
            else if (response.code() != successCode) {
                if (response.code() == errorCode)
                    response.errorBody().string()
                else onFail("Unknown error occurred")
            }
            else onSuccess(response.body().toString())
        }

        override fun onFailure(call: Call<String>?, t: Throwable?) {
            onFail("Could not reach server")
        }

    }
}