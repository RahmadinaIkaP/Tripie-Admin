@file:Suppress("unused", "unused", "unused", "unused", "unused")

package binar.academy.kelompok6.tripie_admin.model.request


import com.google.gson.annotations.SerializedName

@Suppress("unused", "unused", "unused", "unused", "unused")
data class LoginRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)