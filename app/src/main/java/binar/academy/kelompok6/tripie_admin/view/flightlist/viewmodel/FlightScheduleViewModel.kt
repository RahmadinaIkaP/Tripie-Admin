package binar.academy.kelompok6.tripie_admin.view.flightlist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import binar.academy.kelompok6.tripie_admin.data.network.ApiEndpoint
import binar.academy.kelompok6.tripie_admin.data.network.ApiResponse
import binar.academy.kelompok6.tripie_admin.model.request.AddEditScheduleRequest
import binar.academy.kelompok6.tripie_admin.model.response.AddScheduleResponse
import binar.academy.kelompok6.tripie_admin.model.response.DeleteResponse
import binar.academy.kelompok6.tripie_admin.model.response.EditScheduleResponse
import binar.academy.kelompok6.tripie_admin.model.response.GetScheduleResponse
import binar.academy.kelompok6.tripie_admin.utils.EspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class FlightScheduleViewModel @Inject constructor(private val api : ApiEndpoint) : ViewModel() {

    private val allScheduleLiveData : MutableLiveData<ApiResponse<GetScheduleResponse>> = MutableLiveData()
    private val addScheduleLiveData : MutableLiveData<ApiResponse<AddScheduleResponse>> = MutableLiveData()
    private val editScheduleLiveData : MutableLiveData<ApiResponse<EditScheduleResponse>> = MutableLiveData()
    private val deleteScheduleLiveData : MutableLiveData<ApiResponse<DeleteResponse>> = MutableLiveData()

    fun getAllScheduleObserver() : MutableLiveData<ApiResponse<GetScheduleResponse>> = allScheduleLiveData
    fun addScheduleObserver() : MutableLiveData<ApiResponse<AddScheduleResponse>> = addScheduleLiveData
    fun editScheduleObserver() : MutableLiveData<ApiResponse<EditScheduleResponse>> = editScheduleLiveData
    fun deleteScheduleObserver() : MutableLiveData<ApiResponse<DeleteResponse>> = deleteScheduleLiveData

    fun getAllFlightSchedule(token : String){
        allScheduleLiveData.postValue(ApiResponse.Loading())
        EspressoIdlingResource.increment()

        api.getAllSchedule(token)
            .enqueue(object : Callback<GetScheduleResponse>{
                override fun onResponse(
                    call: Call<GetScheduleResponse>,
                    response: Response<GetScheduleResponse>
                ) {
                    if (response.isSuccessful){
                        val data = response.body()

                        data?.let {
                            allScheduleLiveData.postValue(ApiResponse.Success(it))
                        }
                    }else{
                        try {
                            response.errorBody()?.let {
                                val jsonObject = JSONObject(it.string()).getString("message")
                                allScheduleLiveData.postValue(ApiResponse.Error(jsonObject))
                            }
                        } catch (e: Exception) {
                            allScheduleLiveData.postValue(ApiResponse.Error("${e.message}"))
                        }
                    }

                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<GetScheduleResponse>, t: Throwable) {
                    allScheduleLiveData.postValue(ApiResponse.Error("${t.message}"))
                }

            })
    }

    fun addFlightSchedule(token: String, addEditScheduleRequest: AddEditScheduleRequest){
        addScheduleLiveData.postValue(ApiResponse.Loading())
        EspressoIdlingResource.increment()

        api.addSchedule(token, addEditScheduleRequest)
            .enqueue(object :  Callback<AddScheduleResponse> {
                override fun onResponse(
                    call: Call<AddScheduleResponse>,
                    response: Response<AddScheduleResponse>
                ) {
                    if (response.isSuccessful){
                        val data = response.body()

                        data?.let {
                            addScheduleLiveData.postValue(ApiResponse.Success(it))
                        }
                    }else{
                        try {
                            response.errorBody()?.let {
                                val jsonObject = JSONObject(it.string()).getString("message")
                                addScheduleLiveData.postValue(ApiResponse.Error(jsonObject))
                            }
                        } catch (e: Exception) {
                            addScheduleLiveData.postValue(ApiResponse.Error("${e.message}"))
                        }
                    }

                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<AddScheduleResponse>, t: Throwable) {
                    addScheduleLiveData.postValue(ApiResponse.Error("${t.message}"))
                }

            })
    }

    fun editFlightSchedule(token: String, id: Int, addEditScheduleRequest: AddEditScheduleRequest){
        editScheduleLiveData.postValue(ApiResponse.Loading())
        EspressoIdlingResource.increment()

        api.editSchedule(token, id, addEditScheduleRequest)
            .enqueue(object : Callback<EditScheduleResponse>{
                override fun onResponse(
                    call: Call<EditScheduleResponse>,
                    response: Response<EditScheduleResponse>
                ) {
                    if (response.isSuccessful){
                        val data = response.body()

                        data?.let {
                            editScheduleLiveData.postValue(ApiResponse.Success(it))
                        }
                    }else{
                        try {
                            response.errorBody()?.let {
                                val jsonObject = JSONObject(it.string()).getString("message")
                                editScheduleLiveData.postValue(ApiResponse.Error(jsonObject))
                            }
                        } catch (e: Exception) {
                            editScheduleLiveData.postValue(ApiResponse.Error("${e.message}"))
                        }
                    }

                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<EditScheduleResponse>, t: Throwable) {
                    editScheduleLiveData.postValue(ApiResponse.Error("${t.message}"))
                }

            })
    }

    fun deleteFlightSchedule(token: String, id: Int){
        api.deleteSchedule(token, id)
            .enqueue(object : Callback<DeleteResponse>{
                override fun onResponse(
                    call: Call<DeleteResponse>,
                    response: Response<DeleteResponse>
                ) {
                    if (response.isSuccessful){
                        val data = response.body()

                        data?.let {
                            deleteScheduleLiveData.postValue(ApiResponse.Success(it))
                        }
                    }else{
                        try {
                            response.errorBody()?.let {
                                val jsonObject = JSONObject(it.string()).getString("message")
                                deleteScheduleLiveData.postValue(ApiResponse.Error(jsonObject))
                            }
                        } catch (e: Exception) {
                            deleteScheduleLiveData.postValue(ApiResponse.Error("${e.message}"))
                        }
                    }

                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<DeleteResponse>, t: Throwable) {
                    deleteScheduleLiveData.postValue(ApiResponse.Error("${t.message}"))
                }

            })
    }
}