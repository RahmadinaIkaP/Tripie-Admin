@file:Suppress("unused", "unused")

package binar.academy.kelompok6.tripie_admin.model.response


import com.google.gson.annotations.SerializedName

@Suppress("unused", "unused")
data class DeleteResponse(
    @SerializedName("data")
    val data: Int,
    @SerializedName("status")
    val status: String
)