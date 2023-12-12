package com.project.elderlyhealthcare.presentation.fragment.main.event

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.databinding.FragmentExerciseEventBinding
import com.project.elderlyhealthcare.domain.models.ExerciseEventModel
import com.project.elderlyhealthcare.presentation.adapter.ExerciseAdapter
import com.project.elderlyhealthcare.presentation.adapter.OnItemRemoveListener
import com.project.elderlyhealthcare.presentation.adapter.OnItemSelectListener
import com.project.elderlyhealthcare.presentation.adapter.OnItemTurnOnListener
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.EventViewModel
import com.project.elderlyhealthcare.utils.AlarmReceiver
import com.project.elderlyhealthcare.utils.Constant
import com.project.elderlyhealthcare.utils.SingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.TimeZone

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
        val exerciseAdapter: ExerciseAdapter by lazy {
            ExerciseAdapter().apply {
                onItemSelectListener = object : OnItemSelectListener<ExerciseEventModel> {
                    override fun onItemSelected(item: ExerciseEventModel, position: Int) {
                        findNavController().navigate(
                            ExerciseEventFragmentDirections.actionExerciseEventFragmentToUpdateExerciseEventFragment(
                                item
                            )
                        )
                    }
                }
                onItemRemoveListener = object : OnItemRemoveListener<ExerciseEventModel> {
                    override fun onItemRemove(item: ExerciseEventModel, position: Int) {
                        viewModel?.deleteExerciseEvent(item.id)
                    }
                }

                onItemTurnOnListener = object : OnItemTurnOnListener<ExerciseEventModel> {
                    override fun onItemTurnOn(item: ExerciseEventModel, position: Int) {
                        createAlarm(item)
                    }

                    override fun onItemTurnOff(item: ExerciseEventModel, position: Int) {
                        cancelAlarm()
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
            exerciseRcvListExercise.adapter = exerciseAdapter


        }
        getExerciseEvent()
    }

    private fun createAlarm(item: ExerciseEventModel) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, item.hour!!.toInt())
        calendar.set(Calendar.MINUTE, item.minutes!!.toInt())
        calendar.set(Calendar.MILLISECOND, 0)
        calendar.set(Calendar.SECOND, 0)

        val alarmManager = activity?.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        intent.putExtra(Constant.KEY_EXERCISE_EVENT, item)
        val pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE)

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }

    private fun cancelAlarm() {
        val alarmManager = activity?.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE)
        alarmManager.cancel(pendingIntent)
    }

    private fun getExerciseEvent() {
        viewModel?.getExerciseEvent()
    }
}