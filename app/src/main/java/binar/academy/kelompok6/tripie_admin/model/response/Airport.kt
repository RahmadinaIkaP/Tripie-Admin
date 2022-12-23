package binar.academy.kelompok6.tripie_admin.model.response


import com.google.gson.annotations.SerializedName

data class Airport(
    @SerializedName("Airport_Code")
    val airportCode: String,
    @SerializedName("Airport_Name")
    val airportName: String,
    @SerializedName("City")
    val city: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("Description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("updatedAt")
    val updatedAt: String
)