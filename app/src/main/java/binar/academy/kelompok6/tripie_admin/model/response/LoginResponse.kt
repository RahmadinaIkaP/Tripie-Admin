@file:Suppress("unused", "unused", "unused", "unused", "unused", "unused", "unused")

package binar.academy.kelompok6.tripie_admin.model.response


import com.google.gson.annotations.SerializedName

@Suppress("unused", "unused", "unused", "unused", "unused", "unused", "unused")
data class LoginResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("Name")
    val name: String,
    @SerializedName("Email")
    val email: String,
    @SerializedName("Role")
    val role: String,
    @SerializedName("Token")
    val token: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)