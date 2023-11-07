package com.project.elderlyhealthcare.presentation.fragment.main.event

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.widget.ToggleButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.databinding.FragmentAddExerciseBinding
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.EventViewModel
import com.project.elderlyhealthcare.utils.SingleClickListener


class AddExerciseFragment : BaseFragment<EventViewModel, FragmentAddExerciseBinding>(R.layout.fragment_add_exercise){
	override fun variableId(): Int = BR.addExViewModel

	override fun createViewModel(): Lazy<EventViewModel> = activityViewModels()

	override fun bindView(view: View): FragmentAddExerciseBinding {
		return FragmentAddExerciseBinding.bind(view)
	}

	override fun init() {
		super.init()
		binding.apply {
			addExerciseFrCsBar.customAppBarIvBack.setOnClickListener(object : SingleClickListener() {
				override fun onSingleClick(v: View) {
					backToPreScreen()
				}
			})
			settingTimePicker ()
			settingDayPicker ()
		}
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