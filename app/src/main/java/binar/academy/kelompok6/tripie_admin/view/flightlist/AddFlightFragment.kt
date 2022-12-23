package binar.academy.kelompok6.tripie_admin.view.flightlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import binar.academy.kelompok6.tripie_admin.R
import binar.academy.kelompok6.tripie_admin.data.datastore.SharedPref
import binar.academy.kelompok6.tripie_admin.data.network.ApiResponse
import binar.academy.kelompok6.tripie_admin.databinding.FragmentAddFlightBinding
import binar.academy.kelompok6.tripie_admin.model.request.AddEditScheduleRequest
import binar.academy.kelompok6.tripie_admin.model.response.Airport
import binar.academy.kelompok6.tripie_admin.view.MainActivity
import binar.academy.kelompok6.tripie_admin.view.dashboard.viewmodel.DashboardViewModel
import binar.academy.kelompok6.tripie_admin.view.flightlist.viewmodel.FlightScheduleViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddFlightFragment : Fragment() {
    private var _binding : FragmentAddFlightBinding? = null
    private val binding get() = _binding!!
    private val flightScheduleViewModel : FlightScheduleViewModel by viewModels()
    private lateinit var sharedPref: SharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddFlightBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).supportActionBar?.title = "Tambah Jadwal Penerbangan"
        sharedPref = SharedPref(requireContext())

        binding.apply {
            etFlightDate.setOnClickListener {
                getDateFromDatePicker()
            }

            etOriginDestination.setOnClickListener {
                findNavController().navigate(R.id.action_addFlightFragment_to_listAirportOriginFragment)
            }

            getNameOriginAirport()

            etDestinationAirport.setOnClickListener {
                findNavController().navigate(R.id.action_addFlightFragment_to_listAirportDestinationFragment)
            }

            getNameDestinationAirport()

            btnSaveFlightSchedule.setOnClickListener {
                addSchedule()
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

            binding.etFlightDate.setText(dateFormat.format(Date(it)))
        }
    }

    private fun getNameOriginAirport(){
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Airport>(
            "namaAirportOrigin"
        )?.observe(viewLifecycleOwner) {
            binding.etOriginDestination.setText(it.airportName)
        }
    }

    private fun getNameDestinationAirport(){
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Airport>(
            "namaAirportDestination"
        )?.observe(viewLifecycleOwner) {
            binding.etDestinationAirport.setText(it.airportName)
        }
    }

    private fun addSchedule() {
        if (binding.etOriginDestination.text.toString().isEmpty() || binding.etOgDestinationCode.text.toString().isEmpty() || binding.etDestinationAirport.text.toString().isEmpty()
            || binding.etFlightDate.text.toString().isEmpty() || binding.etFlightClass.text.toString().isEmpty()
            || binding.etArriveTime.text.toString().isEmpty() || binding.etDepartureTime.text.toString().isEmpty()
            || binding.etFlightPrice.text.toString().isEmpty() || binding.etAirplaneName.text.toString().isEmpty()
        ){
            Toast.makeText(requireContext(), "DataHistory tidak boleh kosong", Toast.LENGTH_SHORT).show()
        } else {
            sharedPref.getToken.asLiveData().observe(requireActivity()){ token ->
                flightScheduleViewModel.addFlightSchedule(token, AddEditScheduleRequest(
                    originAirport = binding.etOriginDestination.text.toString(),
                    destinationAirport = binding.etDestinationAirport.text.toString(),
                    flightDate = binding.etFlightDate.text.toString(),
                    planeClass = binding.etFlightClass.text.toString(),
                    arrivalHour = binding.etArriveTime.text.toString(),
                    airlineName = binding.etAirplaneName.text.toString(),
                    departureHour = binding.etDepartureTime.text.toString(),
                    price = binding.etFlightPrice.text.toString().toInt()
                ))
            }

            flightScheduleViewModel.addScheduleObserver().observe(viewLifecycleOwner){ response ->
                when(response){
                    is ApiResponse.Loading -> {
                        Log.d("Loading: ", response.toString())
                    }
                    is ApiResponse.Success -> {
                        Toast.makeText(requireContext(), "Tambah jadwal penerbangan berhasil!", Toast.LENGTH_SHORT).show()
                        Log.d("Success: ", response.toString())
                        findNavController().navigate(R.id.action_addFlightFragment_to_flightFragment)
                    }
                    is ApiResponse.Error -> {
                        Log.d("Error: ", response.toString())
                        Toast.makeText(requireContext(), response.msg, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}