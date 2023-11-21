package com.project.elderlyhealthcare.presentation.fragment.main.event

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.databinding.FragmentReExaminationEventBinding
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.EventViewModel
import com.project.elderlyhealthcare.utils.SingleClickListener


class ReExaminationEventFragment :  BaseFragment<EventViewModel, FragmentReExaminationEventBinding>(R.layout.fragment_re_examination_event) {
	override fun variableId(): Int = BR.reExaminationViewModel

	override fun createViewModel(): Lazy<EventViewModel> = activityViewModels()

	override fun bindView(view: View): FragmentReExaminationEventBinding {
		return FragmentReExaminationEventBinding.bind(view)
	}

	override fun init() {
		super.init()

		binding.apply {
			reExFrCsBar.customAppBarIvBack.setOnClickListener(object : SingleClickListener() {
				override fun onSingleClick(v: View) {
					backToPreScreen()
				}
			})

			reExFabAdd.setOnClickListener(object : SingleClickListener() {
				override fun onSingleClick(v: View) {
					findNavController().navigate(ReExaminationEventFragmentDirections.actionReExaminationEventFragmentToAddReExaminationFragment())
				}
			})
		}

	}

}