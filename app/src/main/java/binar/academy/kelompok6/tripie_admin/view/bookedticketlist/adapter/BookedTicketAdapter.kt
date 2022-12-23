package binar.academy.kelompok6.tripie_admin.view.bookedticketlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import binar.academy.kelompok6.tripie_admin.databinding.ItemHistoryBookedTicketBinding
import binar.academy.kelompok6.tripie_admin.model.response.Booking

class BookedTicketAdapter (private val onClick : BookedTicketInterface) : RecyclerView.Adapter<BookedTicketAdapter.ViewHolder>() {

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

            itemView.setOnClickListener {
                onClick.onItemClick(booking)
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
}