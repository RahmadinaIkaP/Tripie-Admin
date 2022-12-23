package binar.academy.kelompok6.tripie_admin.view.flightlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import binar.academy.kelompok6.tripie_admin.R
import binar.academy.kelompok6.tripie_admin.data.datastore.SharedPref
import binar.academy.kelompok6.tripie_admin.data.network.ApiResponse
import binar.academy.kelompok6.tripie_admin.databinding.FragmentEditFlightBinding
import binar.academy.kelompok6.tripie_admin.model.request.AddEditScheduleRequest
import binar.academy.kelompok6.tripie_admin.model.response.Airport
import binar.academy.kelompok6.tripie_admin.view.MainActivity
import binar.academy.kelompok6.tripie_admin.view.flightlist.viewmodel.FlightScheduleViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

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

            etEditFlightDate.setOnClickListener {
                getDateFromDatePicker()
            }

            etEditOriginDestination.setOnClickListener {
                findNavController().navigate(R.id.action_addFlightFragment_to_listAirportOriginFragment)
            }

            etEditDestinationAirport.setOnClickListener {
                findNavController().navigate(R.id.action_addFlightFragment_to_listAirportDestinationFragment)
            }

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

            btnDeleteFlight.setOnClickListener {
                dataFlightSchedule?.let { data -> deleteFlight(data.id) }
            }
        }
    }

    private fun getDateFromDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Tanggal Penerbangan")
            .setSelection(MaterialDatePicker.thisMonthInUtcMilliseconds())
            .build()

        datePicker.show(parentFragmentManager, "datePicker")
        datePicker.addOnPositiveButtonClickListener {
            val dateFormat = SimpleDateFormat("MM-dd-yyyy'T'HH:mm:ss.SSS'Z'", Locale.getDefault())

            binding.etEditFlightDate.setText(dateFormat.format(Date(it)))
        }
    }

    private fun getNameOriginAirport(){
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Airport>(
            "namaAirportOrigin"
        )?.observe(viewLifecycleOwner) {
            binding.etEditOriginDestination.setText(it.airportName)
        }
    }

    private fun getNameDestinationAirport(){
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Airport>(
            "namaAirportDestination"
        )?.observe(viewLifecycleOwner) {
            binding.etEditDestinationAirport.setText(it.airportName)
        }
    }

    private fun deleteFlight(id : Int) {
        sharedPref.getToken.asLiveData().observe(requireActivity()){ token ->
            flightScheduleViewModel.deleteFlightSchedule(token, id)
        }

        flightScheduleViewModel.deleteScheduleObserver().observe(viewLifecycleOwner){ response ->
            when(response){
                is ApiResponse.Loading -> {
                    Log.d("Loading: ", response.toString())
                }
                is ApiResponse.Success -> {
                    Log.d("Success: ", response.toString())
                    findNavController().navigate(R.id.action_editFlightFragment_to_flightFragment)
                }
                is ApiResponse.Error -> {
                    Toast.makeText(requireContext(), response.msg, Toast.LENGTH_SHORT).show()
                    Log.d("Error: ", response.toString())
                }
            }
        }
    }

    private fun editFlightSchedule(token : String, id : Int, addEditScheduleRequest: AddEditScheduleRequest) {
        flightScheduleViewModel.editFlightSchedule(token, id, addEditScheduleRequest)

        flightScheduleViewModel.editScheduleObserver().observe(viewLifecycleOwner){ response ->
            when(response){
                is ApiResponse.Loading -> {
                    Log.d("Loading: ", response.toString())
                }
                is ApiResponse.Success -> {
                    Log.d("Success: ", response.toString())
                    findNavController().navigate(R.id.action_editFlightFragment_to_flightFragment)
                }
                is ApiResponse.Error -> {
                    Log.d("Error: ", response.toString())
                    Toast.makeText(requireContext(), response.msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}