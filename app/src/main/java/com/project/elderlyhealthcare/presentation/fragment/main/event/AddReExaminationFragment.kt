package com.project.elderlyhealthcare.presentation.fragment.main.event

import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.databinding.FragmentAddReExaminationBinding
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.EventViewModel
import com.project.elderlyhealthcare.utils.SingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddReExaminationFragment : BaseFragment<EventViewModel, FragmentAddReExaminationBinding>(R.layout.fragment_add_re_examination) {
	override fun variableId(): Int = BR.addReExViewModel

	override fun createViewModel(): Lazy<EventViewModel> = activityViewModels ()

	override fun bindView(view: View): FragmentAddReExaminationBinding {
		return FragmentAddReExaminationBinding.bind(view)
	}

	override fun init() {
		binding.apply {
			addReExFrCsBar.customAppBarIvBack.setOnClickListener(object : SingleClickListener() {
				override fun onSingleClick(v: View) {
					backToPreScreen()
				}
			})
			pickerHour.textColor = ContextCompat.getColor(requireContext(), R.color.black)
			pickerMinute.textColor = ContextCompat.getColor(requireContext(), R.color.black)
		}
		settingTimePicker()
	}


	private fun settingTimePicker() {
		binding.apply {
			pickerHour.maxValue = 23
			pickerMinute.maxValue = 59

			pickerHour.value = 6
			pickerMinute.value = 0
		}
	}

}