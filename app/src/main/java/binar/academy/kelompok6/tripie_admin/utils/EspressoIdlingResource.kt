package binar.academy.kelompok6.tripie_admin.utils

import android.support.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {
    private const val RESOURCE = "GLOBAL"

    private val idlingResource = CountingIdlingResource(RESOURCE)


    fun increment() {
        idlingResource.increment()
    }

    fun decrement() {
        idlingResource.decrement()
    }
}