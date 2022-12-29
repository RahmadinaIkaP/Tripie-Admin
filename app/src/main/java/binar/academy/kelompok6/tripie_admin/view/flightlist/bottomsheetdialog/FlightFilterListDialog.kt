package binar.academy.kelompok6.tripie_admin.view.flightlist.bottomsheetdialog

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import binar.academy.kelompok6.tripie_admin.databinding.DialogFlightFilterBinding
import binar.academy.kelompok6.tripie_admin.model.PlaneClass
import binar.academy.kelompok6.tripie_admin.view.MainActivity
import binar.academy.kelompok6.tripie_admin.view.flightlist.adapter.PlaneClassAdapter
import binar.academy.kelompok6.tripie_admin.view.flightlist.bottomsheetdialog.adapter.PlaneClassFilterAdapter
import binar.academy.kelompok6.tripie_admin.view.flightlist.viewmodel.PlaneClassViewModel

class FlightFilterListDialog : BottomSheetDialogFragment(), PlaneClassFilterAdapter.FilterInterface {

    private var _binding: DialogFlightFilterBinding? = null
    private val binding get() = _binding!!
    private val vmPlaneClass : PlaneClassViewModel by viewModels()
    private lateinit var adapter : PlaneClassFilterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFlightFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).supportActionBar?.hide()


        setPlaneFilter()
    }

    private fun setPlaneFilter() {


        vmPlaneClass.getPlaneClassFilter()
        vmPlaneClass.planeClassListFilter.observe(viewLifecycleOwner){
            adapter = PlaneClassFilterAdapter(it,this)

            binding.apply {
                rvPlaneClass.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                rvPlaneClass.adapter = adapter
            }
        }
    }

    override fun onItemClick(planeClass: PlaneClass) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(
            "planeFilter",
            planeClass.kelasPesawat
        )
        dialog?.dismiss()
    }

    override fun dismiss() {
        super.dismiss()
        (activity as MainActivity).supportActionBar?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}