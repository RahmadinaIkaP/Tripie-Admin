@file:Suppress("unused", "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate",
    "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate",
    "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate",
    "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate", "unused", "unused"
)

package binar.academy.kelompok6.tripie_admin.dashboard.viewmodel

import binar.academy.kelompok6.tripie_admin.data.network.ApiEndpoint
import binar.academy.kelompok6.tripie_admin.model.response.AirportResponse
import binar.academy.kelompok6.tripie_admin.model.response.GetUserResponse
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Call

@Suppress("MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate",
    "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate",
    "MemberVisibilityCanBePrivate", "MemberVisibilityCanBePrivate", "unused"
)
class AdminDashboardViewModelTest {
    lateinit var service : ApiEndpoint

    @Before
    fun setUp() {
        service = mockk()
    }

    @Test
    fun getDataAirPort() : Unit = runBlocking {
        val response = mockk<Call<AirportResponse>>()
        every {
            runBlocking {
                service.getAirport()
            }
        } returns response

        val result = service.getAirport()

        verify {
            runBlocking {
                service.getAirport()
            }
        }
        assertEquals(result, response)
    }

    @Test
    fun getDataUser() : Unit = runBlocking {
        val token = "token"
        val response = mockk<Call<GetUserResponse>>()
        every {
            runBlocking {
                service.getUser(token)
            }
        } returns response
        val result = service.getUser(token)

        verify {
            runBlocking {
                service.getUser(token)
            }
        }
        assertEquals(result, response)
    }
}