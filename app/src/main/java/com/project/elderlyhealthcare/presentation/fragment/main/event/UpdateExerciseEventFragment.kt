package com.project.elderlyhealthcare.presentation.fragment.main.event

import android.app.DatePickerDialog
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.data.models.ExerciseEventEntity
import com.project.elderlyhealthcare.databinding.FragmentUpdateExerciseEventBinding
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.EventViewModel
import com.project.elderlyhealthcare.utils.Constant
import com.project.elderlyhealthcare.utils.SingleClickListener
import com.project.elderlyhealthcare.utils.Utils
import com.project.elderlyhealthcare.utils.Utils.getCurrentTime
import com.project.elderlyhealthcare.utils.Utils.settingDayPicker
import java.util.Calendar


class UpdateExerciseEventFragment :
    BaseFragment<EventViewModel, FragmentUpdateExerciseEventBinding>(R.layout.fragment_update_exercise_event) {

    private lateinit var dayRepeatList: MutableList<String?>
    override fun variableId(): Int = BR.updateExerciseViewModel

    override fun createViewModel(): Lazy<EventViewModel> = activityViewModels()

    override fun bindView(view: View): FragmentUpdateExerciseEventBinding {
        return FragmentUpdateExerciseEventBinding.bind(view)
    }

    private val navArgs: UpdateExerciseEventFragmentArgs by navArgs()


    override fun init() {
        super.init()
        binding.apply {
            updateExerciseFrCsBar.customAppBarIvBack.setOnClickListener(object :
                SingleClickListener() {
                override fun onSingleClick(v: View) {
                    backToPreScreen()
                }
            })
            pickerHour.textColor = ContextCompat.getColor(requireContext(), R.color.black)
            pickerMinute.textColor = ContextCompat.getColor(requireContext(), R.color.black)
            settingDayPicker(
                requireContext(),
                toggleBtMon,
                toggleBtTu,
                toggleBtWe,
                toggleBtTh,
                toggleBtFr,
                toggleBtSa,
                toggleBtSun
            )
            Utils.settingDayRepeat(
                navArgs.exerciseEventModel.dayRepeat, toggleBtMon,
                toggleBtTu,
                toggleBtWe,
                toggleBtTh,
                toggleBtFr,
                toggleBtSa,
                toggleBtSun
            )
            updateExEdtExerciseName.setText(navArgs.exerciseEventModel.exerciseName)
            updateExEdtDescription.setText(navArgs.exerciseEventModel.description)
            layoutDatePicker.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    selectDate()
                }
            })

            updateExBtUpdateEvent.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    updateExerciseEvent()
                }
            })
            getCurrentTime(updateExTvDate, requireContext())
            getValueDayRepeat()
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
                        Utils.formatTime(pickerHour),
                        Utils.formatTime(pickerMinute)
                    )
                ) {
                    Utils.showDialog(requireContext(), "Không thể đặt giờ trong quá khứ")
                } else {
                    val exerciseEvent = ExerciseEventEntity(
                        id = navArgs.exerciseEventModel.id,
                        hour = Utils.formatTime(pickerHour),
                        minutes = Utils.formatTime(pickerMinute),
                        dayRepeat = dayRepeatList.distinct(),
                        dayBegin = updateExTvDate.text.trim().toString(),
                        exerciseName = updateExEdtExerciseName.text?.trim().toString(),
                        description = updateExEdtDescription.text?.trim().toString()
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
                    Utils.uncheckedRepeatDay(
                        toggleBtMon,
                        toggleBtTu,
                        toggleBtWe,
                        toggleBtTh,
                        toggleBtFr,
                        toggleBtSa,
                        toggleBtSun
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

    private fun getValueDayRepeat() {
        dayRepeatList = navArgs.exerciseEventModel.dayRepeat as MutableList<String?>
        binding.apply {
            toggleBtMon.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    dayRepeatList.add("T2")
                    getCurrentTime(updateExTvDate, requireContext())
                } else {
                    dayRepeatList.remove("T2")
                }
            }
            toggleBtTu.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    dayRepeatList.add("T3")
                    getCurrentTime(updateExTvDate, requireContext())
                } else {
                    dayRepeatList.remove("T3")
                }
            }
            toggleBtWe.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    dayRepeatList.add("T4")
                    getCurrentTime(updateExTvDate, requireContext())
                } else {
                    dayRepeatList.remove("T4")
                }
            }
            toggleBtTh.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    dayRepeatList.add("T5")
                    getCurrentTime(updateExTvDate, requireContext())
                } else {
                    dayRepeatList.remove("T5")
                }
            }

            toggleBtFr.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    dayRepeatList.add("T6")
                    getCurrentTime(updateExTvDate, requireContext())
                } else {
                    dayRepeatList.remove("T6")
                }
            }
            toggleBtSa.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    dayRepeatList.add("T7")
                    getCurrentTime(updateExTvDate, requireContext())
                } else {
                    dayRepeatList.remove("T7")
                }
            }
            toggleBtSun.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    dayRepeatList.add("CN")
                    getCurrentTime(updateExTvDate, requireContext())
                } else {
                    dayRepeatList.remove("CN")
                }
            }
        }
    }

}