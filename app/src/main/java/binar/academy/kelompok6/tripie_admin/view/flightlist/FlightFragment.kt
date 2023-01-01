@file:Suppress("unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused",
    "unused", "unused", "unused"
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
import androidx.recyclerview.widget.LinearLayoutManager
import binar.academy.kelompok6.tripie_admin.R
import binar.academy.kelompok6.tripie_admin.data.datastore.SharedPref
import binar.academy.kelompok6.tripie_admin.data.network.ApiResponse
import binar.academy.kelompok6.tripie_admin.databinding.FragmentFlightBinding
import binar.academy.kelompok6.tripie_admin.model.response.Jadwal
import binar.academy.kelompok6.tripie_admin.view.MainActivity
import binar.academy.kelompok6.tripie_admin.view.authentication.LoginActivity
import binar.academy.kelompok6.tripie_admin.view.flightlist.adapter.FlightAdapter
import binar.academy.kelompok6.tripie_admin.view.flightlist.viewmodel.FlightScheduleViewModel
import dagger.hilt.android.AndroidEntryPoint

@Suppress("unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused",
    "unused", "unused"
)
@AndroidEntryPoint
class FlightFragment : Fragment(), FlightAdapter.FlightInterface {
    private var _binding : FragmentFlightBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPref: SharedPref
    private lateinit var adapter : FlightAdapter
    private val flightScheduleViewModel : FlightScheduleViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.title = "Daftar Penerbangan"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFlightBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).supportActionBar?.title = "Daftar Penerbangan"

        sharedPref = SharedPref(requireContext())

        binding.apply {
            btnAddFlightSchedule.setOnClickListener {
                findNavController().navigate(R.id.action_flightFragment_to_addFlightFragment)
            }

            btnFilter.setOnClickListener {
                findNavController().navigate(R.id.action_flightFragment_to_flightFilterListDialog)
            }

//            btnSortPrice.setOnClickListener {
//                findNavController().navigate(R.id.action_flightFragment_to_sortingPriceDialog)
//            }
        }

        setFlightRvData()
        getPlaneClassFilter()
    }

    private fun setFlightRvData() {
        sharedPref.getToken.asLiveData().observe(viewLifecycleOwner){ token ->
            flightScheduleViewModel.getAllFlightSchedule(token)

            flightScheduleViewModel.getAllScheduleObserver().observe(viewLifecycleOwner){ response ->
                when(response){
                    is ApiResponse.Loading -> {
                        showLoading()
                        Log.d("Loading: ", response.toString())
                    }
                    is ApiResponse.Success -> {
                        stopLoading()
                        response.data?.let {
                            val sortedSchedule = it.data.jadwal.sortedByDescending { data -> data.id }
                            showRvData(sortedSchedule)
                        }
                        Log.d("Success: ", response.toString())
                    }
                    is ApiResponse.Error -> {
                        stopLoading()
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

    private fun showRvData(listJadwal: List<Jadwal>) {
        adapter = FlightAdapter(this)
        adapter.setData(listJadwal)

        binding.apply {
            rvFlightSchedule.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            rvFlightSchedule.adapter = adapter
        }
    }

    private fun getPlaneClassFilter(){
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(
            "planeFilter"
        )?.observe(viewLifecycleOwner){ pclass ->
            sharedPref.getToken.asLiveData().observe(viewLifecycleOwner){ token ->
                flightScheduleViewModel.getAllFlightSchedule(token)

                flightScheduleViewModel.getAllScheduleObserver().observe(viewLifecycleOwner){ response ->
                    when(response){
                        is ApiResponse.Loading -> {
                            showLoading()
                            Log.d("Loading: ", response.toString())
                        }
                        is ApiResponse.Success -> {
                            stopLoading()
                            response.data?.let {
                                checkingFilter(pclass, it.data.jadwal)
                            }
                            Log.d("Success: ", response.toString())
                        }
                        is ApiResponse.Error -> {
                            stopLoading()
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
    }

    private fun checkingFilter(pclass: String?, jadwal: List<Jadwal>) {
        when{
            pclass.equals("economy") -> {
                val resultEco = jadwal.filter { it.planeClass.lowercase() == "economy" }
                showRvData(resultEco)
            }
            pclass.equals("business") -> {
                val resultBusiness = jadwal.filter { it.planeClass.lowercase() == "business" }
                showRvData(resultBusiness)
            }
            pclass.equals("first class") -> {
                val resultFc = jadwal.filter { it.planeClass.lowercase() == "first class" }
                showRvData(resultFc)
            }
            else -> {
                showRvData(jadwal)
            }
        }
    }

    private fun stopLoading() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun onItemClick(jadwal: Jadwal) {
        val action = FlightFragmentDirections.actionFlightFragmentToEditFlightFragment(jadwal)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}