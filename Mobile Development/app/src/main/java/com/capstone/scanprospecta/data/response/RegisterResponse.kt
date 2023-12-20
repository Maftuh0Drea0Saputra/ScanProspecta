package com.capstone.scanprospecta.data.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String
)

data class Data(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("email")
	val email: String
)
