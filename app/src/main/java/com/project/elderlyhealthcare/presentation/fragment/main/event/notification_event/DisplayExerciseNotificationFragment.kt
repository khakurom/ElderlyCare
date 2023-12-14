package com.project.elderlyhealthcare.presentation.fragment.main.event.notification_event

import android.view.View
import androidx.fragment.app.activityViewModels
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.databinding.FragmentDisplayExerciseNotificationBinding
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.EventViewModel
import com.project.elderlyhealthcare.utils.SingleClickListener

class DisplayExerciseNotificationFragment :  BaseFragment<EventViewModel, FragmentDisplayExerciseNotificationBinding>(R.layout.fragment_display_exercise_notification) {
    override fun variableId(): Int = BR.displayExViewModel

    override fun createViewModel(): Lazy<EventViewModel> = activityViewModels ()

    override fun bindView(view: View): FragmentDisplayExerciseNotificationBinding {
        return FragmentDisplayExerciseNotificationBinding.bind(view)
    }

    override fun init() {
        super.init()
        binding.apply {
            displayExBtBack.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    backToPreScreen()
                }
            })
        }
    }

}