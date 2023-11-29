package com.project.elderlyhealthcare.presentation.fragment.main.event

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.databinding.FragmentExerciseEventBinding
import com.project.elderlyhealthcare.domain.models.ExerciseEventModel
import com.project.elderlyhealthcare.presentation.adapter.ExerciseAdapter
import com.project.elderlyhealthcare.presentation.adapter.OnItemRemoveListener
import com.project.elderlyhealthcare.presentation.adapter.OnItemSelectListener
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.EventViewModel
import com.project.elderlyhealthcare.utils.SingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseEventFragment :
    BaseFragment<EventViewModel, FragmentExerciseEventBinding>(R.layout.fragment_exercise_event) {
    private val exerciseAdapter = ExerciseAdapter()
    override fun variableId(): Int = BR.exerciseViewModel

    override fun createViewModel(): Lazy<EventViewModel> = activityViewModels()
    override fun bindView(view: View): FragmentExerciseEventBinding {
        return FragmentExerciseEventBinding.bind(view)
    }

    override fun init() {
        super.init()
        exerciseAdapter.apply {
            onItemSelectListener = object : OnItemSelectListener<ExerciseEventModel> {
                override fun onItemSelected(item: ExerciseEventModel, position: Int) {
                }
            }
            onItemRemoveListener = object : OnItemRemoveListener<ExerciseEventModel> {
                override fun onItemRemove(item: ExerciseEventModel, position: Int) {
                    viewModel?.deleteExerciseEvent(item.id)
                }
            }
        }

        binding.apply {
            exerciseFrCsBar.customAppBarIvBack.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    backToPreScreen()
                }
            })

            exerciseFabAdd.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    try {
                        findNavController().navigate(ExerciseEventFragmentDirections.actionExerciseEventFragmentToAddExerciseFragment())
                    } catch (_: Exception) {
                    }
                }
            })
            exerciseRcvListExercise.adapter = exerciseAdapter


        }
        getExerciseEvent()
    }

    private fun getExerciseEvent() {
        viewModel?.getExerciseEvent()
    }



}