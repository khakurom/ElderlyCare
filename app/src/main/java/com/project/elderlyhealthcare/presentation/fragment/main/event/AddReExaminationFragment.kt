package com.project.elderlyhealthcare.presentation.fragment.main.event

import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.data.models.ReExaminationEventEntity
import com.project.elderlyhealthcare.databinding.FragmentAddReExaminationBinding
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.EventViewModel
import com.project.elderlyhealthcare.utils.Constant
import com.project.elderlyhealthcare.utils.OnFragmentInteractionListener
import com.project.elderlyhealthcare.utils.SingleClickListener
import com.project.elderlyhealthcare.utils.Utils
import com.project.elderlyhealthcare.utils.Utils.hideKeyboard
import com.project.elderlyhealthcare.utils.Utils.showDialog
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Random

@AndroidEntryPoint
class AddReExaminationFragment :
    BaseFragment<EventViewModel, FragmentAddReExaminationBinding>(R.layout.fragment_add_re_examination) {
    private var listener: OnFragmentInteractionListener? = null
    override fun variableId(): Int = BR.addReExViewModel

    override fun createViewModel(): Lazy<EventViewModel> = activityViewModels()

    override fun bindView(view: View): FragmentAddReExaminationBinding {
        return FragmentAddReExaminationBinding.bind(view)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        }
    }


    override fun init() {
        listener?.updateBottomNavVisible(true)
        binding.apply {
            layoutAddReEx.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    v.hideKeyboard()
                }
            })
            addReExFrCsBar.customAppBarIvBack.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    backToPreScreen()
                }
            })

            addReExTvDate.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    selectDate()
                }
            })
            addReExBtAddReminder.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    createReExEvent()
                }
            })
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
        minDateCalendar.set(year, month, day + 1)
        val minDateInMillis = minDateCalendar.timeInMillis

        binding.apply {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    binding.addReExTvDate.text = getString(
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

    private fun createReExEvent() {
        binding.apply {
            if (addReExTvDate.text == getString(R.string.add_re_ex_pick_date)) {
                showDialog(requireContext(), "Vui lòng chọn ngày tái khám")
            } else {
                if (addReExEdDiseaseName.text?.trim().toString().isEmpty()) {
                    showDialog(requireContext(), "Vui lòng đặt tên bệnh cần tái khám")
                } else {
                    if (Utils.compareToCurrentTime(
                            addReExTvDate.text.trim().toString(),
                            Utils.formatTimeNumberPicker(pickerHour),
                            Utils.formatTimeNumberPicker(pickerMinute)
                        )
                    ) {
                        showDialog(requireContext(), "Không thể đặt giờ trong quá khứ")
                    } else {
                        val reExEvent = ReExaminationEventEntity(
                            hour = Utils.formatTimeNumberPicker(pickerHour),
                            minutes = Utils.formatTimeNumberPicker(pickerMinute),
                            dayBegin = addReExTvDate.text.trim().toString(),
                            diseaseName = addReExEdDiseaseName.text?.trim().toString(),
                            address = addReExEdAddress.text?.trim().toString(),
                            isOn = true,
                            uniqueIntent = Random().nextInt()
                        )
                        viewModel?.insertReExEvent(reExEvent)
                        backToPreScreen()
                    }
                }
            }

        }
    }

}