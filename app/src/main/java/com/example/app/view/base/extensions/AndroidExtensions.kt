package com.example.app.view.base.extensions

import android.view.View

/**
 * If it's true then view has visibility status VISIBLE else GONE
 */
var View.isVisible: Boolean
	get() {
		return this.visibility == View.VISIBLE
	}
	set(value) {
		if (value) {
			this.visibility = View.VISIBLE
		} else {
			this.visibility = View.GONE
		}
	}