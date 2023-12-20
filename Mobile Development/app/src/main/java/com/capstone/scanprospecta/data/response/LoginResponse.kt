package com.capstone.scanprospecta.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("accessToken")
	val Token: String,

	@field:SerializedName("client_id")
	val clientId: Int,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("refreshToken")
	val refreshToken: String
)
