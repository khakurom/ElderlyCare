package com.project.elderlyhealthcare.utils

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.project.elderlyhealthcare.databinding.MonthYearPickerBinding
import java.util.Calendar
import java.util.Date

class MonthYearPickerDialog(private val date: Date = Date()) : DialogFragment() {

	companion object {
		private const val MAX_YEAR = 2100
	}

	private lateinit var binding: MonthYearPickerBinding

	private var listener: DatePickerDialog.OnDateSetListener? = null

	fun setListener(listener: DatePickerDialog.OnDateSetListener?) {
		this.listener = listener
	}

	override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
		binding = MonthYearPickerBinding.inflate(requireActivity().layoutInflater)
		val cal: Calendar = Calendar.getInstance().apply { time = date }

		binding.pickerMonth.run {
			minValue = 0
			maxValue = 11
			value = cal.get(Calendar.MONTH)
			displayedValues = arrayOf("1","2","3","4","5","6","7",
				"8","9","10","11","12")
		}

		binding.pickerYear.run {
			val year = cal.get(Calendar.YEAR)
			minValue = year
			maxValue = MAX_YEAR
			value = year

		}

		return AlertDialog.Builder(requireContext())
			.setView(binding.root)
			.setCancelable(true)
			.setPositiveButton("Chọn") { _, _ -> listener?.onDateSet(null, binding.pickerMonth.value, binding.pickerYear.value, 1) }
			.setNegativeButton("Trở về") { _, _ -> dialog?.cancel() }
			.create()
	}
}