package binar.academy.kelompok6.tripie_admin.view.bookedticketlist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import binar.academy.kelompok6.tripie_admin.data.datastore.SharedPref
import binar.academy.kelompok6.tripie_admin.data.network.ApiResponse
import binar.academy.kelompok6.tripie_admin.databinding.FragmentBookedTicketBinding
import binar.academy.kelompok6.tripie_admin.view.MainActivity
import binar.academy.kelompok6.tripie_admin.view.authentication.LoginActivity
import binar.academy.kelompok6.tripie_admin.view.bookedticketlist.adapter.BookedTicketAdapter
import binar.academy.kelompok6.tripie_admin.view.bookedticketlist.viewmodel.HistoryBookingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookedTicketFragment : Fragment() {
    private var _binding : FragmentBookedTicketBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPref: SharedPref
    private lateinit var adapter : BookedTicketAdapter
    private val historyBookingViewModel : HistoryBookingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookedTicketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).supportActionBar?.title = "Daftar Pesanan"

        sharedPref = SharedPref(requireContext())

        setBookedHistory()
    }

    private fun setBookedHistory() {
        historyBookingViewModel.getHistoryBooking()

        historyBookingViewModel.getHistoryBookingObserver().observe(viewLifecycleOwner){ response ->
            when(response){
                is ApiResponse.Loading -> {
                    showLoading()
                    Log.d("Loading: ", response.toString())
                }
                is ApiResponse.Success -> {
                    stopLoading()
                    Log.d("Success: ", response.toString())
                }
                is ApiResponse.Error -> {
                    stopLoading()
                    Toast.makeText(requireContext(), response.msg, Toast.LENGTH_SHORT).show()
                    Log.d("Error: ", response.toString())

                }
            }
        }
    }

    private fun stopLoading() {
        binding.progressBarHistory.visibility = View.GONE
    }

    private fun showLoading() {
        binding.progressBarHistory.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}