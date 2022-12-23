package binar.academy.kelompok6.tripie_admin.model.response


import com.google.gson.annotations.SerializedName

data class AirportResponse(
    @SerializedName("data")
    val dataAirport: DataAirport,
    @SerializedName("status")
    val status: String
)