package com.example.buspayment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.buspayment.funtions.NotificationService
import com.example.buspayment.navigations.SetupNavGraph
import com.example.buspayment.realtimeDB.repository.DBRepository
import com.example.buspayment.ui.theme.BusPaymentTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	
	private lateinit var navController: NavHostController
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		DBRepository.setContext(this)
		NotificationService(applicationContext).createNotificationChannel()
		setContent {
			BusPaymentTheme {
				Surface(
					modifier = Modifier.fillMaxSize(),
					color = MaterialTheme.colorScheme.background
				) {
					Column(
						modifier = Modifier.fillMaxSize(),
					) {
						navController = rememberNavController()
						SetupNavGraph(navController)
					}
				}
			}
		}
	}
}


