@file:Suppress("unused", "unused", "unused", "unused", "unused")

package binar.academy.kelompok6.tripie_admin.view.flightlist.bottomsheetdialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import binar.academy.kelompok6.tripie_admin.data.datastore.SharedPref
import binar.academy.kelompok6.tripie_admin.databinding.DialogFlightFilterBinding
import binar.academy.kelompok6.tripie_admin.model.PlaneClass
import binar.academy.kelompok6.tripie_admin.view.MainActivity
import binar.academy.kelompok6.tripie_admin.view.flightlist.bottomsheetdialog.adapter.PlaneClassFilterAdapter
import binar.academy.kelompok6.tripie_admin.view.flightlist.viewmodel.PlaneClassViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Suppress("unused", "unused", "unused", "unused", "unused")
@OptIn(DelicateCoroutinesApi::class)
class FlightFilterListDialog : BottomSheetDialogFragment(), PlaneClassFilterAdapter.FilterInterface {

    private var _binding: DialogFlightFilterBinding? = null
    private val binding get() = _binding!!
    private val vmPlaneClass : PlaneClassViewModel by viewModels()
    private lateinit var adapter : PlaneClassFilterAdapter
    private lateinit var sharedPref: SharedPref

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

        sharedPref = SharedPref(requireContext())

        binding.ivFilterExit.setOnClickListener {
            dialog?.dismiss()
        }

        setPlaneFilter()
    }

    private fun setPlaneFilter() {

        vmPlaneClass.getPlaneClassFilter()
        vmPlaneClass.planeClassListFilter.observe(viewLifecycleOwner){ list ->
            sharedPref.getPlane.asLiveData().observe(viewLifecycleOwner){ pclass ->
                adapter = PlaneClassFilterAdapter(pclass, list ,this)

                binding.apply {
                    rvPlaneClass.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    rvPlaneClass.adapter = adapter
                }
            }
        }
    }

    override fun onItemClick(planeClass: PlaneClass) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(
            "planeFilter",
            planeClass.kelasPesawat
        )
        GlobalScope.launch {
            sharedPref.savePlane(planeClass.kelasPesawat)
        }
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