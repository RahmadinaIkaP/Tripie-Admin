package binar.academy.kelompok6.tripie_admin.view.authentication.viewmodel

import androidx.lifecycle.ViewModel
import binar.academy.kelompok6.tripie_admin.data.network.ApiEndpoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val api : ApiEndpoint) : ViewModel() {
}