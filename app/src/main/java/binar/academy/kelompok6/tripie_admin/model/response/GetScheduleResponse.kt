package binar.academy.kelompok6.tripie_admin.model.response


import com.google.gson.annotations.SerializedName

data class GetScheduleResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("data")
    val data: DataGetSchedule
)