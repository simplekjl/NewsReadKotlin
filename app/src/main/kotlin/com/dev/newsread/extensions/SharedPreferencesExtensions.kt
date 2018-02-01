package com.dev.newsread.extensions

import android.content.SharedPreferences

inline fun SharedPreferences.apply(action: SharedPreferences.Editor.() -> Unit) {
	val editor = this.edit()
	action.invoke(editor)
	editor.apply()
}