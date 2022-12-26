package com.example.buspayment.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.buspayment.R

@Composable
fun LogoImage() {
	Box(
		modifier = Modifier
			.height(200.dp)
			.width(300.dp)
			.clip(RoundedCornerShape(20.dp))
	) {
		Image(
			painter = painterResource(id = R.drawable.undraw_bus_stop_re_h8ej),
			contentDescription = "Logo"
		)
	}
}

@Composable
fun LogoIcon() {
	Box(
		modifier = Modifier
			.height(100.dp)
			.padding(20.dp)
			.width(100.dp),

		contentAlignment = Alignment.Center
	) {
		Image(
			painter = painterResource(id = R.drawable.ticket_logo),
			contentDescription = "Logo"
		)
	}
}