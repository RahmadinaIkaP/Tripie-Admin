@file:Suppress("unused", "unused")

package binar.academy.kelompok6.tripie_admin.model.response


import com.google.gson.annotations.SerializedName

@Suppress("unused", "unused")
data class GetScheduleResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("data")
    val data: DataGetSchedule
)