@file:Suppress("unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused",
    "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused"
)

package binar.academy.kelompok6.tripie_admin.model.response


import com.google.gson.annotations.SerializedName

@Suppress("unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused",
    "unused", "unused", "unused", "unused", "unused", "unused", "unused"
)
data class Schedules(
    @SerializedName("id")
    val id: Int,
    @SerializedName("origin_code")
    val originCode: String,
    @SerializedName("origin_name")
    val originName: String,
    @SerializedName("origin_city")
    val originCity: String,
    @SerializedName("destination_code")
    val destinationCode: String,
    @SerializedName("destination_name")
    val destinationName: String,
    @SerializedName("destination_city")
    val destinationCity: String,
    @SerializedName("plane_class")
    val planeClass: String,
    @SerializedName("flight_date")
    val flightDate: String,
    @SerializedName("airline_name")
    val airlineName: String,
    @SerializedName("departure_hour")
    val departureHour: String,
    @SerializedName("arrival_hour")
    val arrivalHour: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("capacity")
    val capacity: Int,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)