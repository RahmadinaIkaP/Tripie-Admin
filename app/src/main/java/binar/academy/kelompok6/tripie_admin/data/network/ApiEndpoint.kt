package binar.academy.kelompok6.tripie_admin.data.network

import binar.academy.kelompok6.tripie_admin.model.request.AddEditScheduleRequest
import binar.academy.kelompok6.tripie_admin.model.request.LoginRequest
import binar.academy.kelompok6.tripie_admin.model.response.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiEndpoint {

    @POST("login")
    fun login(@Body loginRequest: LoginRequest) : Call<LoginResponse>

    @GET("schedule")
    fun getAllSchedule(
        @Header("Authorization")
        token : String
    ) : Call<GetScheduleResponse>

    @POST("add-schedule")
    fun addSchedule(
        @Header("Authorization") token: String,
        @Body addEditScheduleRequest: AddEditScheduleRequest
    ) : Call<AddScheduleResponse>

    @PUT("edit-schedule/{id}")
    fun editSchedule(
        @Header("Authorization") token: String,
        @Path("id") id : Int,
        @Body addEditScheduleRequest: AddEditScheduleRequest) : Call<EditScheduleResponse>

    @DELETE("delete-schedule/{id}")
    fun deleteSchedule(
        @Header("Authorization") token: String,
        @Path("id") id : Int
    ) : Call<DeleteResponse>

    @GET("history")
    fun getHistoryBooking() : Call<HistoryResponse>

    @GET("get-airport")
    fun getAirport() : Call<AirportResponse>
}