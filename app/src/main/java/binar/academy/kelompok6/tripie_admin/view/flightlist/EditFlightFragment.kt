package binar.academy.kelompok6.tripie_admin.view.flightlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import binar.academy.kelompok6.tripie_admin.R
import binar.academy.kelompok6.tripie_admin.data.datastore.SharedPref
import binar.academy.kelompok6.tripie_admin.data.network.ApiResponse
import binar.academy.kelompok6.tripie_admin.databinding.FragmentEditFlightBinding
import binar.academy.kelompok6.tripie_admin.model.request.AddEditScheduleRequest
import binar.academy.kelompok6.tripie_admin.view.MainActivity
import binar.academy.kelompok6.tripie_admin.view.flightlist.viewmodel.FlightScheduleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditFlightFragment : Fragment() {
    private var _binding : FragmentEditFlightBinding? = null
    private val binding get() = _binding!!
    private val args : EditFlightFragmentArgs by navArgs()
    private val flightScheduleViewModel : FlightScheduleViewModel by viewModels()
    private lateinit var sharedPref: SharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditFlightBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).supportActionBar?.title = "Ubah Jadwal Penerbangan"
        sharedPref = SharedPref(requireContext())
        val dataFlightSchedule = args.argsEditFlight

        binding.apply {
            scheduleDataEdit = dataFlightSchedule

            btnUpdateFlight.setOnClickListener {
                sharedPref.getToken.asLiveData().observe(requireActivity()){ token ->
                    dataFlightSchedule?.let { data -> editFlightSchedule(token, data.id,
                        AddEditScheduleRequest(
                            data.airlineName,
                            data.arrivalHour,
                            data.departureHour,
                            data.destinationAirport,
                            data.flightDate,
                            data.originAirport,
                            data.planeClass,
                            data.price
                        )) }
                }
            }
        }
    }

    private fun editFlightSchedule(token : String, id : Int,
        addEditScheduleRequest: AddEditScheduleRequest) {
        flightScheduleViewModel.editFlightSchedule(token, id, addEditScheduleRequest)

        flightScheduleViewModel.editScheduleObserver().observe(viewLifecycleOwner){ response ->
            when(response){
                is ApiResponse.Loading -> {
                    Log.d("Loading: ", response.toString())
                }
                is ApiResponse.Success -> {
                    Log.d("Success: ", response.toString())
                }
                is ApiResponse.Error -> {
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