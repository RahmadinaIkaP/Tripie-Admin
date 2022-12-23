package binar.academy.kelompok6.tripie_admin.view.authentication.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import binar.academy.kelompok6.tripie_admin.data.network.ApiEndpoint
import binar.academy.kelompok6.tripie_admin.data.network.ApiResponse
import binar.academy.kelompok6.tripie_admin.model.request.LoginRequest
import binar.academy.kelompok6.tripie_admin.model.response.LoginResponse
import binar.academy.kelompok6.tripie_admin.utils.EspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val api : ApiEndpoint) : ViewModel() {

    private val loginLiveData : MutableLiveData<ApiResponse<LoginResponse>> = MutableLiveData()

    fun loginObserver() : MutableLiveData<ApiResponse<LoginResponse>> = loginLiveData

    fun login(loginRequest: LoginRequest){
        loginLiveData.postValue(ApiResponse.Loading()) // post response loading
        EspressoIdlingResource.increment()

        api.login(loginRequest)
            .enqueue(object : Callback<LoginResponse>{
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful){
                        // untuk menampung response code
                        val data = response.body()

                        // checking null in var dataHistory
                        data?.let {
                            loginLiveData.postValue(ApiResponse.Success(it)) // post dataHistory response ke sealed class success
                            Log.d("Success: ", ApiResponse.Success(it).toString())
                        }
                    }else{
                        try {
                            response.errorBody()?.let {
                                val jsonObject = JSONObject(it.string()).getString("message") // get message response from JSON format
                                loginLiveData.postValue(ApiResponse.Error(jsonObject)) // post dataHistory response ke sealed class error
                                Log.d("Error: ", ApiResponse.Error(jsonObject).toString())
                            }
                        } catch (e: Exception) {
                            loginLiveData.postValue(ApiResponse.Error("${e.message}"))
                            Log.d("Error: ", ApiResponse.Error("${e.message}").toString())
                        }
                    }

                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    loginLiveData.postValue(ApiResponse.Error(t.message.toString()))
                    Log.d("Error: ", ApiResponse.Error(t.message.toString()).toString())
                }

            })
    }
}