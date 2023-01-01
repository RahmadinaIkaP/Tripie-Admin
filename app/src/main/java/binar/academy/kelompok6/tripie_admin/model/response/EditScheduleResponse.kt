@file:Suppress("unused")

package binar.academy.kelompok6.tripie_admin.model.response


import com.google.gson.annotations.SerializedName

@Suppress("unused")
data class EditScheduleResponse(
    @SerializedName("data")
    val data : List<Int>
)