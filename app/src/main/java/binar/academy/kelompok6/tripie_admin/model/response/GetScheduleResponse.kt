package binar.academy.kelompok6.tripie_admin.model.response


import com.google.gson.annotations.SerializedName

data class GetScheduleResponse(
    @SerializedName("dataGetSchedule")
    val dataGetSchedule : DataGetSchedule,
    @SerializedName("status")
    val status: String
)