package binar.academy.kelompok6.tripie_admin.model.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Jadwal(
    @SerializedName("id")
    val id: Int,
    @SerializedName("Origin_Airport")
    val originAirport: String,
    @SerializedName("Destination_Airport")
    val destinationAirport: String,
    @SerializedName("Arrival_Hour")
    val arrivalHour: String,
    @SerializedName("Departure_Hour")
    val departureHour: String,
    @SerializedName("flight_Date")
    val flightDate: String,
    @SerializedName("Airline_Name")
    val airlineName: String,
    @SerializedName("Plane_class")
    val planeClass: String,
    @SerializedName("Price")
    val price: Int,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String
) : Parcelable