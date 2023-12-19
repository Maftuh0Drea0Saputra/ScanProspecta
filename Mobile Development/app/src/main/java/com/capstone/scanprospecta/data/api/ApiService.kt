package com.capstone.scanprospecta.data.api

import com.capstone.scanprospecta.data.response.LoginResponse
import com.capstone.scanprospecta.data.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
//    @FormUrlEncoded
    @POST("authentication/register")
    suspend fun register(
        @Body requestBody: Map<String, String>
//        @Query("name") name: String,
//        @Query("email") email: String,
//        @Query("password") password: String,
//        @Query("conf_password") conf_passwrod: String
    ): RegisterResponse

//    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ):LoginResponse
}