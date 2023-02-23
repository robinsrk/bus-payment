@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.buspayment.screens.user

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.util.Log
import android.util.Size
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.core.app.ActivityCompat
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
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.google.maps.android.PolyUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.random.Random

// api TIP6644qAAr1r0mv_b73cIoi3UYZ3TLUS2B39A4jnLbJJ959q2Fi8Z6Bf7PM26WL
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScanScreen(
	navController: NavController,
	viewModel: RealtimeViewModel = hiltViewModel()
) {
	val context = LocalContext.current
	val distance = viewModel.distRes.value
	val scope = rememberCoroutineScope()
	val buses = viewModel.busRes.value
	val time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm a"))
	val date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/YYYY"))
	var user by remember { mutableStateOf(listOf<User>()) }
	var bus by remember { mutableStateOf(RealtimeBusResponse.BusResponse()) }
	val mUserViewModel: UserViewModel =
		viewModel(factory = UserViewModel.UserViewModelFactory(context.applicationContext as Application))
	user = mUserViewModel.readUser.observeAsState(listOf()).value
	var code by remember { mutableStateOf("") }
	var location by remember { mutableStateOf("") }
	var person by remember { mutableStateOf(1f) }
	var loading by remember { mutableStateOf(false) }
	var isFromExpanded by remember { mutableStateOf(false) }
	var isToExpanded by remember { mutableStateOf(false) }
	val lifeCycleOwner = LocalLifecycleOwner.current
	val fromList = mutableListOf<String>()
	val toList = mutableListOf<String>()
	var fromPrice by remember { mutableStateOf(0.0) }
	var toPrice by remember { mutableStateOf<Double>(0.0) }
	var price by remember { mutableStateOf(0.0) }
	val initialLocation = LocationServices.getFusedLocationProviderClient(context)
//	var rs: Location? = null
	
	val xpoints = mutableListOf<LatLng>()
	xpoints.add(LatLng(23.955673, 90.288608))
	xpoints.add(LatLng(23.955595, 90.289075))
	xpoints.add(LatLng(23.955165, 90.289126))
	xpoints.add(LatLng(23.955121, 90.288482))
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
	LaunchedEffect(key1 = true) {
	}
	LaunchedEffect(code) {
		if (code.isNotEmpty()) {
			loading = true
			buses.bus.map { item ->
				if (code == item.bus!!.id) {
					bus = item.bus
				}
			}
			when {
				ActivityCompat.checkSelfPermission(
					context,
					Manifest.permission.ACCESS_FINE_LOCATION
				) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
					context,
					Manifest.permission.ACCESS_COARSE_LOCATION
				) != PackageManager.PERMISSION_GRANTED -> {
					// TODO: Consider calling
					//    ActivityCompat#requestPermissions
					// here to request the missing permissions, and then overriding
					//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
					//                                          int[] grantResults)
					// to handle the case where the user grants the permission. See the documentation
					// for ActivityCompat#requestPermissions for more details.
				}
			}
			initialLocation.getCurrentLocation(
				LocationRequest.PRIORITY_HIGH_ACCURACY,
				object : CancellationToken() {
					override fun onCanceledRequested(p0: OnTokenCanceledListener) =
						CancellationTokenSource().token
					
					override fun isCancellationRequested() = false
				})
				.addOnSuccessListener { location: Location? ->
					if (location == null)
						Toast.makeText(context, "Cannot get location.", Toast.LENGTH_SHORT).show()
					else {
						Toast.makeText(context, "Location updated", Toast.LENGTH_SHORT).show()
					}
					
				}
			initialLocation.lastLocation.addOnCompleteListener { task ->
				val rs = task.result
				if (rs != null) {
					distance.dist.forEach { item ->
						val points = mutableListOf<LatLng>()
						points.add(LatLng(item.dist!!.lat1, item.dist.lng1))
						points.add(LatLng(item.dist.lat2, item.dist.lng2))
						points.add(LatLng(item.dist.lat3, item.dist.lng3))
						points.add(LatLng(item.dist.lat4, item.dist.lng4))
						points.forEach {
							Log.d("location test", "${it.latitude},${it.longitude} ${points.size}")
						}
						val test = PolyUtil.containsLocation(rs.latitude, rs.longitude, points, true)
						Log.d("location test", "${rs.latitude},${rs.longitude} ${test}")
						if (test) {
							Log.d("location test", rs.longitude.toString())
							location = item.dist.name
							selectedFrom = item.dist.name
						}
//			Log.d("test location", "$test")
//			location = "${rs.latitude} ${rs.longitude} $test"
					}
				}
			}
			loading = false
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
	val icon = if (isToExpanded)
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
		when {
			ActivityCompat.checkSelfPermission(
				context,
				Manifest.permission.ACCESS_FINE_LOCATION
			) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
				context,
				Manifest.permission.ACCESS_COARSE_LOCATION
			) != PackageManager.PERMISSION_GRANTED -> {
				// TODO: Consider calling
				//    ActivityCompat#requestPermissions
				// here to request the missing permissions, and then overriding
				//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
				//                                          int[] grantResults)
				// to handle the case where the user grants the permission. See the documentation
				// for ActivityCompat#requestPermissions for more details.
			}
		}
	}
	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center,
		modifier = Modifier.fillMaxSize()
	) {
		if (bus.name.isNotEmpty() && !loading) {
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
								colors = TextFieldDefaults.outlinedTextFieldColors(
									disabledTextColor = LocalContentColor.current.copy(LocalContentAlpha.current),
									disabledBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = ContentAlpha.high),
									disabledLabelColor = MaterialTheme.colorScheme.onSurface.copy(ContentAlpha.high)
								),
								enabled = false,
								modifier = Modifier
									.fillMaxWidth()
									.onGloballyPositioned { coordinates ->
										//This value is used to assign to the DropDown the same width
										textFieldSize = coordinates.size.toSize()
									},
								trailingIcon = {
									Icon(icon, "contentDescription",
										Modifier.clickable { isFromExpanded = !isFromExpanded })
								},
								label = { Text("From") },
							)
							DropdownMenu(
								expanded = isFromExpanded,
								onDismissRequest = { isFromExpanded = false },
								modifier = Modifier
									.width(with(LocalDensity.current) { textFieldSize.width.toDp() })
							) {
								toList.forEach { label ->
									DropdownMenuItem(
										onClick = {
											selectedFrom = label
											isFromExpanded = !isFromExpanded
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
								colors = TextFieldDefaults.outlinedTextFieldColors(
									disabledTextColor = LocalContentColor.current.copy(LocalContentAlpha.current),
									disabledBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = ContentAlpha.high),
									disabledLabelColor = MaterialTheme.colorScheme.onSurface.copy(ContentAlpha.high)
								),
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
										time = time,
										date = date,
										passNum = person.toInt(),
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
		} else if (loading) {
			CircularProgressIndicator()
		}
//		else if (!loading && bus.name.isNotEmpty() && location.isEmpty()) {
//			Column {
//				Text(text = "Not available in your location")
//			}
//		}
		else {
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
					if (code.isNotEmpty() && bus.name.isEmpty() && !loading)
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