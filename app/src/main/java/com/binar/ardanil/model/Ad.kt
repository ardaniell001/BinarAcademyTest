package com.binar.ardanil.model

import com.squareup.moshi.Json

data class Ad(

	@Json(name="company")
	val company: String? = null,

	@Json(name="text")
	val text: String? = null,

	@Json(name="url")
	val url: String? = null
)