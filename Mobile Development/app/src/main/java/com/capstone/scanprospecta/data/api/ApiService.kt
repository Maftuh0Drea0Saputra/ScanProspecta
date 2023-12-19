package com.capstone.scanprospecta.data.api

import com.capstone.scanprospecta.data.response.LoginResponse
import com.capstone.scanprospecta.data.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("authentication/register")
    suspend fun register(
        @Body requestBody: Map<String, String>
    ): RegisterResponse

    @POST("authentication/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse
}