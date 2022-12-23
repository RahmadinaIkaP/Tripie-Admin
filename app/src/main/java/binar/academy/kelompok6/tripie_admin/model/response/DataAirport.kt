package binar.academy.kelompok6.tripie_admin.model.response


import com.google.gson.annotations.SerializedName

data class DataAirport(
    @SerializedName("airport")
    val airport: List<Airport>
)