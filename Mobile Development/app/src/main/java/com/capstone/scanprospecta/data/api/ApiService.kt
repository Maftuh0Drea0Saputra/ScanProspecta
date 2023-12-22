package com.capstone.scanprospecta.data.api

import com.capstone.scanprospecta.data.response.LoginResponse
import com.capstone.scanprospecta.data.response.RegisterResponse
import com.capstone.scanprospecta.data.response.ResumeResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @POST("authentication/register")
    suspend fun register(
        @Body requestBody: Map<String, String>
    ): RegisterResponse

    @POST("authentication/login")
    suspend fun login(
        @Body requestBody: Map<String, String>
    ): LoginResponse

    @Multipart
    @POST("predict")
    suspend fun postJobRecom(
        @Part file: MultipartBody.Part,
        @Part("predictions") predictions: RequestBody
    ): JobRecomResponse

    @GET("resume_example")
    fun getResumeExample(
        @Header("Authorization") token: String
    ): ResumeResponse
}