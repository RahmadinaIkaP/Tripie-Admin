package binar.academy.kelompok6.tripie_admin.model.response


import com.google.gson.annotations.SerializedName

data class DataGetSchedule(
    @SerializedName("Jadwal")
    val jadwal: List<Jadwal>
)