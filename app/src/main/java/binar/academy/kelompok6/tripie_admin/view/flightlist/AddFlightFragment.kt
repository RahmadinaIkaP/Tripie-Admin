package binar.academy.kelompok6.tripie_admin.view.flightlist

import android.content.Intent
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
import binar.academy.kelompok6.tripie_admin.R
import binar.academy.kelompok6.tripie_admin.data.datastore.SharedPref
import binar.academy.kelompok6.tripie_admin.data.network.ApiResponse
import binar.academy.kelompok6.tripie_admin.databinding.FragmentAddFlightBinding
import binar.academy.kelompok6.tripie_admin.model.request.AddEditScheduleRequest
import binar.academy.kelompok6.tripie_admin.view.MainActivity
import binar.academy.kelompok6.tripie_admin.view.authentication.LoginActivity
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
    private val airportViewModel : DashboardViewModel by viewModels()
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

            etFlightClass.setOnClickListener {
                findNavController().navigate(R.id.action_addFlightFragment_to_listPlaneClassFragment)
            }

            getPlaneClass()

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
            val dateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault())

            binding.etFlightDate.setText(dateFormat.format(Date(it)))
        }
    }

    private fun getNameOriginAirport(){
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(
            "namaAirportOrigin"
        )?.observe(viewLifecycleOwner) { name ->
            airportViewModel.getAllAirport()
            airportViewModel.getAllAirportObserver().observe(viewLifecycleOwner){ response ->
                when(response){
                    is ApiResponse.Loading -> {
                        Log.d("Loading: ", response.toString())
                    }
                    is ApiResponse.Success -> {
                        response.data?.let {
                            val sortedAirport = it.data.filter { data -> data.airportName == name }
                            sortedAirport.forEach { airport ->
                                binding.apply {
                                    binding.apply {
                                        etOriginDestination.setText(airport.airportName)
                                        etOgDestinationCode.setText(airport.airportCode)
                                        etOriginDestinationCity.setText(airport.city)
                                    }
                                }
                            }

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
    }

    private fun getNameDestinationAirport(){
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(
            "namaAirportDestination"
        )?.observe(viewLifecycleOwner) { name ->
            airportViewModel.getAllAirport()
            airportViewModel.getAllAirportObserver().observe(viewLifecycleOwner){ response ->
                when(response){
                    is ApiResponse.Loading -> {
                        Log.d("Loading: ", response.toString())
                    }
                    is ApiResponse.Success -> {
                        response.data?.let {
                            val sortedAirport = it.data.filter { data -> data.airportName == name }
                            sortedAirport.forEach { airport ->
                                binding.apply {
                                    etDestinationAirport.setText(airport.airportName)
                                    etDestinationAirportCode.setText(airport.airportCode)
                                    etDestinationAirportCity.setText(airport.city)
                                }
                            }

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
    }

    private fun getPlaneClass() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(
            "planeClassName"
        )?.observe(viewLifecycleOwner){
            binding.etFlightClass.setText(it)
        }
    }

    private fun addSchedule() {
        if (binding.etOriginDestination.text.toString().isEmpty() || binding.etOgDestinationCode.text.toString().isEmpty() || binding.etDestinationAirport.text.toString().isEmpty()
            || binding.etFlightDate.text.toString().isEmpty() || binding.etFlightClass.text.toString().isEmpty() || binding.etOriginDestinationCity.text.toString().isEmpty()
            || binding.etArriveTime.text.toString().isEmpty() || binding.etDepartureTime.text.toString().isEmpty() || binding.etDestinationAirportCity.text.toString().isEmpty()
            || binding.etFlightPrice.text.toString().isEmpty() || binding.etAirplaneName.text.toString().isEmpty() || binding.etDestinationAirportCode.text.toString().isEmpty()
        ){
            Toast.makeText(requireContext(), "Data tidak boleh kosong", Toast.LENGTH_SHORT).show()
        } else {
            sharedPref.getToken.asLiveData().observe(requireActivity()){ token ->
                flightScheduleViewModel.addFlightSchedule(token, AddEditScheduleRequest(
                    originCode = binding.etOgDestinationCode.text.toString(),
                    originName = binding.etOriginDestination.text.toString(),
                    originCity = binding.etOriginDestinationCity.text.toString(),
                    destinationCode = binding.etDestinationAirportCode.text.toString(),
                    destinationName = binding.etDestinationAirport.text.toString(),
                    destinationCity = binding.etDestinationAirportCity.text.toString(),
                    planeClass = binding.etFlightClass.text.toString(),
                    flightDate = binding.etFlightDate.text.toString(),
                    airlineName = binding.etAirplaneName.text.toString(),
                    departureHour = binding.etDepartureTime.text.toString(),
                    arrivalHour = binding.etArriveTime.text.toString(),
                    price = binding.etFlightPrice.text.toString().toInt(),
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}