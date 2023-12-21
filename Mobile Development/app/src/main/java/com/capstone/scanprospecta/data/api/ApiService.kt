package com.capstone.scanprospecta.data.api

import com.capstone.scanprospecta.data.response.LoginResponse
import com.capstone.scanprospecta.data.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @POST("/authentication/register")
    suspend fun register(
        @Body requestBody: Map<String, String>
    ): RegisterResponse


    @POST("/authentication/login")
    suspend fun login(
        @Body requestBody: Map<String, String>
    ): LoginResponse
}