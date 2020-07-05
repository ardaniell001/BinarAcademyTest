package com.binar.ardanil.model

import com.squareup.moshi.Json
import java.io.Serializable

data class User(

	@Json(name="id")
	val id: Int? = null,

	@Json(name="email")
	val email: String? = null,

	@Json(name="first_name")
	val first_name: String? = null,

	@Json(name="last_name")
	val last_name: String? = null,

	@Json(name="avatar")
	val avatar: String? = null

) : Serializable