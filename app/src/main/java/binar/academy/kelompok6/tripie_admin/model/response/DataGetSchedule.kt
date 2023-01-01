@file:Suppress("unused", "unused", "unused", "unused")

package binar.academy.kelompok6.tripie_admin.model.response


import com.google.gson.annotations.SerializedName

@Suppress("unused", "unused")
data class DataGetSchedule(
    @SerializedName("Jadwal")
    val jadwal: List<Jadwal>
)