package binar.academy.kelompok6.tripie_admin.view.bookedticketlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import binar.academy.kelompok6.tripie_admin.databinding.FragmentBookedTicketBinding
import binar.academy.kelompok6.tripie_admin.view.MainActivity

class BookedTicketFragment : Fragment() {
    private var _binding : FragmentBookedTicketBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookedTicketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).supportActionBar?.title = "Daftar Pesanan"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}