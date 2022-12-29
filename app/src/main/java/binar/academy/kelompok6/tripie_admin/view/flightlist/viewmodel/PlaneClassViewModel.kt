package binar.academy.kelompok6.tripie_admin.view.flightlist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import binar.academy.kelompok6.tripie_admin.model.PlaneClass

class PlaneClassViewModel : ViewModel() {

    val planeClassList : MutableLiveData<List<PlaneClass>> = MutableLiveData()
    val planeClassListFilter : MutableLiveData<List<PlaneClass>> = MutableLiveData()

    private val listPlaneClass = arrayListOf(
        PlaneClass("economy"),
        PlaneClass("business"),
        PlaneClass("first class")
    )

    private val listPlaneClassFilter = arrayListOf(
        PlaneClass("economy"),
        PlaneClass("business"),
        PlaneClass("first class"),
        PlaneClass("all class")
    )

    fun getPlaneClass(){
        planeClassList.value = listPlaneClass
    }

    fun getPlaneClassFilter(){
        planeClassListFilter.value = listPlaneClassFilter
    }
}