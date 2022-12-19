package binar.academy.kelompok6.tripie_admin.view.bookedticketlist.viewmodel

import androidx.lifecycle.ViewModel
import binar.academy.kelompok6.tripie_admin.data.network.ApiEndpoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryBookingViewModel @Inject constructor(private val api : ApiEndpoint) : ViewModel() {

}