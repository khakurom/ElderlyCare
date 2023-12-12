package com.project.elderlyhealthcare.presentation.di

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
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

	@Singleton
	@Provides
	fun provideNotificationBuilder(@ApplicationContext context: Context): NotificationCompat.Builder {
		return NotificationCompat.Builder(context, "Main Channel")
			.setContentTitle("Welcome")
			.setSmallIcon(R.drawable.ic_notification)
			.setContentText("This is notification")
			.setAutoCancel(true)
			.setDefaults(NotificationCompat.DEFAULT_ALL)
			.setPriority(NotificationCompat.PRIORITY_DEFAULT)
	}

	@Singleton
	@Provides
	fun provideNotificationManager(@ApplicationContext context: Context): NotificationManagerCompat {
		val notificationManager = NotificationManagerCompat.from(context)
		val channel = NotificationChannel(
			"Main Channel",
			"Channel 1",
			NotificationManager.IMPORTANCE_DEFAULT
		)
		notificationManager.createNotificationChannel(channel)

		return notificationManager
	}
}