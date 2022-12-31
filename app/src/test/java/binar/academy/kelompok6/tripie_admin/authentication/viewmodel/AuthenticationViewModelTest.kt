package binar.academy.kelompok6.tripie_admin.authentication.viewmodel

import binar.academy.kelompok6.tripie_admin.data.network.ApiEndpoint
import binar.academy.kelompok6.tripie_admin.model.request.LoginRequest
import binar.academy.kelompok6.tripie_admin.model.response.LoginResponse
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Call

class AuthenticationViewModelTest {

    lateinit var service : ApiEndpoint

    @Before
    fun setUp() {
        service = mockk()
    }

    @Test
    fun loginUserTest() : Unit = runBlocking {
        val response = mockk<Call<LoginResponse>>()
        every {
            runBlocking {
                service.login(LoginRequest("admin@tripie.com", "admin"))
            }
        } returns response

        val result = service.login(LoginRequest("admin@tripie.com", "admin"))

        verify {
            runBlocking {
                service.login(LoginRequest("admin@tripie.com", "admin"))
            }
        }
        assertEquals(result, response)
    }
}