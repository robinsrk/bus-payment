package com.example.buspayment.funtions

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast

class ToastService() {
	companion object {
		@SuppressLint("StaticFieldLeak")
		private lateinit var context: Context
		fun setContext(con: Context) {
			context = con
		}
	}
	
	fun showToast(text: String) {
		Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
	}
}