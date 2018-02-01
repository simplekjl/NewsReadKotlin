package com.dev.newsread.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast


@Suppress("UNCHECKED_CAST")
fun <T : View> View.find(id: Int): T = this.findViewById(id) as T

fun Context.showToast(charSequence: CharSequence, length: Int = Toast.LENGTH_LONG) {
	Toast.makeText(this, charSequence, length).show()
}

inline fun <reified T : Activity> Context.startActivity() =
		this.startActivity(newIntent<T>())

inline fun <reified T : Activity> Context.newIntent(): Intent =
		Intent(this, T::class.java)

