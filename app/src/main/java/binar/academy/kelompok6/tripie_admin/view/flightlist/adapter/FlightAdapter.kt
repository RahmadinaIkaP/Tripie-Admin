package binar.academy.kelompok6.tripie_admin.view.flightlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import binar.academy.kelompok6.tripie_admin.databinding.ItemPenerbanganBinding
import binar.academy.kelompok6.tripie_admin.model.response.Jadwal

class FlightAdapter (private val onClick : FlightInterface) : RecyclerView.Adapter<FlightAdapter.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Jadwal>(){
        override fun areItemsTheSame(oldItem: Jadwal, newItem: Jadwal): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Jadwal, newItem: Jadwal): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    inner class ViewHolder(private val binding: ItemPenerbanganBinding) :
        RecyclerView.ViewHolder(binding.root){
            fun bind(jadwal: Jadwal){
                binding.scheduleData = jadwal

                itemView.setOnClickListener {
                    onClick.onItemClick(jadwal)
                }
            }
    }

    interface FlightInterface{
        fun onItemClick(jadwal: Jadwal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemPenerbanganBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun setData(data : List<Jadwal>){
        differ.submitList(data)
    }
}