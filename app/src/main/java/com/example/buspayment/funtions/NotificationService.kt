package com.example.buspayment.funtions

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.buspayment.R

class NotificationService(
	private val context: Context,
) {
	fun createNotificationChannel() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			val channel =
				NotificationChannel(
					"BusPayment",
					"Notification for bys payment",
					NotificationManager.IMPORTANCE_DEFAULT
				).apply {
					description = "Testing new notification"
				}
			val notificationManager: NotificationManager =
				context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
			notificationManager.createNotificationChannel(channel)
		}
	}
	
	fun showNotification(
		title: String,
		text: String,
	) {
		val notificationManager =
			context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
		val notification =
			NotificationCompat.Builder(context, "BusPayment").setContentTitle(title)
				.setSmallIcon(R.drawable.undraw_bus_stop_re_h8ej)
				.setContentText(text).build()
		notificationManager.notify(1, notification)
	}
	
}