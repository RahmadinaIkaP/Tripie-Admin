package binar.academy.kelompok6.tripie_admin.view.dashboard.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import binar.academy.kelompok6.tripie_admin.data.network.ApiEndpoint
import binar.academy.kelompok6.tripie_admin.data.network.ApiResponse
import binar.academy.kelompok6.tripie_admin.model.response.AirportResponse
import binar.academy.kelompok6.tripie_admin.model.response.GetUserResponse
import binar.academy.kelompok6.tripie_admin.utils.EspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val api : ApiEndpoint) : ViewModel(){

    private val allAirportLiveData : MutableLiveData<ApiResponse<AirportResponse>> = MutableLiveData()
    private val allUserLiveData : MutableLiveData<ApiResponse<GetUserResponse>> = MutableLiveData()

    fun getAllAirportObserver() : MutableLiveData<ApiResponse<AirportResponse>> = allAirportLiveData
    fun getAllUserObserver() : MutableLiveData<ApiResponse<GetUserResponse>> = allUserLiveData

    fun getAllAirport(){
        allAirportLiveData.postValue(ApiResponse.Loading())
        EspressoIdlingResource.increment()

        api.getAirport().enqueue(object : Callback<AirportResponse>{
            override fun onResponse(
                call: Call<AirportResponse>,
                response: Response<AirportResponse>
            ) {
                if (response.isSuccessful){
                    val data = response.body()

                    data?.let {
                        allAirportLiveData.postValue(ApiResponse.Success(it))
                    }
                }else{
                    try {
                        response.errorBody()?.let {
                            val jsonObject = JSONObject(it.string()).getString("message")
                            allAirportLiveData.postValue(ApiResponse.Error(jsonObject))
                        }
                    } catch (e: Exception) {
                        allAirportLiveData.postValue(ApiResponse.Error("${e.message}"))
                    }
                }

                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<AirportResponse>, t: Throwable) {
                allAirportLiveData.postValue(ApiResponse.Error("${t.message}"))
            }

        })
    }

    fun getAllUser(token : String){
        allUserLiveData.postValue(ApiResponse.Loading())
        EspressoIdlingResource.increment()

        api.getUser(token).enqueue(object : Callback<GetUserResponse>{
            override fun onResponse(
                call: Call<GetUserResponse>,
                response: Response<GetUserResponse>
            ) {
                if (response.isSuccessful){
                    val data = response.body()

                    data?.let {
                        allUserLiveData.postValue(ApiResponse.Success(it))
                    }
                }else{
                    try {
                        response.errorBody()?.let {
                            val jsonObject = JSONObject(it.string()).getString("message")
                            allUserLiveData.postValue(ApiResponse.Error(jsonObject))
                        }
                    } catch (e: Exception) {
                        allUserLiveData.postValue(ApiResponse.Error("${e.message}"))
                    }
                }

                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                allUserLiveData.postValue(ApiResponse.Error("${t.message}"))
            }

        })
    }
}