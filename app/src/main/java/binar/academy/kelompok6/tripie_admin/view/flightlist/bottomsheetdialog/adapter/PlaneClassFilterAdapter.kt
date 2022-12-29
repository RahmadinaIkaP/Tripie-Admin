package binar.academy.kelompok6.tripie_admin.view.flightlist.bottomsheetdialog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import binar.academy.kelompok6.tripie_admin.databinding.ItemFlightFilterDialogBinding
import binar.academy.kelompok6.tripie_admin.model.PlaneClass

class PlaneClassFilterAdapter(private val listPlane: List<PlaneClass>,private val onClick : FilterInterface) : RecyclerView.Adapter<PlaneClassFilterAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding : ItemFlightFilterDialogBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(planeClass: PlaneClass){
            binding.textViewFilterClass.text = planeClass.kelasPesawat
            itemView.setOnClickListener {
                onClick.onItemClick(planeClass)
            }
        }
    }

    interface FilterInterface {
        fun onItemClick(planeClass: PlaneClass)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemFlightFilterDialogBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listPlane[position])
    }

    override fun getItemCount(): Int = listPlane.size
}