package com.capstone.scanprospecta.data

import androidx.lifecycle.liveData
import com.capstone.scanprospecta.data.api.ApiService
import com.capstone.scanprospecta.data.response.LoginResponse
import com.capstone.scanprospecta.data.response.RegisterResponse
import com.google.gson.Gson
import retrofit2.HttpException

class UserRepository (
    private val apiService: ApiService
) {
    fun login(email: String, password: String) = liveData {
        emit(ResultState.loading)
        try {
            val successResponse = apiService.login(email, password)
            emit(ResultState.success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
            emit(errorResponse.message?.let { ResultState.error(it)})
        }
    }

    fun signup(name: String, email: String, password: String) = liveData {
        emit(ResultState.loading)
        try {
            val successResponse = apiService.register(name, email, password)
            emit(ResultState.success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
            emit(errorResponse.message?.let { ResultState.error(it)})
        }

    }
}