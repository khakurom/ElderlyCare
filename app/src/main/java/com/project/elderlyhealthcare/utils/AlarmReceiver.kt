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
import com.project.elderlyhealthcare.presentation.activity.MainActivity
import com.project.elderlyhealthcare.utils.Constant.KEY_EXERCISE_EVENT
import com.project.elderlyhealthcare.utils.Constant.KEY_NOTIFICATION
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {
    @Inject
    lateinit var notificationBuilder: NotificationCompat.Builder

    @Inject
    lateinit var notificationManager: NotificationManagerCompat


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onReceive(context: Context?, intent: Intent?) {
        val dataItemEvent = getDataItemEvent(intent)
        val i = Intent(context, MainActivity::class.java)
        i.putExtra(KEY_EXERCISE_EVENT, dataItemEvent)
        i.putExtra(KEY_NOTIFICATION, true)

        intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        setVibration(context)
        val pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_IMMUTABLE)

        if (context?.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.POST_NOTIFICATIONS) } != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Vui lòng bật cho phép thông báo đến thiết bị", Toast.LENGTH_SHORT).show()
        } else {
            notificationManager.notify(1, setNotification(context,pendingIntent, dataItemEvent))
        }
    }

    private fun setNotification(context: Context?, pendingIntent: PendingIntent, item: ExerciseEventModel?): Notification {
        val notificationLayout = RemoteViews(context?.packageName, R.layout.small_notification)
        notificationLayout.setTextViewText(R.id.notification_time, "${item?.hour}:${item?.minutes}")

        notificationBuilder.apply {
            setCustomContentView(notificationLayout)
            setContentIntent(pendingIntent)
        }
        return notificationBuilder.build()
    }


    private fun getDataItemEvent(intent: Intent?): ExerciseEventModel? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(Constant.KEY_EXERCISE_EVENT, ExerciseEventModel::class.java)
        } else {
            intent?.getParcelableExtra(Constant.KEY_EXERCISE_EVENT)
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