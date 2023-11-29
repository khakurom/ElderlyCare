package com.project.elderlyhealthcare.presentation.fragment.main.event

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.view.View
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.data.models.ExerciseEventEntity
import com.project.elderlyhealthcare.databinding.FragmentAddExerciseBinding
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.EventViewModel
import com.project.elderlyhealthcare.utils.Constant.listHour
import com.project.elderlyhealthcare.utils.Constant.listMinutes
import com.project.elderlyhealthcare.utils.SingleClickListener
import com.project.elderlyhealthcare.utils.Utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

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

            settingTimePicker()
            settingDayPicker()
            getValueDayRepeat()
            getCurrentTime()

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

    private fun settingDayPicker() {

        val colorStateList = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked), // Checked state
                intArrayOf(-android.R.attr.state_checked)  // Unchecked state
            ),
            intArrayOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.login_blue
                ),   // Color when checked
                ContextCompat.getColor(requireContext(), R.color.blue),   // Color when uncheck
            )
        )
        binding.apply {
            toggleBtMon.backgroundTintList = colorStateList
            toggleBtTu.backgroundTintList = colorStateList
            toggleBtWe.backgroundTintList = colorStateList
            toggleBtTh.backgroundTintList = colorStateList
            toggleBtFr.backgroundTintList = colorStateList
            toggleBtSa.backgroundTintList = colorStateList
            toggleBtSun.backgroundTintList = colorStateList
        }
    }

    private fun getValueDayRepeat() {
        dayRepeatList = mutableListOf()
        binding.apply {
            toggleBtMon.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    dayRepeatList.add("T2")
                    getCurrentTime()
                } else {
                    dayRepeatList.remove("T2")
                }
            }

            toggleBtTu.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    dayRepeatList.add("T3")
                    getCurrentTime()
                } else {
                    dayRepeatList.remove("T3")
                }
            }
            toggleBtWe.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    dayRepeatList.add("T4")
                    getCurrentTime()
                } else {
                    dayRepeatList.remove("T4")
                }
            }
            toggleBtTh.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    dayRepeatList.add("T5")
                    getCurrentTime()
                } else {
                    dayRepeatList.remove("T5")
                }
            }

            toggleBtFr.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    dayRepeatList.add("T6")
                    getCurrentTime()
                } else {
                    dayRepeatList.remove("T6")
                }
            }
            toggleBtSa.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    dayRepeatList.add("T7")
                    getCurrentTime()
                } else {
                    dayRepeatList.remove("T7")
                }
            }
            toggleBtSun.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    dayRepeatList.add("CN")
                    getCurrentTime()
                } else {
                    dayRepeatList.remove("CN")
                }
            }
        }
    }

    private fun createExerciseEvent() {
        binding.apply {
            if (addExEdtExerciseName.text?.trim().toString().isEmpty()) {
                showDialog("Vui lòng đặt tên bài tập thể dục")
            } else {
                if (compareToCurrentTime(addExTvDate.text.trim().toString(), formatTime(pickerHour), formatTime(pickerMinute))) {
                    showDialog("Không thể đặt giờ trong quá khứ")
                } else {
                    val exerciseEvent = ExerciseEventEntity(
                        hour = formatTime(pickerHour),
                        minutes = formatTime(pickerMinute) ,
                        dayRepeat = dayRepeatList.distinct(),
                        dayBegin = addExTvDate.text.trim().toString(),
                        exerciseName = addExEdtExerciseName.text?.trim().toString(),
                        description = addExEdtDescription.text?.trim().toString()
                    )
                    viewModel?.insertExerciseEvent(exerciseEvent)
                    try {
                        findNavController().navigate(AddExerciseFragmentDirections.actionAddExerciseFragmentToExerciseEventFragment())
                    } catch (_: Exception) {
                    }
                    backToPreScreen()
                }
            }
        }
    }

    private fun compareToCurrentTime (date : String, hour : String, minutes : String) : Boolean {
        val dateTime = "$date $hour:$minutes"
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val targetDateTime = Calendar.getInstance()

        targetDateTime.time = dateFormat.parse(dateTime) ?: Date()
        val currentDateTime = Calendar.getInstance()
        return currentDateTime.time.after(targetDateTime.time)
    }

    private fun formatTime (editText: NumberPicker): String {
        val time  = editText.value.toString()
        return if (time.length == 1) "0$time" else time
    }

    private fun showDialog(message: String) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        with(alertDialogBuilder) {
            setMessage(message)
            setCancelable(true)
            setPositiveButton("OK") { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
            }
            setNegativeButton("Trở về") { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
            }
        }

        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun getCurrentTime() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DATE)
        binding.addExTvDate.text = getString(
            R.string.day_month_year,
            day.toString(),
            (month + 1).toString(),
            year.toString()
        )
    }

    private fun selectDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                binding.addExTvDate.text = getString(
                    R.string.day_month_year,
                    selectedDay.toString(),
                    (selectedMonth + 1).toString(),
                    selectedYear.toString()
                )
                uncheckedRepeatDay()
            },
            year,
            month,
            day
        )

        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun uncheckedRepeatDay() {
        binding.apply {
            toggleBtMon.isChecked = false
            toggleBtTu.isChecked = false
            toggleBtWe.isChecked = false
            toggleBtTh.isChecked = false
            toggleBtFr.isChecked = false
            toggleBtSa.isChecked = false
            toggleBtSun.isChecked = false
        }
    }


}