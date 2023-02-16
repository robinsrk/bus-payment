@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.buspayment.screens.user

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.util.Size
import android.widget.Toast
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.buspayment.data.User
import com.example.buspayment.data.UserViewModel
import com.example.buspayment.funtions.QrCodeAnalysis
import com.example.buspayment.navigations.Screens
import com.example.buspayment.realtimeDB.responses.RealtimeBusResponse
import com.example.buspayment.realtimeDB.responses.RealtimePaymentResponse
import com.example.buspayment.realtimeDB.ui.RealtimeViewModel
import com.example.buspayment.ui.theme.Typography
import com.example.buspayment.utils.ResultState
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.random.Random

@Composable
fun ScanScreen(
	navController: NavController,
	viewModel: RealtimeViewModel = hiltViewModel()
) {
	val context = LocalContext.current
	val distance = viewModel.distRes.value
	val scope = rememberCoroutineScope()
	val buses = viewModel.busRes.value
	var user by remember { mutableStateOf(listOf<User>()) }
//	var bus by remember { mutableStateOf(listOf<RealtimeBusResponse.BusResponse>()) }
//	var bus: RealtimeBusResponse.BusResponse? by remember
	var bus by remember { mutableStateOf(RealtimeBusResponse.BusResponse()) }
	val mUserViewModel: UserViewModel =
		viewModel(factory = UserViewModel.UserViewModelFactory(context.applicationContext as Application))
	user = mUserViewModel.readUser.observeAsState(listOf()).value
	var code by remember { mutableStateOf("") }
	var person by remember { mutableStateOf(1f) }
	var isExpanded by remember { mutableStateOf(false) }
	var isToExpanded by remember { mutableStateOf(false) }
	val lifeCycleOwner = LocalLifecycleOwner.current
	val fromList = mutableListOf<String>()
	val toList = mutableListOf<String>()
	var fromPrice by remember { mutableStateOf(0.0) }
	var toPrice by remember { mutableStateOf<Double>(0.0) }
	var price by remember { mutableStateOf(0.0) }
	if (fromPrice > 0 && toPrice > 0) {
		price = abs((fromPrice - toPrice) * 2.5) * (person.toInt())
	}
	var selectedFrom by remember { mutableStateOf("") }
	val busList = mutableListOf<String>()
//	toList.remove(selectedFrom)
	var selectedTo by remember { mutableStateOf("") }
	if (distance.dist.isNotEmpty()) {
		distance.dist.forEach { item ->
			fromList.add(item.dist!!.name)
			toList.add(item.dist.name)
		}
	}
	LaunchedEffect(code) {
		buses.bus.map { item ->
			if (code == item.bus!!.id) {
				bus = item.bus
			}
		}
	}
	if (buses.bus.isNotEmpty()) {
		buses.bus.forEach { item ->
			busList.add(item.bus!!.id)
		}
	}
	if (fromList.isNotEmpty() && toList.isNotEmpty()) {
		distance.dist.forEach { item ->
			if (selectedFrom == item.dist!!.name) {
				fromPrice = item.dist.value
			}
			if (selectedTo == item.dist.name) {
				toPrice = item.dist.value
			}
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
		if (bus.name.isNotEmpty()) {
			Card(
				modifier = Modifier
					.width(300.dp)
//					.height(400.dp),
			) {
				Column(modifier = Modifier.padding(20.dp, 10.dp)) {
					Text(
						text = bus.name,
						style = Typography.headlineLarge,
					)
					Text(text = "From '${bus.startAddress}' to '${bus.endAddress}'")
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
						Row(modifier = Modifier.fillMaxWidth()) {
							Text(text = "${person.roundToInt()}")
							Slider(
								value = person,
								onValueChange = {
									person = it
								},
								steps = 2,
								valueRange = 1f..4f,
								thumb = {
									Icon(imageVector = Icons.Filled.Person, contentDescription = "", tint = Color.Red)
								}
							)
						}
					}
				}
				Column(
					verticalArrangement = Arrangement.Center,
					horizontalAlignment = Alignment.CenterHorizontally,
					modifier = Modifier.fillMaxWidth()
				) {
					OutlinedButton(
						onClick = {
							scope.launch(Dispatchers.Main) {
								viewModel.submitPayment(
									RealtimePaymentResponse.PaymentResponse(
										"Pending",
										from = selectedFrom,
										to = selectedTo,
										fromUser = user[0].email.substringBefore("@"),
										toUser = code,
										paid = price,
										bus = bus.name,
										code = Random.nextInt(1000, 10000).toString()
									), from = user[0].email.substringBefore("@"), to = code
								).collect { response ->
									when (response) {
										is ResultState.Success -> {
											Toast.makeText(context, "Payment successful", Toast.LENGTH_SHORT).show()
											navController.navigate(Screens.UHome.route)
										}
										
										is ResultState.Failure -> {
											Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
										}
										
										is ResultState.Loading -> {}
									}
								}
							}
							scope.launch(Dispatchers.Main) {
								viewModel.updateBalance(-price, user[0].email.substringBefore("@"), code)
									.collect { response ->
										when (response) {
											is ResultState.Success -> {
											}
											
											is ResultState.Failure -> {
											}
											
											is ResultState.Loading -> {}
										}
									}
							}
						},
						enabled = price > 0
					) {
						Text(text = "Proceed to payment $price taka")
					}
				}
			}
		} else {
			if (hasCameraPermission) {
				Column(
					modifier = Modifier.fillMaxSize()
				) {
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
						Text("")
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
//									scope.launch(Dispatchers.Main) {
//										if (buses.bus.isNotEmpty()) {
//											bus = buses.bus.filter { item -> item.bus!!.id == code }[0].bus!!.name
//										}
//									}
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
					if (code.isNotEmpty() && bus.name.isEmpty())
						Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
							Text(
								text = "Invalid QR Code",
								style = MaterialTheme.typography.headlineMedium,
								color = Color.Red
							)
							
						}
				}
			}
		}
	}
}

