package com.project.elderlyhealthcare.presentation.fragment.main.event

import android.app.DatePickerDialog
import android.content.res.ColorStateList
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.databinding.FragmentAddMedicineBinding
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.EventViewModel
import com.project.elderlyhealthcare.utils.SingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class AddMedicineFragment :
    BaseFragment<EventViewModel, FragmentAddMedicineBinding>(R.layout.fragment_add_medicine) {
    override fun variableId(): Int = BR.addMedicineViewModel

    override fun createViewModel(): Lazy<EventViewModel> = activityViewModels()

    override fun bindView(view: View): FragmentAddMedicineBinding {
        return FragmentAddMedicineBinding.bind(view)
    }

    override fun init() {
        super.init()
        binding.apply {
            addMedicineFrCsBar.customAppBarIvBack.setOnClickListener(object :
                SingleClickListener() {
                override fun onSingleClick(v: View) {
                    backToPreScreen()
                }
            })

            addMedicineLayoutBeginDatePicker.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    selectBeginDate()
                }
            })

            addMedicineLayoutEndDatePicker.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    selectEndDate()
                }
            })
            pickerHour.textColor = ContextCompat.getColor(requireContext(), R.color.black)
            pickerMinute.textColor = ContextCompat.getColor(requireContext(), R.color.black)
        }
        settingTimePicker()
        settingDayPicker()
        getCurrentDate()
    }

    private fun settingTimePicker() {
        binding.apply {
            pickerHour.maxValue = 23
            pickerMinute.maxValue = 59

            pickerHour.value = 6
            pickerMinute.value = 0
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

    private fun getCurrentDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DATE)
        binding.addMedicineTvBeginDate.text = getString(
            R.string.day_month_year,
            day.toString(),
            (month + 1).toString(),
            year.toString()
        )
    }

    private fun selectBeginDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                binding.addMedicineTvBeginDate.text = getString(
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

    private fun selectEndDate() {
        val result =
            getDayMonthYearFromCurrentDate(binding.addMedicineTvBeginDate.text.toString().trim())
        if (result != null) {
            val (day, month, year) = result
            val calendar = Calendar.getInstance().apply {
                set(year.toInt(), month.toInt() - 1, day.toInt()) // Adjust for zero-based month index
            }
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    val selectedCalendar = Calendar.getInstance().apply {
                        set(selectedYear, selectedMonth, selectedDay)
                    }

                    if (selectedCalendar.timeInMillis >= System.currentTimeMillis()) {
                        binding.addMedicineTvEndDate.text = getString(
                            R.string.day_month_year,
                            selectedDay.toString(),
                            (selectedMonth + 1).toString(),
                            selectedYear.toString()
                        )
                        uncheckedRepeatDay()
                    } else {
                        // Show a message or handle the case where the user selected a previous date
                        Toast.makeText(requireContext(), "Please select a future date", Toast.LENGTH_SHORT).show()
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            datePickerDialog.show()
        } else {
            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
        }
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

    private fun getDayMonthYearFromCurrentDate(dateString: String): Triple<String, String, String>? {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        try {
            val date = dateFormat.parse(dateString)
            val calendar = Calendar.getInstance()
            calendar.time = date ?: return null

            val day = calendar.get(Calendar.DAY_OF_MONTH).toString()
            val month =
                (calendar.get(Calendar.MONTH) + 1).toString() // Months are zero-based, so add 1
            val year = calendar.get(Calendar.YEAR).toString()

            return Triple(day, month, year)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

}