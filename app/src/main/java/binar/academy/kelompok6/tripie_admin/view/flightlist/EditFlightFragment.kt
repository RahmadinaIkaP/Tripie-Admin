@file:Suppress("unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused",
    "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused",
    "unused", "unused", "unused", "unused", "unused", "unused", "unused"
)

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
import androidx.navigation.fragment.navArgs
import binar.academy.kelompok6.tripie_admin.R
import binar.academy.kelompok6.tripie_admin.data.datastore.SharedPref
import binar.academy.kelompok6.tripie_admin.data.network.ApiResponse
import binar.academy.kelompok6.tripie_admin.databinding.FragmentEditFlightBinding
import binar.academy.kelompok6.tripie_admin.model.request.AddEditScheduleRequest
import binar.academy.kelompok6.tripie_admin.view.MainActivity
import binar.academy.kelompok6.tripie_admin.view.authentication.LoginActivity
import binar.academy.kelompok6.tripie_admin.view.dashboard.viewmodel.DashboardViewModel
import binar.academy.kelompok6.tripie_admin.view.flightlist.viewmodel.FlightScheduleViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@Suppress("unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused",
    "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused",
    "unused", "unused", "unused", "unused", "unused", "unused"
)
@AndroidEntryPoint
class EditFlightFragment : Fragment() {
    private var _binding : FragmentEditFlightBinding? = null
    private val binding get() = _binding!!
    private val args : EditFlightFragmentArgs by navArgs()
    private val flightScheduleViewModel : FlightScheduleViewModel by viewModels()
    private val airportViewModel : DashboardViewModel by viewModels()
    private lateinit var sharedPref: SharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
                findNavController().navigate(R.id.action_editFlightFragment_to_listAirportOriginFragment)
            }

            getNameOriginAirport()

            etEditDestinationAirport.setOnClickListener {
                findNavController().navigate(R.id.action_editFlightFragment_to_listAirportDestinationFragment)
            }

            getNameDestinationAirport()

//            etEditFlightClass.setOnClickListener {
//                findNavController().navigate(R.id.action_editFlightFragment_to_listPlaneClassFragment)
//            }
//
//            getPlaneClass()

            btnUpdateFlight.setOnClickListener {
                sharedPref.getToken.asLiveData().observe(viewLifecycleOwner){ token ->
                    dataFlightSchedule?.let { data -> editFlightSchedule(token, data.id,
                        AddEditScheduleRequest(
                            etEditOgDestinationCode.text.toString(),
                            etEditOriginDestination.text.toString(),
                            etEditOriginDestinationCity.text.toString(),
                            etEditDestinationAirportCode.text.toString(),
                            etEditDestinationAirport.text.toString(),
                            etEditDestinationAirportCity.text.toString(),
                            etEditFlightClass.text.toString().lowercase(),
                            etEditFlightDate.text.toString(),
                            etEditAirplaneName.text.toString(),
                            etEditDepartureTime.text.toString(),
                            etEditArriveTime.text.toString(),
                            etEditFlightPrice.text.toString().toInt()
                        )
                    ) }
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
            val dateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault())

            binding.etEditFlightDate.setText(dateFormat.format(Date(it)))
        }
    }

    private fun getNameOriginAirport(){
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(
            "namaAirportOrigin"
        )?.observe(viewLifecycleOwner) { name ->
            airportViewModel.getAllAirport()
            airportViewModel.getAllAirportObserver().observe(viewLifecycleOwner){ response->
                when(response){
                    is ApiResponse.Loading -> {
                        Log.d("Loading: ", response.toString())
                    }
                    is ApiResponse.Success -> {
                        response.data?.let {
                            val sortedAirport = it.data.sortedBy { data -> data.airportName == name }
                            sortedAirport.forEach { airport ->
                                binding.apply {
                                    etEditOriginDestination.setText(airport.airportName)
                                    etEditOgDestinationCode.setText(airport.airportCode)
                                    etEditOriginDestinationCity.setText(airport.city)
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
                                    etEditDestinationAirport.setText(airport.airportName)
                                    etEditDestinationAirportCode.setText(airport.airportCode)
                                    etEditDestinationAirportCity.setText(airport.city)
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
            binding.etEditFlightClass.setText(it)
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

                    if (response.msg == "Unauthorized"){
                        val intent = Intent(requireActivity(), LoginActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
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