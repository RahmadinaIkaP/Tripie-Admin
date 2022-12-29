package binar.academy.kelompok6.tripie_admin.view.flightlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import binar.academy.kelompok6.tripie_admin.data.network.ApiResponse
import binar.academy.kelompok6.tripie_admin.databinding.FragmentListAirportOriginBinding
import binar.academy.kelompok6.tripie_admin.model.response.Airport
import binar.academy.kelompok6.tripie_admin.view.MainActivity
import binar.academy.kelompok6.tripie_admin.view.dashboard.viewmodel.DashboardViewModel
import binar.academy.kelompok6.tripie_admin.view.flightlist.adapter.AirportAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListAirportOriginFragment : Fragment(), AirportAdapter.AirportInterface {
    private var _binding : FragmentListAirportOriginBinding? = null
    private val binding get() = _binding!!
    private val airportViewModel : DashboardViewModel by viewModels()
    private lateinit var adapter : AirportAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListAirportOriginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).supportActionBar?.title = ""

        setFlightDataAirport()
    }

    private fun setFlightDataAirport() {
        airportViewModel.getAllAirport()
        airportViewModel.getAllAirportObserver().observe(viewLifecycleOwner){ response ->
            when(response){
                is ApiResponse.Loading -> {
                    showLoading()
                    Log.d("Loading: ", response.toString())
                }
                is ApiResponse.Success -> {
                    stopLoading()
                    response.data?.let {
                        val sortedAirport = it.data.sortedBy { data -> data.id }
                        showRvDataAirport(sortedAirport)
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

    private fun showRvDataAirport(sortedAirport: List<Airport>) {
        adapter = AirportAdapter(this)
        adapter.setData(sortedAirport)

        binding.apply {
            rvListOriginAirport.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvListOriginAirport.adapter = adapter
        }
    }

    private fun stopLoading() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(airport: Airport) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(
            "namaAirportOrigin",
            airport.airportName
        )
        findNavController().navigateUp()
    }
}