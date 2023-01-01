@file:Suppress("unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused",
    "unused"
)

package binar.academy.kelompok6.tripie_admin.view.historybookedticket

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import binar.academy.kelompok6.tripie_admin.data.datastore.SharedPref
import binar.academy.kelompok6.tripie_admin.data.network.ApiResponse
import binar.academy.kelompok6.tripie_admin.databinding.FragmentBookedTicketBinding
import binar.academy.kelompok6.tripie_admin.model.response.Booking
import binar.academy.kelompok6.tripie_admin.view.MainActivity
import binar.academy.kelompok6.tripie_admin.view.historybookedticket.adapter.BookedTicketAdapter
import binar.academy.kelompok6.tripie_admin.view.historybookedticket.viewmodel.HistoryBookingViewModel
import dagger.hilt.android.AndroidEntryPoint

@Suppress("SENSELESS_COMPARISON", "unused", "unused", "unused", "unused", "unused", "unused",
    "unused", "unused", "unused"
)
@AndroidEntryPoint
class BookedTicketFragment : Fragment(), BookedTicketAdapter.BookedTicketInterface {
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

        (activity as MainActivity).supportActionBar?.title = "Daftar Booking Tiket"

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
                    response.data?.let {
                        val filteredHistory = response.data.data.booking.filter { booking -> booking.schedules != null  }
                        setDataRv(filteredHistory)
                    }
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

    private fun setDataRv(booking: List<Booking>) {
        adapter = BookedTicketAdapter(booking,this)
        adapter.setData(booking)

        binding.apply {
            rvHistoryBookedTicket.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvHistoryBookedTicket.adapter = adapter

            svHistoryBookedTicket.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    adapter.filter.filter(query)
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    adapter.filter.filter(newText)
                    return true
                }
            })
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

    override fun onItemClick(booking: Booking) {
        val action = BookedTicketFragmentDirections.actionBookedTicketFragmentToDetailTicketBookedFragment(booking)
        findNavController().navigate(action)
    }
}