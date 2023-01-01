package binar.academy.kelompok6.tripie_admin.model.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Booking(
    @SerializedName("id")
    val id: Int,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("schedule_id")
    val scheduleId: Int,
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
    @SerializedName("total_passenger")
    val totalPassenger: Int,
    @SerializedName("flight_type")
    val flightType: String,
    @SerializedName("flight_date")
    val flightDate: String,
    @SerializedName("flight_back_date")
    val flightBackDate: String?,
    @SerializedName("airline_name")
    val airlineName: String,
    @SerializedName("departure_hour")
    val departureHour: String,
    @SerializedName("arrival_hour")
    val arrivalHour: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("passenger_name")
    val passengerName: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("users")
    val users: Users,
    @SerializedName("schedules")
    val schedules: Schedules
) : Parcelable