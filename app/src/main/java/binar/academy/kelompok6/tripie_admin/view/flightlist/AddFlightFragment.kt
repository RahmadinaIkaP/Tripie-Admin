package binar.academy.kelompok6.tripie_admin.view.flightlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import binar.academy.kelompok6.tripie_admin.R
import binar.academy.kelompok6.tripie_admin.data.datastore.SharedPref
import binar.academy.kelompok6.tripie_admin.databinding.FragmentAddFlightBinding
import binar.academy.kelompok6.tripie_admin.view.MainActivity
import binar.academy.kelompok6.tripie_admin.view.flightlist.viewmodel.FlightScheduleViewModel
import dagger.hilt.android.AndroidEntryPoint

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

        binding.btnSaveFlightSchedule.setOnClickListener {
            addSchedule()
        }
    }

    private fun addSchedule() {
        sharedPref.getToken.asLiveData().observe(requireActivity()){ token ->

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}