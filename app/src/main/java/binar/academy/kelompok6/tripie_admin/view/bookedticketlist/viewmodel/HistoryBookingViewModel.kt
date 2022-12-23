package binar.academy.kelompok6.tripie_admin.view.bookedticketlist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import binar.academy.kelompok6.tripie_admin.data.network.ApiEndpoint
import binar.academy.kelompok6.tripie_admin.data.network.ApiResponse
import binar.academy.kelompok6.tripie_admin.model.response.HistoryResponse
import binar.academy.kelompok6.tripie_admin.utils.EspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HistoryBookingViewModel @Inject constructor(private val api : ApiEndpoint) : ViewModel() {

    private var historyBookingLiveData : MutableLiveData<ApiResponse<HistoryResponse>> = MutableLiveData()

    fun getHistoryBookingObserver() : MutableLiveData<ApiResponse<HistoryResponse>> = historyBookingLiveData

    fun getHistoryBooking(){
        historyBookingLiveData.postValue(ApiResponse.Loading())
        EspressoIdlingResource.increment()

        api.getHistoryBooking().enqueue(object : Callback<HistoryResponse>{
            override fun onResponse(
                call: Call<HistoryResponse>,
                response: Response<HistoryResponse>
            ) {
                if(response.isSuccessful){
                    val data = response.body()

                    data?.let {
                        historyBookingLiveData.postValue(ApiResponse.Success(it))
                    }
                }else{
                    try {
                        response.errorBody()?.let {
                            val jsonObject = JSONObject(it.string()).getString("message")
                            historyBookingLiveData.postValue(ApiResponse.Error(jsonObject))
                        }
                    } catch (e: Exception) {
                        historyBookingLiveData.postValue(ApiResponse.Error("${e.message}"))
                    }
                }

                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {
                historyBookingLiveData.postValue(ApiResponse.Error("${t.message}"))
            }

        })
    }
}