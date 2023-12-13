package com.project.elderlyhealthcare.utils

import android.Manifest
import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.project.elderlyhealthcare.domain.models.ExerciseEventModel
import com.project.elderlyhealthcare.presentation.activity.MainActivity
import com.project.elderlyhealthcare.presentation.activity.NotLoginActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {
    @Inject
    lateinit var notificationBuilder: NotificationCompat.Builder

    @Inject
    lateinit var notificationManager: NotificationManagerCompat


    override fun onReceive(context: Context?, intent: Intent?) {
        val i = Intent(context, MainActivity::class.java)
        intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_IMMUTABLE)

        val dataItemEvent = getDataItemEvent(intent)

        if (context?.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.POST_NOTIFICATIONS) } != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Vui lòng bật cho phép thông báo đến thiết bị", Toast.LENGTH_SHORT).show()
        } else {
            notificationManager.notify(1, setNotification(pendingIntent, dataItemEvent))
        }
    }

    private fun setNotification(pendingIntent: PendingIntent, item : ExerciseEventModel?): Notification {
        notificationBuilder.apply {
//            setContentTitle(item?.exerciseName)
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
}