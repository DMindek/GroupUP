package com.intersoft.network

import com.intersoft.network.models.responses.EditBody
import com.intersoft.network.models.responses.EditEventBody
import com.intersoft.network.models.responses.EventBody
import com.intersoft.network.models.responses.EventDetails
import com.intersoft.network.models.responses.NewEventData
import com.intersoft.network.models.responses.EventSuccessResponse
import com.intersoft.network.models.responses.LoginBody
import com.intersoft.network.models.responses.LoginResponse
import com.intersoft.network.models.responses.RegisterBody
import com.intersoft.network.models.responses.StoredEventData
import com.intersoft.network.models.responses.UserData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
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
        @Header("Authorization") authToken: String ) : Call<UserData>

    @Headers("Content-Type: application/json")
    @POST("/api/v1/events")
    fun createEvent(@Body event: EventBody): Call<EventSuccessResponse>

    @GET("/api/v1/events/{event_id}")
    fun getEvent(@Path("event_id") eventId: Int) : Call<StoredEventData>

    @Headers("Content-Type: application/json")
    @GET("/api/v1/users/{user_id}")
    fun getUser(@Path("user_id") userId: Int, @Header("Authorization") authToken: String): Call<UserData>

    @Headers("Content-Type: application/json")
    @GET("/api/v1/users/search/{username}")
    fun getUsersByUsername(@Path("username") username: String, @Header("Authorization") authToken: String): Call<UserData>

    @Headers("Content-Type: application/json")
    @GET("/api/v1/users/{user_id}/owned_events")
    fun getUserEvents(
        @Path("user_id") userId: Int,
        @Header("Authorization") authToken: String): Call<List<NewEventData>>

    @Headers("Content-Type: application/json")
    @GET("/api/v1/events")
    fun getAvailableEvents(
        @Header("Authorization") authToken: String): Call<List<NewEventData>>

    @Headers("Content-Type: application/json")
    @GET("/api/v1/users/{user_id}/joined_events") /*TODO TO BE DEFINED*/
    fun getJoinedEvents(
        @Path("userId") userId: Int,
        @Header("Authorization") authToken: String): Call<List<NewEventData>>

    @Headers("Content-Type: application/json")
    @POST("api/v1/events/{event_id}/edit")
    fun editEvent(
        @Path("event_id") id: Int,
        @Body event: EditEventBody,
        @Header("Authorization") authToken: String ) : Call<EventDetails>
}