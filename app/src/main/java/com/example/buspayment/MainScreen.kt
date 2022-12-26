package com.example.buspayment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.buspayment.navigations.SetupNavGraph
import com.example.buspayment.ui.theme.BusPaymentTheme

class MainActivity : ComponentActivity() {

	private lateinit var navController: NavHostController

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
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


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
	var email by remember { mutableStateOf("") }
	BusPaymentTheme {
		Column(
			modifier = Modifier.fillMaxSize(),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center
		) {
		}
	}
}
