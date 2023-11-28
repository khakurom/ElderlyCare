package com.project.elderlyhealthcare.presentation.fragment.main.event

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.databinding.FragmentExerciseEventBinding
import com.project.elderlyhealthcare.domain.models.ExerciseEventModel
import com.project.elderlyhealthcare.presentation.adapter.ExerciseAdapter
import com.project.elderlyhealthcare.presentation.adapter.OnItemClearListener
import com.project.elderlyhealthcare.presentation.adapter.OnItemSelectListener
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.EventViewModel
import com.project.elderlyhealthcare.utils.SingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.notifyAll

@AndroidEntryPoint
class ExerciseEventFragment :
    BaseFragment<EventViewModel, FragmentExerciseEventBinding>(R.layout.fragment_exercise_event) {
    override fun variableId(): Int = BR.exerciseViewModel

    override fun createViewModel(): Lazy<EventViewModel> = activityViewModels()
    override fun bindView(view: View): FragmentExerciseEventBinding {
        return FragmentExerciseEventBinding.bind(view)
    }

    override fun init() {
        super.init()
        val adapter : ExerciseAdapter by lazy {
            ExerciseAdapter().apply {
                onItemSelectListener = object : OnItemSelectListener<ExerciseEventModel> {
                    override fun onItemSelected(item: ExerciseEventModel, position: Int) {
                        Toast.makeText(requireContext(), item.id.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
                onIvClearListener = object : OnItemClearListener<ExerciseEventModel> {
                    override fun onItemClear(item: ExerciseEventModel, position: Int) {
                        Log.d("khatag", item.id.toString())
                        viewModel?.deleteExerciseEvent(item.id)
                    }
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
            exerciseRcvListExercise.adapter = adapter


        }
        observeExerciseEventList(adapter)
        getExerciseEvent()
    }

    private fun getExerciseEvent() {
        viewModel?.getExerciseEvent()
    }

    private fun observeExerciseEventList (adapter: ExerciseAdapter) {
        viewModel?.listExerciseEvent?.observe(this) {
            adapter.submitList(it)
            Log.d("khatag", "submit")
        }
    }


}