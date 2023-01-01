@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package binar.academy.kelompok6.tripie_admin.bookticket.viewmodel

import binar.academy.kelompok6.tripie_admin.data.network.ApiEndpoint
import binar.academy.kelompok6.tripie_admin.model.response.GetHistoryResponse
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Call

@Suppress("MemberVisibilityCanBePrivate", "unused")
class HystoryBookTicketViewModelTest {
    lateinit var service: ApiEndpoint

    @Before
    fun setUp() {
        service = mockk()
    }

    @Test
    fun getHistoryBookTicketTest()  : Unit = runBlocking {
        val response = mockk<Call<GetHistoryResponse>>()
        every {
            runBlocking {
                service.getHistoryBooking()
            }
        } returns response

        val result = service.getHistoryBooking()

        verify {
            runBlocking {
                service.getHistoryBooking()
            }
        }
        assertEquals(result, response)
    }
}