package binar.academy.kelompok6.tripie_admin.model.response


import com.google.gson.annotations.SerializedName

data class AirportResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("data")
    val data: List<Airport>
)