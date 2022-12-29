package binar.academy.kelompok6.tripie_admin.model.response


import com.google.gson.annotations.SerializedName

data class GetHistoryResponse(
    @SerializedName("data")
    val data: DataHistory
)