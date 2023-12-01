package com.project.elderlyhealthcare.presentation.fragment.main.event

import android.app.DatePickerDialog
import android.content.res.ColorStateList
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.data.models.MedicineEventEntity
import com.project.elderlyhealthcare.databinding.FragmentUpdateMedicineEventBinding
import com.project.elderlyhealthcare.domain.models.MedicineEventModel
import com.project.elderlyhealthcare.domain.models.MedicineTypeModel
import com.project.elderlyhealthcare.presentation.adapter.MedicineTypeAdapter
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.EventViewModel
import com.project.elderlyhealthcare.utils.Constant
import com.project.elderlyhealthcare.utils.SingleClickListener
import com.project.elderlyhealthcare.utils.Utils
import com.project.elderlyhealthcare.utils.Utils.getDayMonthYearFromCurrentDate
import java.util.Calendar

class UpdateMedicineEventFragment :
    BaseFragment<EventViewModel, FragmentUpdateMedicineEventBinding>(R.layout.fragment_update_medicine_event) {
    private lateinit var dayRepeatList: MutableList<String?>


    private val medicineTypeAdapter = MedicineTypeAdapter()

    private val navArgs: UpdateMedicineEventFragmentArgs by navArgs()

    override fun variableId(): Int = BR.updateMedicineViewModel

    override fun createViewModel(): Lazy<EventViewModel> = activityViewModels()

    override fun bindView(view: View): FragmentUpdateMedicineEventBinding {
        return FragmentUpdateMedicineEventBinding.bind(view)
    }

    override fun init() {
        super.init()
        medicineTypeAdapter.apply {

        }
        binding.apply {
            updateMedicineFrCsBar.customAppBarIvBack.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    backToPreScreen()
                }
            })
            pickerHour.textColor = ContextCompat.getColor(requireContext(), R.color.black)
            pickerMinute.textColor = ContextCompat.getColor(requireContext(), R.color.black)

            updateMedicineLayoutBeginDatePicker.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    selectBeginDate()
                }
            })

            updateMedicineTvBeginDate.text = navArgs.medicineEventModel.dayBegin

            updateMedicineLayoutEndDatePicker.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    selectEndDate()
                }
            })

            updateMedicineBtUpdateEvent.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                   createMedicineEvent()
                }
            })

            updateMedicineTvEndDate.text = navArgs.medicineEventModel.dayEnd

            updateMedicineEdDiseaseName.setText(navArgs.medicineEventModel.diseaseName)
        }
        settingTimePicker()
        settingDayRepeat()
        settingDayPicker()
        createMedicineTypeList ()
        getValueDayRepeat()
    }

    private fun settingDayRepeat() {
        binding.apply {
            val dayRepeatList = navArgs.medicineEventModel.dayRepeat
            if (dayRepeatList.contains("T2")) {
                toggleBtMon.isChecked = true
            }
            if (dayRepeatList.contains("T3")) {
                toggleBtTu.isChecked = true
            }
            if (dayRepeatList.contains("T4")) {
                toggleBtWe.isChecked = true
            }
            if (dayRepeatList.contains("T5")) {
                toggleBtTh.isChecked = true
            }
            if (dayRepeatList.contains("T6")) {
                toggleBtFr.isChecked = true
            }
            if (dayRepeatList.contains("T7")) {
                toggleBtSa.isChecked = true
            }
            if (dayRepeatList.contains("CN")) {
                toggleBtSun.isChecked = true
            }
        }
    }

    private fun getValueDayRepeat() {
        dayRepeatList = navArgs.medicineEventModel.dayRepeat as MutableList<String?>
        binding.apply {
            toggleBtMon.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    dayRepeatList.add("T2")
                } else {
                    dayRepeatList.remove("T2")
                }
            }

            toggleBtTu.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    dayRepeatList.add("T3")
                } else {
                    dayRepeatList.remove("T3")
                }
            }
            toggleBtWe.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    dayRepeatList.add("T4")
                } else {
                    dayRepeatList.remove("T4")
                }
            }
            toggleBtTh.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    dayRepeatList.add("T5")
                } else {
                    dayRepeatList.remove("T5")
                }
            }

            toggleBtFr.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    dayRepeatList.add("T6")
                } else {
                    dayRepeatList.remove("T6")
                }
            }
            toggleBtSa.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    dayRepeatList.add("T7")
                } else {
                    dayRepeatList.remove("T7")
                }
            }
            toggleBtSun.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    dayRepeatList.add("CN")
                } else {
                    dayRepeatList.remove("CN")
                }
            }
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


    private fun settingTimePicker() {
        binding.apply {
            pickerHour.maxValue = 23
            pickerMinute.maxValue = 59

            pickerHour.value = navArgs.medicineEventModel.hour!!.toInt()
            pickerMinute.value = navArgs.medicineEventModel.minutes!!.toInt()

            pickerHour.displayedValues = Constant.listHour
            pickerMinute.displayedValues = Constant.listMinutes
        }
    }

    private fun createMedicineEvent() {
        binding.apply {
            if (updateMedicineTvEndDate.text == getString(R.string.addMedicine_pick_date)) {
                Utils.showDialog(requireContext(), "Vui lòng chọn ngày kết thúc")
            } else {
                if (updateMedicineEdDiseaseName.text?.trim().toString().isEmpty()) {
                    Utils.showDialog(
                        requireContext(),
                        "Vui lòng điền thông tin thuốc điều trị bệnh gì"
                    )
                } else {
                    if (Utils.compareToCurrentTime(
                            updateMedicineTvBeginDate.text.trim().toString(),
                            Utils.formatTime(pickerHour),
                            Utils.formatTime(pickerMinute)
                        )
                    ) {
                        Utils.showDialog(requireContext(), "Không thể đặt giờ trong quá khứ")
                    } else {
                        val medicineEvent = MedicineEventEntity(
                            id = navArgs.medicineEventModel.id,
                            hour = Utils.formatTime(pickerHour),
                            minutes = Utils.formatTime(pickerMinute),
                            dayRepeat = dayRepeatList,
                            dayBegin = updateMedicineTvBeginDate.text.trim().toString(),
                            dayEnd = updateMedicineTvEndDate.text.trim().toString(),
                            medicineName = navArgs.medicineEventModel.medicineName,
                            medicineDose = navArgs.medicineEventModel.medicineDose,
                            diseaseName = updateMedicineEdDiseaseName.text?.trim().toString()
                        )
                        viewModel?.updateMedicineEvent(medicineEvent)
                        backToPreScreen()
                    }
                }
            }
        }
    }



    private fun selectBeginDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                binding.updateMedicineTvBeginDate.text = getString(
                    R.string.day_month_year,
                    selectedDay.toString(),
                    (selectedMonth + 1).toString(),
                    selectedYear.toString()
                )
                binding.updateMedicineTvEndDate.text = getString(R.string.addMedicine_pick_date)
            },
            year,
            month,
            day
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun createMedicineTypeList () {
        val medicineTypeList = mutableListOf<MedicineTypeModel>()
        val medicineName = navArgs.medicineEventModel.medicineName
        val medicineDose = navArgs.medicineEventModel.medicineDose

        val zipMedicineNameDose = medicineName.zip(medicineDose)
        for ((item1,item2) in zipMedicineNameDose) {
            medicineTypeList.add(MedicineTypeModel(item1,item2))
        }

        medicineTypeAdapter.submitList(medicineTypeList)
        binding.updateMedicineRcvMedicineType.adapter = medicineTypeAdapter

    }

    private fun selectEndDate() {
        val result =
            getDayMonthYearFromCurrentDate(binding.updateMedicineTvBeginDate.text.toString().trim())
        if (result != null) {
            val (day, month, year) = result
            val minDate = Calendar.getInstance().apply {
                set(year.toInt(), month.toInt() - 1, day.toInt())
            }
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    binding.updateMedicineTvEndDate.text = getString(
                        R.string.day_month_year,
                        selectedDay.toString(),
                        (selectedMonth + 1).toString(),
                        selectedYear.toString()
                    )
                },
                minDate.get(Calendar.YEAR),
                minDate.get(Calendar.MONTH),
                minDate.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.datePicker.minDate = minDate.timeInMillis
            datePickerDialog.show()
        } else {
            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
        }
    }


}