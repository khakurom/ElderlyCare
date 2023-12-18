package com.project.elderlyhealthcare.presentation.fragment.main.event

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.data.models.ExerciseEventEntity
import com.project.elderlyhealthcare.databinding.FragmentUpdateExerciseEventBinding
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.EventViewModel
import com.project.elderlyhealthcare.utils.AlarmReceiver
import com.project.elderlyhealthcare.utils.Constant
import com.project.elderlyhealthcare.utils.OnFragmentInteractionListener
import com.project.elderlyhealthcare.utils.SingleClickListener
import com.project.elderlyhealthcare.utils.Utils
import com.project.elderlyhealthcare.utils.Utils.hideKeyboard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.util.Calendar
import java.util.Random


class UpdateExerciseEventFragment :
    BaseFragment<EventViewModel, FragmentUpdateExerciseEventBinding>(R.layout.fragment_update_exercise_event) {

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var dayRepeatList: MutableList<String?>
    override fun variableId(): Int = BR.updateExerciseViewModel

    override fun createViewModel(): Lazy<EventViewModel> = activityViewModels()

    override fun bindView(view: View): FragmentUpdateExerciseEventBinding {
        return FragmentUpdateExerciseEventBinding.bind(view)
    }

    private val navArgs: UpdateExerciseEventFragmentArgs by navArgs()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        }
    }

    override fun init() {
        super.init()
        listener?.updateBottomNavVisible(true)
        binding.apply {
            updateExerciseFrCsBar.customAppBarIvBack.setOnClickListener(object :
                SingleClickListener() {
                override fun onSingleClick(v: View) {
                    backToPreScreen()
                }
            })
            exerciseEventModel = navArgs.exerciseEventModel
            pickerHour.textColor = ContextCompat.getColor(requireContext(), R.color.black)
            pickerMinute.textColor = ContextCompat.getColor(requireContext(), R.color.black)

            layoutDatePicker.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    selectDate()
                }
            })

            layoutUpdateEx.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    v.hideKeyboard()
                }
            })

            updateExBtUpdateEvent.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    updateExerciseEvent()
                }
            })
            settingTimePicker()
        }
    }

    private fun updateExerciseEvent() {
        binding.apply {
            if (updateExEdtExerciseName.text?.trim().toString().isEmpty()) {
                Utils.showDialog(requireContext(), "Vui lòng đặt tên bài tập thể dục")
            } else {
                if (Utils.compareToCurrentTime(
                        updateExTvDate.text.trim().toString(),
                        Utils.formatTimeNumberPicker(pickerHour),
                        Utils.formatTimeNumberPicker(pickerMinute)
                    )
                ) {
                    Utils.showDialog(requireContext(), "Không thể đặt giờ trong quá khứ")
                } else {
                    cancelAlarm()
                    val exerciseEvent = ExerciseEventEntity(
                        id = navArgs.exerciseEventModel.id,
                        hour = Utils.formatTimeNumberPicker(pickerHour),
                        minutes = Utils.formatTimeNumberPicker(pickerMinute),
                        dayBegin = updateExTvDate.text.trim().toString(),
                        exerciseName = updateExEdtExerciseName.text?.trim().toString(),
                        description = updateExEdtDescription.text?.trim().toString(),
                        isOn = true,
                        uniqueIntent = Random().nextInt()
                    )
                    viewModel?.updateExerciseEvent(exerciseEvent)
                    backToPreScreen()
                }
            }
        }
    }

    private fun settingTimePicker() {
        binding.apply {
            pickerHour.maxValue = 23
            pickerMinute.maxValue = 59

            pickerHour.value = navArgs.exerciseEventModel.hour!!.toInt()
            pickerMinute.value = navArgs.exerciseEventModel.minutes!!.toInt()

            pickerHour.displayedValues = Constant.listHour
            pickerMinute.displayedValues = Constant.listMinutes
        }
    }

    private fun selectDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        binding.apply {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    binding.updateExTvDate.text = getString(
                        R.string.day_month_year,
                        selectedDay.toString(),
                        (selectedMonth + 1).toString(),
                        selectedYear.toString()
                    )
                },
                year,
                month,
                day
            )
            datePickerDialog.datePicker.minDate = System.currentTimeMillis()
            datePickerDialog.show()
        }
    }


    private fun cancelAlarm() = runBlocking {
        val alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            activity?.applicationContext,
            navArgs.exerciseEventModel.uniqueIntent!!,
            intent,
            PendingIntent.FLAG_MUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }
}