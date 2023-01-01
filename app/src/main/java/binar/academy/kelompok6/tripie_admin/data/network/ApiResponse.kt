@file:Suppress("unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused",
    "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused",
    "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused",
    "unused"
)

package binar.academy.kelompok6.tripie_admin.data.network

@Suppress("unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused",
    "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused",
    "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused",
    "unused", "unused", "unused", "unused", "unused"
)
sealed class ApiResponse<out R>{
    data class Success<out T>(val data: T? = null) : ApiResponse<T>()
    data class Loading(val nothing: Nothing? = null) : ApiResponse<Nothing>()
    data class Error(val msg: String?) : ApiResponse<Nothing>()
}
