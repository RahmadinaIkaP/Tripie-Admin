package binar.academy.kelompok6.tripie_admin.view.historybookedticket.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import binar.academy.kelompok6.tripie_admin.databinding.ItemHistoryBookedTicketBinding
import binar.academy.kelompok6.tripie_admin.model.response.Booking
import binar.academy.kelompok6.tripie_admin.utils.RupiahConverter
import java.text.SimpleDateFormat
import java.util.*

class BookedTicketAdapter (private var listBooking : List<Booking>,
                           private val onClick : BookedTicketInterface) :
    RecyclerView.Adapter<BookedTicketAdapter.ViewHolder>(), Filterable {

    private var filteredBooking : List<Booking> = listBooking

    private val differCallback = object : DiffUtil.ItemCallback<Booking>(){
        override fun areItemsTheSame(oldItem: Booking, newItem: Booking): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Booking, newItem: Booking): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    inner class ViewHolder(private val binding: ItemHistoryBookedTicketBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(booking: Booking){

            binding.apply {
                dataTicket = booking

                val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                val convertedDepartHour = dateFormat.parse(booking.departureHour)?.let {
                    SimpleDateFormat("HH:mm",
                        Locale.getDefault()).format(it)
                }

                val convertedArriveHour = dateFormat.parse(booking.arrivalHour)?.let {
                    SimpleDateFormat("HH:mm",
                        Locale.getDefault()).format(it)
                }

                tvDepartTime.text = convertedDepartHour
                tvArriveTime.text = convertedArriveHour
                tvBookedFlightPrice.text = RupiahConverter.rupiah(booking.price)

                btnDetailTiket.setOnClickListener {
                    onClick.onItemClick(booking)
                }
            }
        }
    }

    interface BookedTicketInterface {
        fun onItemClick(booking: Booking)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookedTicketAdapter.ViewHolder {
        val view = ItemHistoryBookedTicketBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookedTicketAdapter.ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun setData(data : List<Booking>){
        differ.submitList(data)
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()

                if (charString.isEmpty()){
                    filteredBooking = listBooking
                }else{
                    val filtered = ArrayList<Booking>()
                    for (row in listBooking){
                        if (row.originCity.lowercase().contains(constraint.toString().lowercase())
                            || row.destinationCity.lowercase().contains(constraint.toString().lowercase())
                            || row.originCode.lowercase().contains(constraint.toString().lowercase())
                            || row.destinationCode.lowercase().contains(constraint.toString().lowercase())
                        ){
                            filtered.add(row)
                        }
                        filteredBooking = filtered
                    }
                }

                val result = FilterResults()
                result.values = filteredBooking
                return result
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredBooking =
                    if (results?.values == null){
                        ArrayList()
                    }else{
                        results.values as ArrayList<Booking>
                    }
                differ.submitList(filteredBooking)
            }

        }
    }
}