package binar.academy.kelompok6.tripie_admin.view.historybookedticket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import binar.academy.kelompok6.tripie_admin.R
import binar.academy.kelompok6.tripie_admin.databinding.FragmentDetailTicketBookedBinding
import binar.academy.kelompok6.tripie_admin.model.response.Booking
import binar.academy.kelompok6.tripie_admin.utils.RupiahConverter
import binar.academy.kelompok6.tripie_admin.view.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailTicketBookedFragment : Fragment() {
    private var _binding : FragmentDetailTicketBookedBinding? = null
    private val binding get() = _binding!!
    private val args : DetailTicketBookedFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentDetailTicketBookedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).supportActionBar?.title = "Detail Tiket"

        val data = args.dataTiketBook

        setDataTiket(data)
    }

    private fun setDataTiket(data: Booking?) {
        binding.apply {
            tvNamaPenumpang.text = data?.passengerName ?: "Undefined"
            tvDepartureT.text = "${data?.originCode},${data?.originCity}"
            tvDestinationT.text = "${data?.destinationCode},${data?.destinationCity}"
            tvFlightDateDepart.text = data?.flightDate ?: "Undefined"
            tvFlightDateArrive.text = data?.flightDate ?: "Undefined"
            tvTimeDepart.text = data?.departureHour ?: "Undefined"
            tvTimeArrive.text = data?.arrivalHour ?: "Undefined"
            tvPriceT.text = RupiahConverter.rupiah(data?.price)
            tvBookingId.text = data?.id.toString()
            tvClassPesawat.text = data?.planeClass
            tvNamaPesawat.text = data?.airlineName
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}