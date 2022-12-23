package binar.academy.kelompok6.tripie_admin.model.response


import com.google.gson.annotations.SerializedName

data class DataHistory(
    @SerializedName("Booking")
    val booking: List<Booking>
)