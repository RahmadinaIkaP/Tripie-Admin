package binar.academy.kelompok6.tripie_admin.model.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Airport(
    @SerializedName("id")
    val id: Int,
    @SerializedName("Airport_Code")
    val airportCode: String,
    @SerializedName("Airport_Name")
    val airportName: String,
    @SerializedName("City")
    val city: String,
    @SerializedName("Foto")
    val foto: String,
    @SerializedName("Description")
    val description: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String
) : Parcelable