package binar.academy.kelompok6.tripie_admin.model.response


import com.google.gson.annotations.SerializedName

data class GetUserResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("data")
    val data : List<DataUser>,
    @SerializedName("meta")
    val meta: Int
)