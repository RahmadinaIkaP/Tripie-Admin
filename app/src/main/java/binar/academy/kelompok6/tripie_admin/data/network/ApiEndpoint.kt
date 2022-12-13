package binar.academy.kelompok6.tripie_admin.data.network

import binar.academy.kelompok6.tripie_admin.model.request.LoginRequest
import binar.academy.kelompok6.tripie_admin.model.response.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiEndpoint {

    @POST("login")
    fun login(@Body loginRequest: LoginRequest) : Call<LoginResponse>

}