package com.project.elderlyhealthcare.presentation.fragment.main.event

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.databinding.FragmentExerciseEventBinding
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.EventViewModel
import com.project.elderlyhealthcare.utils.SingleClickListener


class ExerciseEventFragment : BaseFragment<EventViewModel, FragmentExerciseEventBinding>(R.layout.fragment_exercise_event) {
	override fun variableId(): Int = BR.exerciseViewModel

	override fun createViewModel(): Lazy<EventViewModel> = activityViewModels()
	override fun bindView(view: View): FragmentExerciseEventBinding {
		return FragmentExerciseEventBinding.bind(view)
	}

	override fun init() {
		super.init()
		binding.apply {
			exerciseFrCsBar.customAppBarIvBack.setOnClickListener(object : SingleClickListener() {
				override fun onSingleClick(v: View) {
					backToPreScreen()
				}
			})

			exerciseFabAdd.setOnClickListener(object : SingleClickListener() {
				override fun onSingleClick(v: View) {
					findNavController().navigate(ExerciseEventFragmentDirections.actionExerciseEventFragmentToAddExerciseFragment())
				}
			})


		}
	}


}