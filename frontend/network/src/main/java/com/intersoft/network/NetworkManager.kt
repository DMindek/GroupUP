package com.intersoft.network

import android.util.Log
import com.intersoft.network.models.responses.EditBody
import com.intersoft.network.models.responses.EditEventBody
import com.intersoft.network.models.responses.EventBody
import com.intersoft.network.models.responses.EventDetails
import com.intersoft.network.models.responses.EventSuccessResponse
import com.intersoft.network.models.responses.LoginBody
import com.intersoft.network.models.responses.LoginResponse
import com.intersoft.network.models.responses.RegisterBody
import com.intersoft.network.models.responses.NewEventData
import com.intersoft.network.models.responses.StoredEventData
import com.intersoft.network.models.responses.UserData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {
    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val serverService: ServerService = retrofit.create(ServerService::class.java)

    fun registerUser(user: RegisterBody, requestListener: RequestListener){

        val res = serverService.createUser(user)
        Log.d("NetworkManager", "Request ${res.request().body()}")

        res.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d("NetworkManager", "Response: ${response?.body()}")
                if(response?.code() != 201){
                    if(response?.code() == 422)
                        requestListener.onError(response.errorBody()?.string())
                    else requestListener.onError("Unknown error occurred")
                } else requestListener.onSuccess(response.body()!!)
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                Log.d("NetworkManager", "Error: ${t?.message}")
                requestListener.onError("Broken connection")
            }
        })
    }

    fun logInUser(data: LoginBody, onLoginSuccess: (LoginResponse) -> Unit, onLoginFail: (String?) -> Unit){
        serverService.logIn(data).enqueue(ResponseHandler<LoginResponse>(200, 422, onLoginSuccess, onLoginFail))
    }

    fun editUser(data: EditBody,
                 userId: Int,
                 authToken: String,
                 onEditSuccess: (UserData) -> Unit,
                 onEditError: (String?) -> Unit ){

        serverService.editUser(userId, data, authToken).enqueue(
            ResponseHandler<UserData>(200, 422, onEditSuccess, onEditError)
        )
    }

    fun createEvent(eventData: EventBody, onCreateEventSuccess: (EventSuccessResponse) -> Unit, onCreateEventFail: (String?) -> Unit){
        val res = serverService.createEvent(eventData)
        res.enqueue(ResponseHandler<EventSuccessResponse>(successCode = 201, errorCode = 422, onCreateEventSuccess, onCreateEventFail))
    }

    fun getUserEvents(
        userId: Int,
        authtoken: String,
        onGetUserEventsSuccess: (List<NewEventData>) ->Unit,
        onGetUserEventsFail: (String?)-> Unit)
    {
        serverService.getUserEvents(userId, authtoken)
            .enqueue(
                ResponseHandler<List<NewEventData>>(
                    200,
                    422,
                    onGetUserEventsSuccess,
                    onGetUserEventsFail
                )
            )
    }

    fun getEvent(eventId: Int, onGetEventSuccess: (StoredEventData) -> Unit, onGetEventFail: (String?) -> Unit){
        val res = serverService.getEvent(eventId)
        res.enqueue(ResponseHandler<StoredEventData>(successCode = 200, errorCode = 404, onGetEventSuccess, onGetEventFail))
    }

    fun getUser(userId: Int, authToken: String, onGetUserSuccess: (UserData) -> Unit, onGetUserError: (String?) -> Unit) {
        val res = serverService.getUser(userId, authToken)
        res.enqueue(ResponseHandler<UserData>(successCode = 200, errorCode = 401, onGetUserSuccess,onGetUserError))
    }

    fun getUserByUsername(username: String, authToken: String,onGetUsersByUsernameSuccess: (List<UserData>) -> Unit, onGetUsersByUsernameError: (String?) -> Unit ){
        val res = serverService.getUsersByUsername(username, authToken)
        res.enqueue(ResponseHandler<List<UserData>>(successCode = 200, errorCode = 404, onGetUsersByUsernameSuccess, onGetUsersByUsernameError))
    }

    fun getAvailableEvents(
        authToken: String,
        onGetUserEventsSuccess: (List<NewEventData>) ->Unit,
        onGetUserEventsFail: (String?)-> Unit) {

        serverService.getAvailableEvents(authToken)
            .enqueue(
                ResponseHandler<List<NewEventData>>(
                    200,
                    422,
                    onGetUserEventsSuccess,
                    onGetUserEventsFail
                )
            )


    }

    fun getJoinedEvents(userId: Int, authToken: String, onGetUserEventsSuccess: (List<NewEventData>) -> Unit, onGetUserEventsFail: (String?)-> Unit) {
        serverService.getJoinedEvents(userId, authToken)
            .enqueue(
                ResponseHandler<List<NewEventData>>(
                    200,
                    422,
                    onGetUserEventsSuccess,
                    onGetUserEventsFail
                )
            )

    }

    fun editEvent(eventId:Int, eventData: EditEventBody, authToken: String, onEditEventSuccess: (EventDetails) ->Unit, onEditEventFail: (String?) -> Unit){
        val res =serverService.editEvent(eventId,eventData,authToken)
        res.enqueue(ResponseHandler<EventDetails>(successCode = 200, errorCode = 422, onEditEventSuccess, onEditEventFail))
    }

    private class ResponseHandler<T>(val successCode: Int, val errorCode: Int,
                                  val onSuccess : (T) -> Unit,
                                  val onFail: (String?) -> Unit): Callback<T>{

        override fun onResponse(call: Call<T>, response: Response<T>) {
            Log.d("NetworkManager", "Response: ${response?.message()}")
            if (response != null) {
                if (response.code() != successCode) {

                    if (response.code() == errorCode) {
                        val errorMessage = response.errorBody()?.string()
                        Log.d("NetworkManager", "Error: $errorMessage")
                        onFail(errorMessage)
                    }
                    else onFail("Unknown error occurred")
                } else {
                    Log.d("NetworkManager", "Success: ${response.body()}")
                    if(response.body() == null) onFail("no response from server")
                    else onSuccess(response.body()!!)
                }


            }
        }

        override fun onFailure(call: Call<T>?, t: Throwable?) {
            Log.d("NetworkManager", "Error: ${t?.message}")
            onFail("Could not reach server")
        }

    }



}