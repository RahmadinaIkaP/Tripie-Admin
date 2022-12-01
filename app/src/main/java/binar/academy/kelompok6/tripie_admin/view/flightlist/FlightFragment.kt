package binar.academy.kelompok6.tripie_admin.view.flightlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import binar.academy.kelompok6.tripie_admin.R
import binar.academy.kelompok6.tripie_admin.databinding.FragmentFlightBinding
import binar.academy.kelompok6.tripie_admin.view.MainActivity

class FlightFragment : Fragment() {
    private var _binding : FragmentFlightBinding? = null
    private val binding get() = _binding!!

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}