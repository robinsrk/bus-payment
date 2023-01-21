@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.buspayment.screens.user

import android.Manifest
import android.content.pm.PackageManager
import android.util.Size
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.buspayment.funtions.QrCodeAnalysis
import com.example.buspayment.navigations.Screens
import com.example.buspayment.ui.theme.Typography
import com.google.android.gms.location.LocationServices

@Composable
fun ScanScreen(navController: NavController) {
	var code by remember { mutableStateOf("") }
	var isExpanded by remember { mutableStateOf(false) }
	var isToExpanded by remember { mutableStateOf(false) }
	val context = LocalContext.current
	val lifeCycleOwner = LocalLifecycleOwner.current
	val fromList = mutableListOf("Dhaka", "Savar")
	val toList = mutableListOf("Dhaka", "Savar")
	val busList = listOf("Thikana", "Itihas")
	var selectedItem by remember { mutableStateOf("Dhaka") }
	toList.remove(selectedItem)
	var selectedToItem = toList[0]
	var initialLocation = LocationServices.getFusedLocationProviderClient(context)
	val icon = if (isExpanded)
		Icons.Filled.KeyboardArrowUp
	else
		Icons.Filled.KeyboardArrowDown
	var textFieldSize by remember { mutableStateOf(androidx.compose.ui.geometry.Size.Zero) }
	val cameraProviderFuture = remember {
		ProcessCameraProvider.getInstance(context)
	}
	var hasCameraPermission by remember {
		mutableStateOf(
			ContextCompat.checkSelfPermission(
				context,
				Manifest.permission.CAMERA
			) == PackageManager.PERMISSION_GRANTED
		)
	}
	
	val launcher = rememberLauncherForActivityResult(
		contract = ActivityResultContracts.RequestPermission(),
		onResult = { granted ->
			hasCameraPermission = granted
		}
	)
	LaunchedEffect(key1 = true) {
		launcher.launch(Manifest.permission.CAMERA)
	}
	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center,
		modifier = Modifier.fillMaxSize()
	) {
		if (code in busList) {
			Card(
				modifier = Modifier
					.width(300.dp)
//					.height(400.dp),
			) {
				Column(modifier = Modifier.padding(20.dp, 10.dp)) {
					Text(
						text = code,
						style = Typography.headlineLarge,
					)
					Text(text = "From 'Chandra' to 'Mirpur'")
					Text(text = "Rate per kilometer: 2.50 taka")
					Column(Modifier.padding(20.dp)) {
						Row {
							OutlinedTextField(
								value = selectedItem,
								onValueChange = { selectedItem = it },
								enabled = false,
								modifier = Modifier
									.fillMaxWidth()
									.onGloballyPositioned { coordinates ->
										//This value is used to assign to the DropDown the same width
										textFieldSize = coordinates.size.toSize()
									}
									.clickable { isExpanded = !isExpanded },
								
								label = { Text("From") },
								trailingIcon = {
									Icon(icon, "contentDescription",
										Modifier.clickable { isExpanded = !isExpanded })
								}
							)
							DropdownMenu(
								expanded = isExpanded,
								onDismissRequest = { isExpanded = false },
								modifier = Modifier
									.width(with(LocalDensity.current) { textFieldSize.width.toDp() })
							) {
								fromList.forEach { label ->
									DropdownMenuItem(
										onClick = {
											selectedItem = label
											isExpanded = !isExpanded
										},
										text = { Text(text = label) }
									)
								}
							}
							
						}
						Row {
							OutlinedTextField(
								value = selectedToItem,
								onValueChange = { selectedToItem = it },
								enabled = false,
								modifier = Modifier
									.fillMaxWidth()
									.onGloballyPositioned { coordinates ->
										//This value is used to assign to the DropDown the same width
										textFieldSize = coordinates.size.toSize()
									}
									.clickable { isToExpanded = !isToExpanded },
								
								label = { Text("To") },
								trailingIcon = {
									Icon(icon, "contentDescription",
										Modifier.clickable { isToExpanded = !isToExpanded })
								}
							)
							DropdownMenu(
								expanded = isToExpanded,
								onDismissRequest = { isToExpanded = false },
								modifier = Modifier
									.width(with(LocalDensity.current) { textFieldSize.width.toDp() })
							) {
								toList.forEach { label ->
									DropdownMenuItem(
										onClick = {
											selectedToItem = label
											isToExpanded = !isToExpanded
										},
										text = { Text(text = label) }
									)
								}
							}
							
						}
					}
				}
				Column(
					verticalArrangement = Arrangement.Center,
					horizontalAlignment = Alignment.CenterHorizontally,
					modifier = Modifier.fillMaxWidth()
				) {
					OutlinedButton(onClick = { navController.navigate(Screens.UHome.route) }) {
						Text(text = "Proceed to payment")
					}
				}
			}
		} else if (code == "Itihas") {
			
			Box {
				Text(text = code, style = Typography.titleLarge)
			}
		} else {
			if (hasCameraPermission) {
				AndroidView(
					factory = { context ->
						val previewView = PreviewView(context)
						val preview = Preview.Builder().build()
						val selector = CameraSelector.Builder()
							.requireLensFacing(CameraSelector.LENS_FACING_BACK)
							.build()
						preview.setSurfaceProvider(previewView.surfaceProvider)
						val imageAnalysis = ImageAnalysis.Builder()
							.setTargetResolution(
								Size(
									previewView.width,
									previewView.height
								)
							)
							.setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
							.build()
						imageAnalysis.setAnalyzer(
							ContextCompat.getMainExecutor(context),
							QrCodeAnalysis { result ->
								code = result
							}
						)
						try {
							cameraProviderFuture.get().bindToLifecycle(
								lifeCycleOwner,
								selector,
								preview,
								imageAnalysis
							)
						} catch (e: Exception) {
							e.printStackTrace()
						}
						previewView
					},
					modifier = Modifier
						.weight(1f)
						.padding(
							40.dp,
							200.dp
						)
						.clip(RoundedCornerShape(40.dp))
				)
			}
		}
	}
}