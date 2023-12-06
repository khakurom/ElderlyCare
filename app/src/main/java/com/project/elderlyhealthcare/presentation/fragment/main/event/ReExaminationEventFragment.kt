package com.project.elderlyhealthcare.presentation.fragment.main.event

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.databinding.FragmentReExaminationEventBinding
import com.project.elderlyhealthcare.domain.models.ExerciseEventModel
import com.project.elderlyhealthcare.domain.models.ReExaminationEventModel
import com.project.elderlyhealthcare.presentation.adapter.OnItemRemoveListener
import com.project.elderlyhealthcare.presentation.adapter.OnItemSelectListener
import com.project.elderlyhealthcare.presentation.adapter.ReExaminationAdapter
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.EventViewModel
import com.project.elderlyhealthcare.utils.SingleClickListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ReExaminationEventFragment :
    BaseFragment<EventViewModel, FragmentReExaminationEventBinding>(R.layout.fragment_re_examination_event) {
    override fun variableId(): Int = BR.reExaminationViewModel

    override fun createViewModel(): Lazy<EventViewModel> = activityViewModels()

    override fun bindView(view: View): FragmentReExaminationEventBinding {
        return FragmentReExaminationEventBinding.bind(view)
    }

    override fun init() {
        super.init()

        val reExAdapter: ReExaminationAdapter by lazy {
            ReExaminationAdapter().apply {
                onItemSelectListener = object : OnItemSelectListener<ReExaminationEventModel> {
                    override fun onItemSelected(item: ReExaminationEventModel, position: Int) {
                        findNavController().navigate(
                            ReExaminationEventFragmentDirections.actionReExaminationEventFragmentToUpdateReExaminationFragment(
                                item
                            )
                        )
                    }
                }
                onItemRemoveListener = object : OnItemRemoveListener<ReExaminationEventModel> {
                    override fun onItemRemove(item: ReExaminationEventModel, position: Int) {
                        viewModel?.deleteReExEvent(item.id)
                    }
                }
            }
        }

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

            reExRcvListExercise.adapter = reExAdapter
        }
        getReExEvent()
    }

    private fun getReExEvent() {
        viewModel?.getReExEvent()
    }

}