@file:OptIn(ExperimentalAnimationApi::class)

package com.example.buspayment

import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.example.buspayment.funtions.NotificationService
import com.example.buspayment.navigations.SetupNavGraph
import com.example.buspayment.realtimeDB.repository.DBRepository
import com.example.buspayment.ui.theme.BusPaymentTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.stripe.android.PaymentConfiguration
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		DBRepository.setContext(this)
		PaymentConfiguration.init(
			this,
			"pk_test_51Iem6QBbLdAJxHXGLSIrAQ3lqaEGBB3b4ZX7h0FsY21lxW4eIv3tbGeSsuFuKeHocD0nS0NpQGvGvRVe00VazD0N00mTPzU5nw"
		)
		NotificationService(applicationContext).createNotificationChannel()
		val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
		val activeNetworkInfo = connectivityManager.activeNetworkInfo
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
						if (activeNetworkInfo != null) {
							SetupNavGraph(navController)
						} else {
							Text("Connection error")
						}
					}
				}
			}
		}
	}
}


