package com.project.elderlyhealthcare.presentation.fragment.main.event

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.data.models.ExerciseEventEntity
import com.project.elderlyhealthcare.databinding.FragmentExerciseEventBinding
import com.project.elderlyhealthcare.domain.models.ExerciseEventModel
import com.project.elderlyhealthcare.presentation.adapter.ExerciseAdapter
import com.project.elderlyhealthcare.presentation.adapter.OnItemRemoveListener
import com.project.elderlyhealthcare.presentation.adapter.OnItemSelectListener
import com.project.elderlyhealthcare.presentation.adapter.OnItemTurnOnListener
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.EventViewModel
import com.project.elderlyhealthcare.utils.AlarmReceiver
import com.project.elderlyhealthcare.utils.Constant
import com.project.elderlyhealthcare.utils.DelegatedPreferences
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
class ExerciseEventFragment :
    BaseFragment<EventViewModel, FragmentExerciseEventBinding>(R.layout.fragment_exercise_event) {
    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference


    private var listener: OnFragmentInteractionListener? = null
    override fun variableId(): Int = BR.exerciseViewModel

    override fun createViewModel(): Lazy<EventViewModel> = activityViewModels()
    override fun bindView(view: View): FragmentExerciseEventBinding {
        return FragmentExerciseEventBinding.bind(view)
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
        val exerciseAdapter: ExerciseAdapter by lazy {
            ExerciseAdapter().apply {
                onItemSelectListener = object : OnItemSelectListener<ExerciseEventModel> {
                    override fun onItemSelected(item: ExerciseEventModel, position: Int) {
                        findNavController().navigate(ExerciseEventFragmentDirections.actionExerciseEventFragmentToUpdateExerciseEventFragment(item))
                    }
                }
                onItemRemoveListener = object : OnItemRemoveListener<ExerciseEventModel> {
                    override fun onItemRemove(item: ExerciseEventModel, position: Int) {
                        viewModel?.deleteExerciseEvent(item.id)
                        removeExerciseEvent(item.id)
                    }
                }

                onItemTurnOnListener = object : OnItemTurnOnListener<ExerciseEventModel> {
                    override fun onItemTurnOn(item: ExerciseEventModel, position: Int): Boolean {
                        return if (Utils.compareToCurrentTime(
                                item.dayBegin!!,
                                Utils.formatTimeString(item.hour!!),
                                Utils.formatTimeString(item.minute!!)
                            )
                        ) {
                            viewModel?.updateExerciseEventOnOff(item.id, false)
                            false
                        } else {
                            viewModel?.updateExerciseEventOnOff(item.id, true)
                            createAlarm(item)
                            true
                        }

                    }

                    override fun onItemTurnOff(item: ExerciseEventModel, position: Int) {
                        viewModel?.updateExerciseEventOnOff(item.id, false)
                        cancelAlarm(item)
                    }
                }
            }
        }


        binding.apply {
            exerciseFrCsBar.customAppBarIvBack.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    backToPreScreen()
                }
            })

            exerciseFabAdd.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    try {
                        findNavController().navigate(ExerciseEventFragmentDirections.actionExerciseEventFragmentToAddExerciseFragment())
                    } catch (_: Exception) {
                    }
                }
            })
            exerciseRcvListExercise.adapter = exerciseAdapter


        }
        listener?.updateBottomNavVisible(true)
        getExerciseEvent()
    }

    @SuppressLint("ScheduleExactAlarm")
    private fun createAlarm(item: ExerciseEventModel) = runBlocking {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = dateFormat.parse(item.dayBegin!!)
        val calendar = Calendar.getInstance()

        calendar.time = date!!
        calendar.set(Calendar.HOUR_OF_DAY, item.hour!!.toInt())
        calendar.set(Calendar.MINUTE, item.minute!!.toInt())
        calendar.set(Calendar.MILLISECOND, 0)
        calendar.set(Calendar.SECOND, 0)

        val alarmManager = activity?.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        intent.putExtra(Constant.KEY_EVENT_ITEM, item)
        intent.putExtra(Constant.KEY_EVENT, Constant.MODE_EXERCISE)
        val uniqueIntent = async(Dispatchers.IO) { viewModel?.getUniqueIntentExercise(item.id) }
        uniqueIntent.await()?.let {
            val pendingIntent = PendingIntent.getBroadcast(activity?.applicationContext, it, intent, PendingIntent.FLAG_MUTABLE)
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }

    }

    private fun cancelAlarm(item: ExerciseEventModel) = runBlocking {
        val uniqueIntent = async(Dispatchers.IO) { viewModel?.getUniqueIntentExercise(item.id) }
        uniqueIntent.await()?.let {
            val alarmManager = activity?.getSystemService(ALARM_SERVICE) as AlarmManager
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

    private fun getExerciseEvent() {
        viewModel?.getExerciseEvent()
        viewModel?.listExerciseEvent?.observe(this) { exerciseList ->
            exerciseList?.let {
                for (i in it) {
                    uploadExerciseEvent(i)
                }
            }
        }
    }

    private fun uploadExerciseEvent(exerciseEvent: ExerciseEventModel) {
        val dataKey = DelegatedPreferences(requireContext(), Constant.PHONE_NUMBER, "").getValue()
        databaseReference.child("data").child(dataKey).child(getString(R.string.key_event)).child("Exercise").child(exerciseEvent.id.toString())
            .setValue(exerciseEvent)
            .addOnSuccessListener {
            }
            .addOnFailureListener {
            }
    }

    private fun removeExerciseEvent(idItem: Int) {
        val dataKey = DelegatedPreferences(requireContext(), Constant.PHONE_NUMBER, "").getValue()
        databaseReference.child("data").child(dataKey).child(getString(R.string.key_event)).child("Exercise").child(idItem.toString()).removeValue()
    }
}