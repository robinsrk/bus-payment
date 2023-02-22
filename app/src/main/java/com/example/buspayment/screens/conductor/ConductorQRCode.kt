package com.example.buspayment.screens.conductor

import android.app.Application
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.buspayment.data.User
import com.example.buspayment.data.UserViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter

@Composable
fun ConductorQRCode(
	navController: NavController
) {
	val context = LocalContext.current
	var user by remember { mutableStateOf(listOf<User>()) }
	val mUserViewModel: UserViewModel =
		viewModel(factory = UserViewModel.UserViewModelFactory(context.applicationContext as Application))
	user = mUserViewModel.readUser.observeAsState(emptyList()).value
	var id by remember { mutableStateOf("") }
	LaunchedEffect(user) {
		
		if (user.isNotEmpty()) {
			id = user[0].email.substringBefore("@")
		}
		
	}
	if (id.isNotEmpty()) {
		Column(
			modifier = Modifier.fillMaxSize(),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center
		) {
			AndroidView(
				factory = { ctx ->
					ImageView(ctx).apply {
						val size = 512
						val hints = hashMapOf<EncodeHintType, Int>().also {
							it[EncodeHintType.MARGIN] = 1
						}
						val bits = QRCodeWriter().encode(
							id,
							BarcodeFormat.QR_CODE,
							size,
							size,
							hints
						)
						val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
							for (x in 0 until size) {
								for (y in 0 until size) {
									it.setPixel(
										x,
										y,
										if (bits[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE
									)
								}
							}
						}
						setImageBitmap(bitmap)
					}
				},
			)
			Text(text = "ID: $id")
		}
	}
}