package com.project.elderlyhealthcare.presentation.fragment.main.event

import android.app.DatePickerDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.data.models.MedicineEventEntity
import com.project.elderlyhealthcare.databinding.FragmentAddMedicineBinding
import com.project.elderlyhealthcare.domain.models.MedicineTypeModel
import com.project.elderlyhealthcare.presentation.adapter.MedicineTypeAdapter
import com.project.elderlyhealthcare.presentation.adapter.OnItemRemoveListener
import com.project.elderlyhealthcare.presentation.adapter.OnItemSelectListener
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.EventViewModel
import com.project.elderlyhealthcare.utils.Constant
import com.project.elderlyhealthcare.utils.CustomBottomSheet
import com.project.elderlyhealthcare.utils.SingleClickListener
import com.project.elderlyhealthcare.utils.Utils.compareToCurrentTime
import com.project.elderlyhealthcare.utils.Utils.formatTime
import com.project.elderlyhealthcare.utils.Utils.getDayMonthYearFromCurrentDate
import com.project.elderlyhealthcare.utils.Utils.showDialog
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class AddMedicineFragment :
    BaseFragment<EventViewModel, FragmentAddMedicineBinding>(R.layout.fragment_add_medicine) {

    private val medicineTypeList = mutableListOf<MedicineTypeModel>()
    private lateinit var dayRepeatList: MutableList<String?>
    override fun variableId(): Int = BR.addMedicineViewModel

    override fun createViewModel(): Lazy<EventViewModel> = activityViewModels()

    override fun bindView(view: View): FragmentAddMedicineBinding {
        return FragmentAddMedicineBinding.bind(view)
    }

    override fun init() {
        super.init()

        val adapter : MedicineTypeAdapter by lazy {
            MedicineTypeAdapter().apply {
                onItemSelectListener = object : OnItemSelectListener<MedicineTypeModel> {
                    override fun onItemSelected(item: MedicineTypeModel, position: Int) {
                    }
                }
                onItemRemoveListener = object : OnItemRemoveListener<MedicineTypeModel> {
                    override fun onItemRemove(item: MedicineTypeModel, position: Int) {
                        removeItemMedicineType(this@apply, position)
                    }

                }
            }
        }


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
            addMedicineBtAddMedicineType.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    createBottomSheet(adapter)
                }
            })
            addMedicineBtAddEvent.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    createMedicineEvent()
                }
            })
            addMedicineRcvMedicineType.adapter = adapter
            pickerHour.textColor = ContextCompat.getColor(requireContext(), R.color.black)
            pickerMinute.textColor = ContextCompat.getColor(requireContext(), R.color.black)
        }
        settingTimePicker()
        settingDayPicker()
        getCurrentDate()
        getValueDayRepeat()
    }

    private fun createMedicineEvent() {
        binding.apply {
            if (addMedicineTvEndDate.text == getString(R.string.add_medicine_pick_date)) {
                showDialog(requireContext(), "Vui lòng chọn ngày kết thúc")
            } else {
                if (addMedicineEdDiseaseName.text?.trim().toString().isEmpty()) {
                    showDialog(requireContext(), "Vui lòng điền thông tin thuốc điều trị bệnh gì")
                } else {
                    if (compareToCurrentTime(
                            addMedicineTvBeginDate.text.trim().toString(),
                            formatTime(pickerHour),
                            formatTime(pickerMinute)
                        )
                    ) {
                        showDialog(requireContext(), "Không thể đặt giờ trong quá khứ")
                    } else {
                        val medicineEvent = MedicineEventEntity(
                            hour = formatTime(pickerHour),
                            minutes = formatTime(pickerMinute),
                            dayRepeat = dayRepeatList,
                            dayBegin = addMedicineTvBeginDate.text.trim().toString(),
                            dayEnd = addMedicineTvEndDate.text.trim().toString(),
                            medicineName = getMedicineName(),
                            medicineDose = getMedicineDose(),
                            diseaseName = addMedicineEdDiseaseName.text?.trim().toString()
                        )
                        viewModel?.insertMedicineEvent(medicineEvent)
                        backToPreScreen()
                    }
                }
            }
        }
    }

    private fun getMedicineName(): List<String> {
        val medicineNameList = mutableListOf<String>()
        for (i in medicineTypeList) {
            medicineNameList.add(i.medicineName)
        }
        return medicineNameList
    }

    private fun getMedicineDose(): List<Int> {
        val medicineDoseList = mutableListOf<Int>()
        for (i in medicineTypeList) {
            medicineDoseList.add(i.medicineDose)
        }
        return medicineDoseList
    }

    private fun getValueDayRepeat() {
        dayRepeatList = mutableListOf()
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

    private fun removeItemMedicineType(adapter: MedicineTypeAdapter, position: Int) {
        medicineTypeList.removeAt(position)
        adapter.submitList(medicineTypeList)
        adapter.notifyDataSetChanged()
    }

    private fun createBottomSheet(adapter: MedicineTypeAdapter) {
        val customBottomSheet = CustomBottomSheet(
            requireContext(),
            object : CustomBottomSheet.OnBottomSheetClickListener {
                override fun onPositiveButtonClick(medicineName: String, medicineDose: String) {
                    medicineTypeList.add(MedicineTypeModel(medicineName, medicineDose.toInt()))
                    adapter.submitList(medicineTypeList)
                    adapter.notifyDataSetChanged()
                }
            })
        customBottomSheet.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        customBottomSheet.show()
    }

    private fun settingTimePicker() {
        binding.apply {
            pickerHour.maxValue = 23
            pickerMinute.maxValue = 59

            pickerHour.displayedValues = Constant.listHour
            pickerMinute.displayedValues = Constant.listMinutes
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
                binding.addMedicineTvEndDate.text = getString(R.string.add_medicine_pick_date)
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
            val minDate = Calendar.getInstance().apply {
                set(year.toInt(), month.toInt() - 1, day.toInt())
            }
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    binding.addMedicineTvEndDate.text = getString(
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