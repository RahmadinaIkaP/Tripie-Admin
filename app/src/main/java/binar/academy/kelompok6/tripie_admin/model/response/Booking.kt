package binar.academy.kelompok6.tripie_admin.model.response


import com.google.gson.annotations.SerializedName

data class Booking(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("Destination_Airport")
    val destinationAirport: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("Origin_Airport")
    val originAirport: String,
    @SerializedName("Plane_Class")
    val planeClass: String,
    @SerializedName("schedule_id")
    val scheduleId: Int,
    @SerializedName("Ticket_Date")
    val ticketDate: String,
    @SerializedName("Total_Passenger")
    val totalPassenger: Int,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: Int
)