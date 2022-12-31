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
import java.text.SimpleDateFormat
import java.util.*

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
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val flightDate = SimpleDateFormat("d MMMM y",
            Locale.getDefault()).format(data?.flightDate?.let { dateFormat.parse(it) }!!)
        val flightBackDate = data.flightBackDate?.let {
            dateFormat.parse(it)?.let { date->
                SimpleDateFormat("d MMMM y",
                    Locale.getDefault()).format(date)
            }
        }

        val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val convertedDepartHour = timeFormat.parse(data.departureHour)?.let {
            SimpleDateFormat("HH:mm",
                Locale.getDefault()).format(it)
        }
        val convertedArriveHour = timeFormat.parse(data.arrivalHour)?.let {
            SimpleDateFormat("HH:mm",
                Locale.getDefault()).format(it)
        }

        binding.apply {
            tvNamaPenumpang.text = data.passengerName
            tvDepartureT.text = "${data.originCode},${data.originCity}"
            tvDestinationT.text = "${data.destinationCode},${data.destinationCity}"
            tvFlightDateDepart.text = flightDate
            tvFlightDateArrive.text = flightDate
            tvTimeDepart.text = convertedDepartHour
            tvTimeArrive.text = convertedArriveHour
            tvPriceT.text = RupiahConverter.rupiah(data.price)
            tvBookingId.text = data.id.toString()
            tvClassPesawat.text = data.planeClass
            tvNamaPesawat.text = data.airlineName
            tvTipePenerbangan.text = data.flightType

            if (data.flightType.lowercase() == "round trip"){
                tvPulang.text = flightBackDate
            }else{
                tvPulang.text = "-"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}