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
import com.project.elderlyhealthcare.databinding.FragmentReExaminationEventBinding
import com.project.elderlyhealthcare.domain.models.ReExaminationEventModel
import com.project.elderlyhealthcare.presentation.adapter.OnItemRemoveListener
import com.project.elderlyhealthcare.presentation.adapter.OnItemSelectListener
import com.project.elderlyhealthcare.presentation.adapter.OnItemTurnOnListener
import com.project.elderlyhealthcare.presentation.adapter.ReExaminationAdapter
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
class ReExaminationEventFragment :
    BaseFragment<EventViewModel, FragmentReExaminationEventBinding>(R.layout.fragment_re_examination_event) {

    private var listener: OnFragmentInteractionListener? = null

    override fun variableId(): Int = BR.reExaminationViewModel

    override fun createViewModel(): Lazy<EventViewModel> = activityViewModels()

    override fun bindView(view: View): FragmentReExaminationEventBinding {
        return FragmentReExaminationEventBinding.bind(view)
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
        val reExAdapter: ReExaminationAdapter by lazy {
            ReExaminationAdapter().apply {
                onItemSelectListener = object : OnItemSelectListener<ReExaminationEventModel> {
                    override fun onItemSelected(item: ReExaminationEventModel, position: Int) {
                        findNavController().navigate(
                            ReExaminationEventFragmentDirections.actionReExaminationEventFragmentToUpdateReExaminationFragment(
                                item
                            )
                        )
                    }
                }
                onItemRemoveListener = object : OnItemRemoveListener<ReExaminationEventModel> {
                    override fun onItemRemove(item: ReExaminationEventModel, position: Int) {
                        viewModel?.deleteReExEvent(item.id)
                    }
                }

                onItemTurnOnListener = object : OnItemTurnOnListener<ReExaminationEventModel> {
                    override fun onItemTurnOn(item: ReExaminationEventModel, position: Int): Boolean {
                        return if (Utils.compareToCurrentTime(
                                item.dayBegin,
                                Utils.formatTimeString(item.hour),
                                Utils.formatTimeString(item.minutes)
                            )
                        ) {
                            viewModel?.updateReExEventOnOff(item.id, false)
                            false
                        } else {
                            viewModel?.updateReExEventOnOff(item.id, true)
                            createAlarm(item)
                            true
                        }

                    }

                    override fun onItemTurnOff(item: ReExaminationEventModel, position: Int) {
                        viewModel?.updateReExEventOnOff(item.id, false)
                        cancelAlarm(item)
                    }
                }
            }
        }

        binding.apply {
            reExFrCsBar.customAppBarIvBack.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    backToPreScreen()
                }
            })

            reExFabAdd.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    findNavController().navigate(ReExaminationEventFragmentDirections.actionReExaminationEventFragmentToAddReExaminationFragment())
                }
            })

            reExRcvListExercise.adapter = reExAdapter
        }
        getReExEvent()
    }

    @SuppressLint("ScheduleExactAlarm")
    private fun createAlarm(item: ReExaminationEventModel) = runBlocking {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = dateFormat.parse(item.dayBegin)
        val calendar = Calendar.getInstance()

        calendar.time = date!!
        calendar.set(Calendar.HOUR_OF_DAY, item.hour.toInt())
        calendar.set(Calendar.MINUTE, item.minutes.toInt())
        calendar.set(Calendar.MILLISECOND, 0)
        calendar.set(Calendar.SECOND, 0)

        val alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        intent.putExtra(Constant.KEY_EVENT_ITEM, item)
        intent.putExtra(Constant.KEY_EVENT, Constant.MODE_RE_EXAMINATION)

        val uniqueIntent = async(Dispatchers.IO) { viewModel?.getUniqueIntentReEx(item.id) }
        uniqueIntent.await()?.let {
            val pendingIntent = PendingIntent.getBroadcast(activity?.applicationContext, it, intent, PendingIntent.FLAG_MUTABLE)
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }
    }
    private fun cancelAlarm(item: ReExaminationEventModel) = runBlocking {
        val uniqueIntent = async(Dispatchers.IO) { viewModel?.getUniqueIntentReEx(item.id) }
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

    private fun getReExEvent() {
        viewModel?.getReExEvent()
    }

}