@file:Suppress("unused", "unused", "unused", "unused", "unused", "unused", "unused")

package binar.academy.kelompok6.tripie_admin.view.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import binar.academy.kelompok6.tripie_admin.data.datastore.SharedPref
import binar.academy.kelompok6.tripie_admin.data.network.ApiResponse
import binar.academy.kelompok6.tripie_admin.databinding.FragmentDashboardBinding
import binar.academy.kelompok6.tripie_admin.view.MainActivity
import binar.academy.kelompok6.tripie_admin.view.authentication.LoginActivity
import binar.academy.kelompok6.tripie_admin.view.dashboard.viewmodel.DashboardViewModel
import binar.academy.kelompok6.tripie_admin.view.historybookedticket.viewmodel.HistoryBookingViewModel
import dagger.hilt.android.AndroidEntryPoint

@Suppress("unused", "unused", "unused", "unused", "unused", "unused", "unused")
@AndroidEntryPoint
class DashboardFragment : Fragment() {
    private var _binding : FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPref: SharedPref
    private val vmHistory : HistoryBookingViewModel by viewModels()
    private val vmDashboard : DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).supportActionBar?.title = "Dashboard"

        sharedPref = SharedPref(requireContext())

        setDataBandara()
        setDataUserAndAdmin()
        setHistoryPenerbangan()
    }

    private fun setHistoryPenerbangan() {
        vmHistory.getHistoryBooking()
        vmHistory.getHistoryBookingObserver().observe(viewLifecycleOwner){ response ->
            when(response){
                is ApiResponse.Loading -> {
                    Log.d("Loading: ", response.toString())
                }
                is ApiResponse.Success -> {
                    response.data?.let {
                        val countedBooking = it.data.booking.count()
                        binding.tvJumlahPesanan.text = countedBooking.toString()
                    }
                    Log.d("Success: ", response.toString())
                }
                is ApiResponse.Error -> {
                    Toast.makeText(requireContext(), response.msg, Toast.LENGTH_SHORT).show()
                    Log.d("Error: ", response.toString())
                }
            }
        }
    }

    private fun setDataUserAndAdmin() {
        sharedPref.getToken.asLiveData().observe(viewLifecycleOwner){ token ->
            vmDashboard.getAllUser(token)
            vmDashboard.getAllUserObserver().observe(viewLifecycleOwner){ response ->
                when(response){
                    is ApiResponse.Loading -> {
                        Log.d("Loading: ", response.toString())
                    }
                    is ApiResponse.Success -> {
                        response.data?.let {
                            val countedUserMember = it.data.count { user -> user.role == "Member" }
                            val countedUserAdmin = it.data.count { admin -> admin.role == "Admin" }
                            binding.apply {
                                tvJumlahPengguna.text = countedUserMember.toString()
                                tvJumlahAdmin.text =countedUserAdmin.toString()
                            }
                        }
                        Log.d("Success: ", response.toString())
                    }
                    is ApiResponse.Error -> {
                        Toast.makeText(requireContext(), response.msg, Toast.LENGTH_SHORT).show()
                        Log.d("Error: ", response.toString())

                        if (response.msg == "Unauthorized"){
                            val intent = Intent(requireActivity(), LoginActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish()
                        }
                    }
                }
            }
        }
    }

    private fun setDataBandara() {
        vmDashboard.getAllAirport()
        vmDashboard.getAllAirportObserver().observe(viewLifecycleOwner){ response ->
            when(response) {
                is ApiResponse.Loading -> {
                    Log.d("Loading: ", response.toString())
                }
                is ApiResponse.Success -> {
                    response.data?.let {
                        val countedAirport = it.data.count()
                        binding.tvJumlahBandara.text = countedAirport.toString()
                    }
                    Log.d("Success: ", response.toString())
                }
                is ApiResponse.Error -> {
                    Toast.makeText(requireContext(), response.msg, Toast.LENGTH_SHORT).show()
                    Log.d("Error: ", response.toString())
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}