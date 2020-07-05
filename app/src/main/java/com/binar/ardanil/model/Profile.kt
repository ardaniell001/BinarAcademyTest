package com.binar.ardanil.model

import java.io.Serializable

data class Profile(
	val name: String,
	val email: String,
	val phone: String,
	val avatar: String
) : Serializable