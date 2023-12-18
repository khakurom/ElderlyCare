package com.project.elderlyhealthcare.presentation.fragment.main.event

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.data.models.MedicineEventEntity
import com.project.elderlyhealthcare.databinding.FragmentUpdateMedicineEventBinding
import com.project.elderlyhealthcare.domain.models.MedicineTypeModel
import com.project.elderlyhealthcare.presentation.adapter.MedicineTypeAdapter
import com.project.elderlyhealthcare.presentation.adapter.OnItemRemoveListener
import com.project.elderlyhealthcare.presentation.adapter.OnItemSelectListener
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.EventViewModel
import com.project.elderlyhealthcare.utils.AlarmReceiver
import com.project.elderlyhealthcare.utils.Constant
import com.project.elderlyhealthcare.utils.CustomBottomSheet
import com.project.elderlyhealthcare.utils.OnFragmentInteractionListener
import com.project.elderlyhealthcare.utils.SingleClickListener
import com.project.elderlyhealthcare.utils.Utils
import com.project.elderlyhealthcare.utils.Utils.getDayMonthYearFromCurrentDate
import kotlinx.coroutines.runBlocking
import java.util.Calendar
import java.util.Random

class UpdateMedicineEventFragment :
    BaseFragment<EventViewModel, FragmentUpdateMedicineEventBinding>(R.layout.fragment_update_medicine_event) {

    private var listener: OnFragmentInteractionListener? = null


    private val medicineTypeList = mutableListOf<MedicineTypeModel>()


    private val navArgs: UpdateMedicineEventFragmentArgs by navArgs()

    override fun variableId(): Int = BR.updateMedicineViewModel

    override fun createViewModel(): Lazy<EventViewModel> = activityViewModels()

    override fun bindView(view: View): FragmentUpdateMedicineEventBinding {
        return FragmentUpdateMedicineEventBinding.bind(view)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        }
    }


    override fun init() {
        super.init()
        listener?.updateBottomNavVisible(true)
        val medicineTypeAdapter: MedicineTypeAdapter by lazy {
            MedicineTypeAdapter(false).apply {
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
            updateMedicineFrCsBar.customAppBarIvBack.setOnClickListener(object :
                SingleClickListener() {
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

            updateMedicineBtAddMedicineType.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    createBottomSheet(medicineTypeAdapter)
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
                    updateMedicineEvent()
                }
            })

            updateMedicineTvEndDate.text = navArgs.medicineEventModel.dayEnd

            updateMedicineEdDiseaseName.setText(navArgs.medicineEventModel.diseaseName)


        }
        settingTimePicker()
        createMedicineTypeList(medicineTypeAdapter)
    }

    private fun removeItemMedicineType(adapter: MedicineTypeAdapter, position: Int) {
        medicineTypeList.removeAt(position)
        adapter.submitList(medicineTypeList)
        adapter.notifyDataSetChanged()
    }


    private fun settingTimePicker() {
        binding.apply {
            pickerHour.maxValue = 23
            pickerMinute.maxValue = 59

            pickerHour.value = navArgs.medicineEventModel.hour!!.toInt()
            pickerMinute.value = navArgs.medicineEventModel.minute!!.toInt()

            pickerHour.displayedValues = Constant.listHour
            pickerMinute.displayedValues = Constant.listMinutes
        }
    }

    private fun updateMedicineEvent() {
        binding.apply {
            if (updateMedicineTvEndDate.text == getString(R.string.add_medicine_pick_date)) {
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
                            Utils.formatTimeNumberPicker(pickerHour),
                            Utils.formatTimeNumberPicker(pickerMinute)
                        )
                    ) {
                        Utils.showDialog(requireContext(), "Không thể đặt giờ trong quá khứ")
                    } else {
                        cancelAlarm()
                        val medicineEvent = MedicineEventEntity(
                            id = navArgs.medicineEventModel.id,
                            hour = Utils.formatTimeNumberPicker(pickerHour),
                            minute = Utils.formatTimeNumberPicker(pickerMinute),
                            dayBegin = updateMedicineTvBeginDate.text.trim().toString(),
                            dayEnd = updateMedicineTvEndDate.text.trim().toString(),
                            medicineName = getMedicineName(),
                            medicineDose = getMedicineDose(),
                            diseaseName = updateMedicineEdDiseaseName.text?.trim().toString(),
                            uniqueIntent = Random().nextInt(),
                            isOn = true
                        )
                        viewModel?.updateMedicineEvent(medicineEvent)
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
                binding.updateMedicineTvEndDate.text = getString(R.string.add_medicine_pick_date)
            },
            year,
            month,
            day
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun createMedicineTypeList(medicineTypeAdapter: MedicineTypeAdapter) {
        val medicineName = navArgs.medicineEventModel.medicineName
        val medicineDose = navArgs.medicineEventModel.medicineDose

        val zipMedicineNameDose = medicineName.zip(medicineDose)
        for ((item1, item2) in zipMedicineNameDose) {
            medicineTypeList.add(MedicineTypeModel(item1, item2))
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

    private fun cancelAlarm() = runBlocking {
        val alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            activity?.applicationContext,
            navArgs.medicineEventModel.uniqueIntent,
            intent,
            PendingIntent.FLAG_MUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }


}