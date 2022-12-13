package binar.academy.kelompok6.tripie_admin.view.flightlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import binar.academy.kelompok6.tripie_admin.databinding.ItemPenerbanganBinding

class FlightAdapter (private val onClick : FlightInterface) : RecyclerView.Adapter<FlightAdapter.ViewHolder>() {

//    private val differCallback = object : DiffUtil.ItemCallback<>(){
//
//    }
//
//    private val differ = AsyncListDiffer(this, differCallback)

    inner class ViewHolder(private val binding: ItemPenerbanganBinding) :
        RecyclerView.ViewHolder(binding.root){
            fun bind(){

            }
    }

    interface FlightInterface{
        fun onItemClick()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemPenerbanganBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
//        differ.currentList.size
        return 0
    }
}