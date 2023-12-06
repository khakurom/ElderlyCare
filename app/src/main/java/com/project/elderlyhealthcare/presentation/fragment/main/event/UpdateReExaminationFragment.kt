package com.project.elderlyhealthcare.presentation.fragment.main.event

import android.app.DatePickerDialog
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.data.models.ReExaminationEventEntity
import com.project.elderlyhealthcare.databinding.FragmentUpdateReExaminationBinding
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.EventViewModel
import com.project.elderlyhealthcare.utils.Constant
import com.project.elderlyhealthcare.utils.SingleClickListener
import com.project.elderlyhealthcare.utils.Utils
import com.project.elderlyhealthcare.utils.Utils.compareToCurrentDay
import java.util.Calendar

class UpdateReExaminationFragment :
    BaseFragment<EventViewModel, FragmentUpdateReExaminationBinding>(R.layout.fragment_update_re_examination) {

    private val navArgs: UpdateReExaminationFragmentArgs by navArgs()

    override fun variableId(): Int = BR.updateReExViewModel

    override fun createViewModel(): Lazy<EventViewModel> = activityViewModels()

    override fun bindView(view: View): FragmentUpdateReExaminationBinding {
        return FragmentUpdateReExaminationBinding.bind(view)
    }

    override fun init() {
        super.init()
        binding.apply {
            reExEventModel = navArgs.reExEventModel
            updateReExFrCsBar.customAppBarIvBack.setOnClickListener(object :
                SingleClickListener() {
                override fun onSingleClick(v: View) {
                    backToPreScreen()
                }
            })

            updateReExTvDate.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    selectDate()
                }
            })

            updateReExBtUpdateReminder.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    updateReExEvent()
                }
            })

            updateReExTvDate.text =
                if (compareToCurrentDay(navArgs.reExEventModel.dayBegin!!)) getString(R.string.add_re_ex_pick_date) else navArgs.reExEventModel.dayBegin


            pickerHour.textColor = ContextCompat.getColor(requireContext(), R.color.black)
            pickerMinute.textColor = ContextCompat.getColor(requireContext(), R.color.black)

            settingTimePicker()
        }
    }

    private fun settingTimePicker() {
        binding.apply {
            pickerHour.maxValue = 23
            pickerMinute.maxValue = 59


            pickerHour.displayedValues = Constant.listHour
            pickerMinute.displayedValues = Constant.listMinutes
        }
    }

    private fun selectDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val minDateCalendar = Calendar.getInstance()
        minDateCalendar.set(year, month, day)
        minDateCalendar.add(Calendar.DAY_OF_MONTH, 1)
        val minDateInMillis = minDateCalendar.timeInMillis

        binding.apply {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    binding.updateReExTvDate.text = getString(
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
            datePickerDialog.datePicker.minDate = minDateInMillis
            datePickerDialog.show()
        }
    }

    private fun updateReExEvent() {
        binding.apply {
            if (updateReExTvDate.text == getString(R.string.add_re_ex_pick_date)) {
                Utils.showDialog(requireContext(), "Vui lòng chọn ngày tái khám")
            } else {
                if (updateReExEdDiseaseName.text?.trim().toString().isEmpty()) {
                    Utils.showDialog(requireContext(), "Vui lòng đặt tên bệnh cần tái khám")
                } else {
                    val reExEvent = ReExaminationEventEntity(
                        id = navArgs.reExEventModel.id,
                        hour = Utils.formatTime(pickerHour),
                        minutes = Utils.formatTime(pickerMinute),
                        dayBegin = updateReExTvDate.text.trim().toString(),
                        diseaseName = updateReExEdDiseaseName.text?.trim().toString(),
                        address = updateReExEdAddress.text?.trim().toString()
                    )
                    viewModel?.updateReExEvent(reExEvent)
                    backToPreScreen()
                }
            }

        }
    }

}