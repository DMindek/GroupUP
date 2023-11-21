package com.intersoft.network

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {
    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val serverService: ServerService = retrofit.create(ServerService::class.java)

    fun registerUser(user: String): String?{
        val res: Response<String>

        val res = serverService.createUser(user)
        Log.d("NetworkManager", "Request ${res.request().body()}")

        res.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                Log.d("NetworkManager", "Response: ${response?.body()}")
                if(response?.code() != 201){
                    if(response?.code() == 422)
                        requestListener.onError(response.errorBody().string())
                    else requestListener.onError("Unknown error occurred")
                } else requestListener.onSuccess(response.body()!!)
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                Log.d("NetworkManager", "Error: ${t?.message}")
                requestListener.onError("Broken connection")
            }
        })
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