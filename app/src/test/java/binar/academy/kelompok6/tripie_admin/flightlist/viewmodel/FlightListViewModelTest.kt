@file:Suppress("MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate", "unused")

package binar.academy.kelompok6.tripie_admin.flightlist.viewmodel

import binar.academy.kelompok6.tripie_admin.data.network.ApiEndpoint
import binar.academy.kelompok6.tripie_admin.model.request.AddEditScheduleRequest
import binar.academy.kelompok6.tripie_admin.model.response.AddScheduleResponse
import binar.academy.kelompok6.tripie_admin.model.response.DeleteResponse
import binar.academy.kelompok6.tripie_admin.model.response.EditScheduleResponse
import binar.academy.kelompok6.tripie_admin.model.response.GetScheduleResponse
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Call

@Suppress("MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate", "unused")
class FlightListViewModelTest {
    lateinit var service : ApiEndpoint

    @Before
    fun setUp() {
        service = mockk()
    }

    @Test
    fun getAllFlightList() : Unit = runBlocking{
        val token = "token"
        val response = mockk<Call<GetScheduleResponse>>()
        every {
            runBlocking {
                service.getAllSchedule(token)
            }
        } returns response

        val result = service.getAllSchedule(token)

        verify {
            runBlocking {
                service.getAllSchedule(token)
            }
        }
        assertEquals(result, response)
    }

    @Test
    fun addFlightSchedule() : Unit = runBlocking {
        val token = "token"
        val response = mockk<Call<AddScheduleResponse>>()
        every {
            runBlocking {
                service.addSchedule(token, AddEditScheduleRequest(
                    originCode = "CGK",
                    originName = "Soekarno Hatta",
                    originCity = "Jakarta",
                    destinationCode = "DPS",
                    destinationName = "Ngurah Rai",
                    destinationCity = "Denpasar",
                    planeClass = "Economy",
                    flightDate = "2021-06-13",
                    airlineName = "Garuda Indonesia",
                    departureHour = "12:00",
                    arrivalHour = "13:00",
                    price = 1000000
                ))
            }
        } returns response

        val result = service.addSchedule(token, AddEditScheduleRequest(
            originCode = "CGK",
            originName = "Soekarno Hatta",
            originCity = "Jakarta",
            destinationCode = "DPS",
            destinationName = "Ngurah Rai",
            destinationCity = "Denpasar",
            planeClass = "Economy",
            flightDate = "2021-06-13",
            airlineName = "Garuda Indonesia",
            departureHour = "12:00",
            arrivalHour = "13:00",
            price = 1000000
        ))

        verify {
            runBlocking {
                service.addSchedule(token, AddEditScheduleRequest(
                    originCode = "CGK",
                    originName = "Soekarno Hatta",
                    originCity = "Jakarta",
                    destinationCode = "DPS",
                    destinationName = "Ngurah Rai",
                    destinationCity = "Denpasar",
                    planeClass = "Economy",
                    flightDate = "2021-06-13",
                    airlineName = "Garuda Indonesia",
                    departureHour = "12:00",
                    arrivalHour = "13:00",
                    price = 1000000
                ))
            }
        }
        assertEquals(result, response)
    }

    @Test
    fun editFlightSchedule() : Unit = runBlocking {
        val token = "token"
        val id = 1
        val response = mockk<Call<EditScheduleResponse>>()
        every {
            runBlocking {
                service.editSchedule(
                    token, id, AddEditScheduleRequest(
                        originCode = "CGK",
                        originName = "Soekarno Hatta",
                        originCity = "Jakarta",
                        destinationCode = "DPS",
                        destinationName = "Ngurah Rai",
                        destinationCity = "Denpasar",
                        planeClass = "Economy",
                        flightDate = "2021-06-13",
                        airlineName = "Garuda Indonesia",
                        departureHour = "12:00",
                        arrivalHour = "13:00",
                        price = 1000000
                    )
                )
            }
        } returns response

        val result = service.editSchedule(
            token, id, AddEditScheduleRequest(
                originCode = "CGK",
                originName = "Soekarno Hatta",
                originCity = "Jakarta",
                destinationCode = "DPS",
                destinationName = "Ngurah Rai",
                destinationCity = "Denpasar",
                planeClass = "Economy",
                flightDate = "2021-06-13",
                airlineName = "Garuda Indonesia",
                departureHour = "12:00",
                arrivalHour = "13:00",
                price = 1000000
            )
        )

        verify {
            runBlocking {
                service.editSchedule(
                    token, id, AddEditScheduleRequest(
                        originCode = "CGK",
                        originName = "Soekarno Hatta",
                        originCity = "Jakarta",
                        destinationCode = "DPS",
                        destinationName = "Ngurah Rai",
                        destinationCity = "Denpasar",
                        planeClass = "Economy",
                        flightDate = "2021-06-13",
                        airlineName = "Garuda Indonesia",
                        departureHour = "12:00",
                        arrivalHour = "13:00",
                        price = 1000000
                    )
                )
            }
        }
        assertEquals(result, response)
    }

    @Test
    fun deleteFlightSchedule() : Unit = runBlocking {
        val token = "token"
        val id = 1
        val response = mockk<Call<DeleteResponse>>()
        every {
            runBlocking {
                service.deleteSchedule(token, id)
            }
        } returns response
        val result = service.deleteSchedule(token, id)

        verify {
            runBlocking {
                service.deleteSchedule(token, id)
            }
        }
        assertEquals(result, response)
    }
}