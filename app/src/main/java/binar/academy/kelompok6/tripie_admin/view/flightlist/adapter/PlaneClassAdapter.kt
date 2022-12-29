package binar.academy.kelompok6.tripie_admin.view.flightlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import binar.academy.kelompok6.tripie_admin.databinding.ItemAirportBinding
import binar.academy.kelompok6.tripie_admin.model.PlaneClass
import binar.academy.kelompok6.tripie_admin.model.response.Airport

class PlaneClassAdapter(private val onClick : PlaneClassInterface) : RecyclerView.Adapter<PlaneClassAdapter.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<PlaneClass>(){
        override fun areItemsTheSame(oldItem: PlaneClass, newItem: PlaneClass): Boolean {
            return oldItem.kelasPesawat == newItem.kelasPesawat
        }

        override fun areContentsTheSame(oldItem: PlaneClass, newItem: PlaneClass): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)

    inner class ViewHolder(private val binding : ItemAirportBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(planeClass: PlaneClass){
                binding.tvAirport.text = planeClass.kelasPesawat

                itemView.setOnClickListener {
                    onClick.onItemClick(planeClass)
                }
            }
    }

    interface PlaneClassInterface {
        fun onItemClick(planeClass: PlaneClass)
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

    fun setData(data : List<PlaneClass>){
        differ.submitList(data)
    }
}