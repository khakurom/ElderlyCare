package com.project.elderlyhealthcare.presentation.fragment.main.event

import android.app.DatePickerDialog
import android.content.Context
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
import com.project.elderlyhealthcare.utils.OnFragmentInteractionListener
import com.project.elderlyhealthcare.utils.SingleClickListener
import com.project.elderlyhealthcare.utils.Utils.compareToCurrentTime
import com.project.elderlyhealthcare.utils.Utils.formatTimeNumberPicker
import com.project.elderlyhealthcare.utils.Utils.getCurrentTime
import com.project.elderlyhealthcare.utils.Utils.hideKeyboard
import com.project.elderlyhealthcare.utils.Utils.showDialog
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Random

@AndroidEntryPoint
class AddExerciseFragment :
    BaseFragment<EventViewModel, FragmentAddExerciseBinding>(R.layout.fragment_add_exercise) {
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var dayRepeatList: MutableList<String?>
    override fun variableId(): Int = BR.addExViewModel

    override fun createViewModel(): Lazy<EventViewModel> = activityViewModels()

    override fun bindView(view: View): FragmentAddExerciseBinding {
        return FragmentAddExerciseBinding.bind(view)
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

            getCurrentTime(addExTvDate, requireContext())

            settingTimePicker()
            listener?.updateBottomNavVisible(true)
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
                        dayBegin = addExTvDate.text.trim().toString(),
                        exerciseName = addExEdtExerciseName.text?.trim().toString(),
                        description = addExEdtDescription.text?.trim().toString(),
                        isOn = true,
                        uniqueIntent = Random().nextInt()
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