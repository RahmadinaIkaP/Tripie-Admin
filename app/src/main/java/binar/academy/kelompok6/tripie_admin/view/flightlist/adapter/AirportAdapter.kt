package binar.academy.kelompok6.tripie_admin.view.flightlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import binar.academy.kelompok6.tripie_admin.databinding.ItemAirportBinding
import binar.academy.kelompok6.tripie_admin.model.response.Airport

class AirportAdapter(private val onClick : AirportInterface) : RecyclerView.Adapter<AirportAdapter.ViewHolder>(){

    private val differCallback = object : DiffUtil.ItemCallback<Airport>(){
        override fun areItemsTheSame(oldItem: Airport, newItem: Airport): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Airport, newItem: Airport): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    inner class ViewHolder(private val binding : ItemAirportBinding) :
        RecyclerView.ViewHolder(binding.root){
            fun bind(airport: Airport){
                binding.tvAirport.text = airport.airportName

                itemView.setOnClickListener {
                    onClick.onItemClick(airport)
                }
            }
    }

    interface AirportInterface {
        fun onItemClick(airport: Airport)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemAirportBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun setData(data : List<Airport>){
        differ.submitList(data)
    }
}