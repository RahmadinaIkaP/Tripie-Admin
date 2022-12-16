package binar.academy.kelompok6.tripie_admin.model.response


import com.google.gson.annotations.SerializedName

data class DataAddSchedule(
    @SerializedName("Airline_Name")
    val airlineName: String,
    @SerializedName("Arrival_Hour")
    val arrivalHour: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("Departure_Hour")
    val departureHour: String,
    @SerializedName("Destination_Airport")
    val destinationAirport: String,
    @SerializedName("flight_Date")
    val flightDate: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("Origin_Airport")
    val originAirport: String,
    @SerializedName("Plane_class")
    val planeClass: String,
    @SerializedName("Price")
    val price: Int,
    @SerializedName("updatedAt")
    val updatedAt: String
)