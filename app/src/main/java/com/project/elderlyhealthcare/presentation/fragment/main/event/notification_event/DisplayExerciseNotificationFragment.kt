package com.project.elderlyhealthcare.presentation.fragment.main.event.notification_event

import android.content.Context
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.databinding.FragmentDisplayExerciseNotificationBinding
import com.project.elderlyhealthcare.domain.models.ExerciseEventModel
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.EventViewModel
import com.project.elderlyhealthcare.utils.OnFragmentInteractionListener
import com.project.elderlyhealthcare.utils.SingleClickListener
import com.project.elderlyhealthcare.utils.Utils

class DisplayExerciseNotificationFragment :  BaseFragment<EventViewModel, FragmentDisplayExerciseNotificationBinding>(R.layout.fragment_display_exercise_notification) {

    private val navArgs : DisplayExerciseNotificationFragmentArgs by navArgs()
    private var listener: OnFragmentInteractionListener? = null
    override fun variableId(): Int = BR.displayExViewModel


    override fun createViewModel(): Lazy<EventViewModel> = activityViewModels ()

    override fun bindView(view: View): FragmentDisplayExerciseNotificationBinding {
        return FragmentDisplayExerciseNotificationBinding.bind(view)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        }
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
        listener?.updateBottomNavVisible(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        listener?.updateBottomNavVisible(false)
    }

}