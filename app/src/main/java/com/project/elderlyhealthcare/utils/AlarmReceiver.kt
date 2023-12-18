package com.project.elderlyhealthcare.utils

import android.Manifest
import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.RemoteViews
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.domain.models.ExerciseEventModel
import com.project.elderlyhealthcare.domain.models.MedicineEventModel
import com.project.elderlyhealthcare.domain.models.ReExaminationEventModel
import com.project.elderlyhealthcare.presentation.activity.MainActivity
import com.project.elderlyhealthcare.utils.Constant.KEY_EVENT
import com.project.elderlyhealthcare.utils.Constant.KEY_EVENT_ITEM
import com.project.elderlyhealthcare.utils.Constant.KEY_NOTIFICATION
import com.project.elderlyhealthcare.utils.Constant.MODE_EXERCISE
import com.project.elderlyhealthcare.utils.Constant.MODE_MEDICINE
import com.project.elderlyhealthcare.utils.Constant.MODE_RE_EXAMINATION
import dagger.hilt.android.AndroidEntryPoint
import java.util.Random
import javax.inject.Inject


@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {
    @Inject
    lateinit var notificationBuilder: NotificationCompat.Builder

    @Inject
    lateinit var notificationManager: NotificationManagerCompat


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.getStringExtra(KEY_EVENT)) {
            MODE_EXERCISE -> {
                createNotification(context, fromNotification(context, intent, MODE_EXERCISE), getDataItemEvent(intent), MODE_EXERCISE)
            }

            MODE_MEDICINE -> {
                createNotification(context, fromNotification(context, intent, MODE_MEDICINE), getDataItemEvent(intent), MODE_MEDICINE)
            }

            MODE_RE_EXAMINATION -> {
                createNotification(context, fromNotification(context, intent, MODE_RE_EXAMINATION), getDataItemEvent(intent), MODE_RE_EXAMINATION)
            }
        }

        intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        setVibration(context)
    }

    private fun createNotification(context: Context?, pendingIntent: PendingIntent, dataItemEvent: Any?, mode: String) {
        if (context?.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.POST_NOTIFICATIONS) } != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Vui lòng bật cho phép thông báo đến thiết bị", Toast.LENGTH_SHORT).show()
        } else {
            notificationManager.notify(Random().nextInt(), settingNotification(context, pendingIntent, dataItemEvent, mode))
        }
    }

    private fun settingNotification(context: Context?, pendingIntent: PendingIntent, item: Any?, mode: String): Notification {
        val notificationLayout = RemoteViews(context?.packageName, R.layout.small_notification)
        when (mode) {
            MODE_EXERCISE -> {
                item as ExerciseEventModel
                notificationLayout.setTextViewText(R.id.notification_time, "${item.hour}:${item.minutes}")
                notificationLayout.setTextViewText(R.id.notification_title, "Hôm nay bạn có lịch tập thể dục")
            }

            MODE_MEDICINE -> {
                item as MedicineEventModel
                notificationLayout.setTextViewText(R.id.notification_time, "${item.hour}:${item.minutes}")
                notificationLayout.setTextViewText(R.id.notification_title, "Hôm nay bạn có lịch uống thuốc")
            }

            MODE_RE_EXAMINATION -> {
                item as ReExaminationEventModel
                notificationLayout.setTextViewText(R.id.notification_time, "${item.hour}:${item.minutes}")
                notificationLayout.setTextViewText(R.id.notification_title, "Bạn có lịch tái khám sắp tới")
            }
        }
        notificationBuilder.apply {
            setCustomContentView(notificationLayout)
            setContentIntent(pendingIntent)
        }
        return notificationBuilder.build()
    }

    private fun fromNotification(context: Context?, intent: Intent?, mode: String): PendingIntent {
        val i = Intent(context, MainActivity::class.java)
        when (mode) {
            MODE_EXERCISE -> {
                i.putExtra(KEY_EVENT_ITEM, getDataItemEvent(intent) as ExerciseEventModel)
                i.putExtra(KEY_EVENT, MODE_EXERCISE)
            }

            MODE_MEDICINE -> {
                i.putExtra(KEY_EVENT_ITEM, getDataItemEvent(intent) as MedicineEventModel)
                i.putExtra(KEY_EVENT, MODE_MEDICINE)
            }

            MODE_RE_EXAMINATION -> {
                i.putExtra(KEY_EVENT_ITEM, getDataItemEvent(intent) as ReExaminationEventModel)
                i.putExtra(KEY_EVENT, MODE_RE_EXAMINATION)
            }
        }

        i.putExtra(KEY_NOTIFICATION, true)
        return PendingIntent.getActivity(context, Random().nextInt(), i, PendingIntent.FLAG_IMMUTABLE)
    }


    private fun getDataItemEvent(intent: Intent?): Any? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(KEY_EVENT_ITEM, Any::class.java)
        } else {
            intent?.getParcelableExtra(KEY_EVENT_ITEM)
        }
    }

    private fun setVibration(context: Context?) {
        val vibrator: Vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val vibrationEffect = VibrationEffect.createOneShot(3000, VibrationEffect.DEFAULT_AMPLITUDE)
        vibrator.vibrate(vibrationEffect, createVibrationAudioAttributes())
    }

    private fun createVibrationAudioAttributes(): AudioAttributes {
        return AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_ALARM)
            .build()
    }
}