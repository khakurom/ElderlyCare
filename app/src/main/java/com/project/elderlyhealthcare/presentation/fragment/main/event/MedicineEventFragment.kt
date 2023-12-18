package com.project.elderlyhealthcare.presentation.fragment.main.event

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.databinding.FragmentMedicineEventBinding
import com.project.elderlyhealthcare.domain.models.MedicineEventModel
import com.project.elderlyhealthcare.presentation.adapter.MedicineAdapter
import com.project.elderlyhealthcare.presentation.adapter.OnItemRemoveListener
import com.project.elderlyhealthcare.presentation.adapter.OnItemSelectListener
import com.project.elderlyhealthcare.presentation.adapter.OnItemTurnOnListener
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.EventViewModel
import com.project.elderlyhealthcare.utils.AlarmReceiver
import com.project.elderlyhealthcare.utils.Constant
import com.project.elderlyhealthcare.utils.OnFragmentInteractionListener
import com.project.elderlyhealthcare.utils.SingleClickListener
import com.project.elderlyhealthcare.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class MedicineEventFragment :
    BaseFragment<EventViewModel, FragmentMedicineEventBinding>(R.layout.fragment_medicine_event) {

    private var listener: OnFragmentInteractionListener? = null

    private val medicineAdapter = MedicineAdapter()


    override fun variableId(): Int = BR.medicineViewModel

    override fun createViewModel(): Lazy<EventViewModel> = activityViewModels()

    override fun bindView(view: View): FragmentMedicineEventBinding {
        return FragmentMedicineEventBinding.bind(view)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        listener?.updateBottomNavVisible(false)
    }


    override fun init() {
        super.init()
        listener?.updateBottomNavVisible(true)
        medicineAdapter.apply {
            onItemSelectListener = object : OnItemSelectListener<MedicineEventModel> {
                override fun onItemSelected(item: MedicineEventModel, position: Int) {
                    findNavController().navigate(
                        MedicineEventFragmentDirections.actionMedicineEventFragmentToUpdateMedicineEventFragment(
                            item
                        )
                    )
                }
            }
            onItemRemoveListener = object : OnItemRemoveListener<MedicineEventModel> {
                override fun onItemRemove(item: MedicineEventModel, position: Int) {
                    viewModel?.deleteMedicineEvent(item.id)
                }
            }
            onItemTurnOnListener = object : OnItemTurnOnListener<MedicineEventModel> {
                override fun onItemTurnOn(item: MedicineEventModel, position: Int): Boolean {
                    viewModel?.updateMedicineEventOnOff(item.id, true)
                    createAlarm(item)
                    return true
                }

                override fun onItemTurnOff(item: MedicineEventModel, position: Int) {
                    viewModel?.updateMedicineEventOnOff(item.id, false)
                    cancelAlarm(item)
                }
            }
        }


        viewModel?.getMedicineEvent()
        binding.apply {
            medicineFrCsBar.customAppBarIvBack.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    backToPreScreen()
                }
            })
            medicineFabAdd.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    findNavController().navigate(MedicineEventFragmentDirections.actionMedicineEventFragmentToAddMedicineFragment())
                }
            })

            medicineRcvListMedicine.adapter = medicineAdapter

        }
    }

    @SuppressLint("ScheduleExactAlarm")
    private fun createAlarm(item: MedicineEventModel) = runBlocking {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val startDate = dateFormat.parse(item.dayBegin)
        val calendar = Calendar.getInstance()

        calendar.time = startDate!!
        calendar.set(Calendar.HOUR_OF_DAY, item.hour!!.toInt())
        calendar.set(Calendar.MINUTE, item.minutes!!.toInt())
        calendar.set(Calendar.MILLISECOND, 0)
        calendar.set(Calendar.SECOND, 0)

        val alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        intent.putExtra(Constant.KEY_EVENT_ITEM, item)
        intent.putExtra(Constant.KEY_EVENT, Constant.MODE_MEDICINE)

        val uniqueIntent = async(Dispatchers.IO) { viewModel?.getUniqueIntentMedicine(item.id) }
        uniqueIntent.await()?.let {
            val pendingIntent = PendingIntent.getBroadcast(activity?.applicationContext, it, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
            val nextAlarmTime = calendar.timeInMillis + AlarmManager.INTERVAL_DAY
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                nextAlarmTime,
                pendingIntent
            )
        }

    }


    private fun cancelAlarm(item: MedicineEventModel) = runBlocking {
        val uniqueIntent = async(Dispatchers.IO) { viewModel?.getUniqueIntentMedicine(item.id) }
        uniqueIntent.await()?.let {
            val alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(requireContext(), AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                activity?.applicationContext,
                it,
                intent,
                PendingIntent.FLAG_MUTABLE
            )
            alarmManager.cancel(pendingIntent)
        }
    }


}