package com.project.elderlyhealthcare.presentation.fragment.main.event

import android.content.res.ColorStateList
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.databinding.FragmentAddExerciseBinding
import com.project.elderlyhealthcare.databinding.FragmentAddMedicineBinding
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.EventViewModel
import com.project.elderlyhealthcare.utils.SingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddMedicineFragment : BaseFragment<EventViewModel, FragmentAddMedicineBinding>(R.layout.fragment_add_medicine) {
	override fun variableId(): Int = BR.addMedicineViewModel

	override fun createViewModel(): Lazy<EventViewModel> = activityViewModels ()

	override fun bindView(view: View): FragmentAddMedicineBinding {
		return FragmentAddMedicineBinding.bind(view)
	}

	override fun init() {
		super.init()
		binding.apply {
			addMedicineFrCsBar.customAppBarIvBack.setOnClickListener(object : SingleClickListener() {
				override fun onSingleClick(v: View) {
					backToPreScreen()
				}
			})
			pickerHour.textColor = ContextCompat.getColor(requireContext(), R.color.black)
			pickerMinute.textColor = ContextCompat.getColor(requireContext(), R.color.black)
		}
		settingTimePicker()
		settingDayPicker()
	}

	private fun settingTimePicker() {
		binding.apply {
			pickerHour.maxValue = 23
			pickerMinute.maxValue = 59

			pickerHour.value = 6
			pickerMinute.value = 0
		}
	}

	private fun settingDayPicker () {

		val colorStateList = ColorStateList(
			arrayOf(
				intArrayOf(android.R.attr.state_checked), // Checked state
				intArrayOf(-android.R.attr.state_checked)  // Unchecked state
			),
			intArrayOf(
				ContextCompat.getColor(requireContext(),R.color.login_blue),   // Color when checked
				ContextCompat.getColor(requireContext(),R.color.blue),   // Color when uncheck
			)
		)
		binding.apply {
			toggleBtMon.backgroundTintList = colorStateList
			toggleBtTu.backgroundTintList = colorStateList
			toggleBtWe.backgroundTintList = colorStateList
			toggleBtTh.backgroundTintList = colorStateList
			toggleBtFr.backgroundTintList = colorStateList
			toggleBtSa.backgroundTintList = colorStateList
			toggleBtSun.backgroundTintList = colorStateList
		}
	}

}