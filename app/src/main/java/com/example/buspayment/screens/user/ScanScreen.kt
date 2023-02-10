@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.buspayment.screens.user

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.util.Size
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.buspayment.funtions.QrCodeAnalysis
import com.example.buspayment.navigations.Screens
import com.example.buspayment.realtimeDB.ui.RealtimeViewModel
import com.example.buspayment.ui.theme.Typography
import com.google.android.gms.location.LocationServices
import kotlin.math.abs

@Composable
fun ScanScreen(
	navController: NavController,
	viewModel: RealtimeViewModel = hiltViewModel()
) {
	val res = viewModel.distRes.value
	val buses = viewModel.busRes.value
	var code by remember { mutableStateOf("") }
	var isExpanded by remember { mutableStateOf(false) }
	var isToExpanded by remember { mutableStateOf(false) }
	val context = LocalContext.current
	val lifeCycleOwner = LocalLifecycleOwner.current
	val fromList = mutableListOf<String>()
	val toList = mutableListOf<String>()
	var fromPrice by remember { mutableStateOf(0.0) }
	var toPrice by remember { mutableStateOf(0.0) }
	var price: Double = 0.0
	if (fromPrice > 0 && toPrice > 0) {
		price = abs((fromPrice - toPrice) * 2.5)
	}
	var selectedFrom by remember { mutableStateOf("") }
	val busList = mutableListOf<String>()
//	toList.remove(selectedFrom)
	var selectedTo by remember { mutableStateOf("") }
	if (res.dist.isNotEmpty()) {
		res.dist.forEach { item ->
			fromList.add(item.dist!!.name)
			toList.add(item.dist.name)
		}
	}
	if (buses.bus.isNotEmpty()) {
		buses.bus.forEach { item ->
			busList.add(item.bus!!.name)
		}
	}
	if (fromList.isNotEmpty() && toList.isNotEmpty()) {
		res.dist.forEach { item ->
			if (selectedFrom == item.dist!!.name) {
				fromPrice = item.dist.value
			}
			if (selectedTo == item.dist.name) {
				toPrice = item.dist.value
			}
			Log.d("Check price", "$fromPrice $toPrice $fromPrice-$toPrice")
		}
	}
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
								value = selectedFrom,
								onValueChange = { selectedFrom = it },
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
											selectedFrom = label
											isExpanded = !isExpanded
										},
										text = { Text(text = label) }
									)
								}
							}
							
						}
						Row {
							OutlinedTextField(
								value = selectedTo,
								onValueChange = { selectedTo = it },
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
											selectedTo = label
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
					OutlinedButton(
						onClick = { navController.navigate(Screens.UHome.route) },
						enabled = price > 0
					) {
						Text(text = "Proceed to payment $price taka")
					}
				}
			}
		} else {
			if (hasCameraPermission) {
				Column {
					Row(
						Modifier
							.fillMaxWidth()
							.background(Color.Red, RoundedCornerShape(0.dp, 0.dp, 30.dp, 30.dp))
							.padding(20.dp),
						horizontalArrangement = Arrangement.SpaceBetween,
						verticalAlignment = Alignment.CenterVertically
					) {
						Row {
							Icon(
								modifier = Modifier.clickable {
									navController.popBackStack()
								}, imageVector = Icons.Filled.ArrowBack, contentDescription = "Back button"
							)
						}
						Text("Scan for Bus", style = MaterialTheme.typography.headlineSmall)
						Row {
							Text(
								text = "",
							)
							
						}
					}
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
}