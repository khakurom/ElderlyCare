package com.project.elderlyhealthcare.presentation.fragment.main.event

import android.content.Intent
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.databinding.FragmentEventBinding
import com.project.elderlyhealthcare.presentation.activity.NotLoginActivity
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.EventViewModel
import com.project.elderlyhealthcare.utils.SingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventFragment : BaseFragment<EventViewModel, FragmentEventBinding>(R.layout.fragment_event) {
	override fun variableId(): Int = BR.eventViewModel

	override fun createViewModel(): Lazy<EventViewModel> = activityViewModels()

	override fun bindView(view: View): FragmentEventBinding {
		return FragmentEventBinding.bind(view)
	}

	override fun init() {
		super.init()
		binding.apply {
			eventLayoutExerciseEvent.setOnClickListener(object : SingleClickListener(){
				override fun onSingleClick(v: View) {
					try {
						findNavController().navigate(EventFragmentDirections.actionEventFragmentToExerciseEventFragment())
					} catch (_: Exception) {
					}

				}
			})

			eventLayoutMedicineEvent.setOnClickListener(object : SingleClickListener(){
				override fun onSingleClick(v: View) {
					try {
						findNavController().navigate(EventFragmentDirections.actionEventFragmentToMedicineEventFragment())
					} catch (_: Exception) {
					}
				}
			})

			eventLayoutReExaminationEvent.setOnClickListener(object : SingleClickListener(){
				override fun onSingleClick(v: View) {
					try {
						findNavController().navigate(EventFragmentDirections.actionEventFragmentToReExaminationEventFragment())
					} catch (_: Exception) {
					}

				}
			})
		}
	}

}