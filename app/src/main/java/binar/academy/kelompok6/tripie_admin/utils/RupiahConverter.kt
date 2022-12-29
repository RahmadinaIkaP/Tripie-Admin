package binar.academy.kelompok6.tripie_admin.utils

import java.text.NumberFormat
import java.util.*

object RupiahConverter {
    fun rupiah(number: Int?): String {
        val localeID = Locale("in", "ID")
        val formatRupiah: NumberFormat = NumberFormat.getCurrencyInstance(localeID)
        return formatRupiah.format(number)
    }
}