package com.project.elderlyhealthcare.presentation.fragment.main.event

import android.app.DatePickerDialog
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.data.models.ExerciseEventEntity
import com.project.elderlyhealthcare.databinding.FragmentAddExerciseBinding
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.EventViewModel
import com.project.elderlyhealthcare.utils.Constant.listHour
import com.project.elderlyhealthcare.utils.Constant.listMinutes
import com.project.elderlyhealthcare.utils.SingleClickListener
import com.project.elderlyhealthcare.utils.Utils
import com.project.elderlyhealthcare.utils.Utils.compareToCurrentTime
import com.project.elderlyhealthcare.utils.Utils.formatTimeNumberPicker
import com.project.elderlyhealthcare.utils.Utils.getCurrentTime
import com.project.elderlyhealthcare.utils.Utils.hideKeyboard
import com.project.elderlyhealthcare.utils.Utils.showDialog
import com.project.elderlyhealthcare.utils.Utils.uncheckedRepeatDay
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class AddExerciseFragment :
    BaseFragment<EventViewModel, FragmentAddExerciseBinding>(R.layout.fragment_add_exercise) {

    private lateinit var dayRepeatList: MutableList<String?>
    override fun variableId(): Int = BR.addExViewModel

    override fun createViewModel(): Lazy<EventViewModel> = activityViewModels()

    override fun bindView(view: View): FragmentAddExerciseBinding {
        return FragmentAddExerciseBinding.bind(view)
    }

    override fun init() {
        super.init()
        binding.apply {
            addExerciseFrCsBar.customAppBarIvBack.setOnClickListener(object :
                SingleClickListener() {
                override fun onSingleClick(v: View) {
                    backToPreScreen()
                }
            })
            layoutAddEx.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    v.hideKeyboard()
                }
            })
            addExBtAddEvent.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    createExerciseEvent()
                }
            })
            layoutDatePicker.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    selectDate()
                }
            })
            pickerHour.textColor = ContextCompat.getColor(requireContext(), R.color.black)
            pickerMinute.textColor = ContextCompat.getColor(requireContext(), R.color.black)

            Utils.settingDayPicker(
                requireContext(),
                toggleBtMon,
                toggleBtTu,
                toggleBtWe,
                toggleBtTh,
                toggleBtFr,
                toggleBtSa,
                toggleBtSun
            )
            getCurrentTime(addExTvDate, requireContext())

            settingTimePicker()
            getValueDayRepeat()

        }
    }

    private fun settingTimePicker() {
        binding.apply {
            pickerHour.maxValue = 23
            pickerMinute.maxValue = 59

            pickerHour.displayedValues = listHour
            pickerMinute.displayedValues = listMinutes
        }
    }


    private fun getValueDayRepeat() {
        dayRepeatList = mutableListOf()
        binding.apply {
            toggleBtMon.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    dayRepeatList.add("T2")
                    getCurrentTime(addExTvDate, requireContext())
                } else {
                    dayRepeatList.remove("T2")
                }
            }
            toggleBtTu.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    dayRepeatList.add("T3")
                    getCurrentTime(addExTvDate, requireContext())
                } else {
                    dayRepeatList.remove("T3")
                }
            }
            toggleBtWe.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    dayRepeatList.add("T4")
                    getCurrentTime(addExTvDate, requireContext())
                } else {
                    dayRepeatList.remove("T4")
                }
            }
            toggleBtTh.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    dayRepeatList.add("T5")
                    getCurrentTime(addExTvDate, requireContext())
                } else {
                    dayRepeatList.remove("T5")
                }
            }

            toggleBtFr.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    dayRepeatList.add("T6")
                    getCurrentTime(addExTvDate, requireContext())
                } else {
                    dayRepeatList.remove("T6")
                }
            }
            toggleBtSa.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    dayRepeatList.add("T7")
                    getCurrentTime(addExTvDate, requireContext())
                } else {
                    dayRepeatList.remove("T7")
                }
            }
            toggleBtSun.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    dayRepeatList.add("CN")
                    getCurrentTime(addExTvDate, requireContext())
                } else {
                    dayRepeatList.remove("CN")
                }
            }
        }
    }

    private fun createExerciseEvent() {
        binding.apply {
            if (addExEdtExerciseName.text?.trim().toString().isEmpty()) {
                showDialog(requireContext(), "Vui lòng đặt tên bài tập thể dục")
            } else {
                if (compareToCurrentTime(
                        addExTvDate.text.trim().toString(),
                        formatTimeNumberPicker(pickerHour),
                        formatTimeNumberPicker(pickerMinute)
                    )
                ) {
                    showDialog(requireContext(), "Không thể đặt giờ trong quá khứ")
                } else {
                    val exerciseEvent = ExerciseEventEntity(
                        hour = formatTimeNumberPicker(pickerHour),
                        minutes = formatTimeNumberPicker(pickerMinute),
                        dayRepeat = dayRepeatList.distinct(),
                        dayBegin = addExTvDate.text.trim().toString(),
                        exerciseName = addExEdtExerciseName.text?.trim().toString(),
                        description = addExEdtDescription.text?.trim().toString(),
                        isOn = true
                    )
                    viewModel?.insertExerciseEvent(exerciseEvent)
                    backToPreScreen()
                }
            }
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
                    binding.addExTvDate.text = getString(
                        R.string.day_month_year,
                        selectedDay.toString(),
                        (selectedMonth + 1).toString(),
                        selectedYear.toString()
                    )
                    uncheckedRepeatDay(
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
}