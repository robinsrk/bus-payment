@file:OptIn(ExperimentalAnimationApi::class)

package com.example.buspayment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.buspayment.funtions.NotificationService
import com.example.buspayment.funtions.ToastService
import com.example.buspayment.navigations.SetupNavGraph
import com.example.buspayment.realtimeDB.repository.DBRepository
import com.example.buspayment.ui.theme.BusPaymentTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		DBRepository.setContext(this)
		ToastService.setContext(this)
		NotificationService(applicationContext).createNotificationChannel()
		setContent {
			BusPaymentTheme {
				val navController = rememberAnimatedNavController()
				Surface(
					modifier = Modifier.fillMaxSize(),
					color = MaterialTheme.colorScheme.background
				) {
					Column(
						modifier = Modifier.fillMaxSize(),
					) {
						SetupNavGraph(navController)
					}
				}
			}
		}
	}
}


