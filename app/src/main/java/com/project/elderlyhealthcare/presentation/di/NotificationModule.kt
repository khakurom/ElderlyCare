package com.project.elderlyhealthcare.presentation.di

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.Settings
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.project.elderlyhealthcare.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

	@SuppressLint("RemoteViewLayout")
	@Singleton
	@Provides
	fun provideNotificationBuilder(@ApplicationContext context: Context): NotificationCompat.Builder {
		return NotificationCompat.Builder(context, "Main Channel")
			.setSmallIcon(R.drawable.background_login)
			.setStyle(NotificationCompat.DecoratedCustomViewStyle())
			.setAutoCancel(true)
			.setOngoing(true)
			.setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
			.setPriority(NotificationCompat.PRIORITY_HIGH)
			.setDefaults(NotificationCompat.DEFAULT_ALL)

	}

	@Singleton
	@Provides
	fun provideNotificationManager(@ApplicationContext context: Context): NotificationManagerCompat {
		val notificationManager = NotificationManagerCompat.from(context)
		val channel = NotificationChannel(
			"Main Channel",
			"Channel 1",
			NotificationManager.IMPORTANCE_HIGH
		)
		with(channel) {
			enableVibration(true)
			lockscreenVisibility = Notification.VISIBILITY_PUBLIC
			vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
		}
		notificationManager.createNotificationChannel(channel)

		return notificationManager
	}
}