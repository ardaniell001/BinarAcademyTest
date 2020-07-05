package com.binar.ardanil.model

import com.squareup.moshi.Json

data class UserResponse(

	@Json(name="per_page")
	val per_page: Int? = null,

	@Json(name="total")
	val total: Int? = null,

	@Json(name="ad")
	val ad: Ad? = null,

	@Json(name="data")
	val data: MutableList<User?>? = null,

	@Json(name="page")
	val page: Int? = null,

	@Json(name="total_pages")
	val totalPages: Int? = null
)