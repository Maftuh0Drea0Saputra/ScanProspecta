package com.capstone.scanprospecta.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        fun getApiService(): ApiService {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://scanprospecta-api-vd4ncija7a-et.a.run.app")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}

//val apiServiceEndpoint1 = ApiConfig.getApiService("https://scanprospecta.et.r.appspot.com/")
//val apiServiceEndpoint2 = ApiConfig.getApiService("https://m296bsy1181-scanprospecta.hf.space/")