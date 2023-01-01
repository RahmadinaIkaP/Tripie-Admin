@file:Suppress("unused", "unused", "unused")

package binar.academy.kelompok6.tripie_admin.model.response


import com.google.gson.annotations.SerializedName

@Suppress("unused", "unused", "unused")
data class GetHistoryResponse(
    @SerializedName("data")
    val data: DataHistory
)