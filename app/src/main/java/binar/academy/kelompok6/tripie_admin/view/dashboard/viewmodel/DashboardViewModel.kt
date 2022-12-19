package binar.academy.kelompok6.tripie_admin.view.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import binar.academy.kelompok6.tripie_admin.data.network.ApiEndpoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val api : ApiEndpoint) : ViewModel(){

}