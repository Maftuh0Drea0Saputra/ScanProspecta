package com.capstone.scanprospecta.data.repository

import androidx.lifecycle.liveData
import com.capstone.scanprospecta.data.ResultState
import com.capstone.scanprospecta.data.api.ApiService
import com.capstone.scanprospecta.data.preference.UserModel
import com.capstone.scanprospecta.data.preference.UserPreference
import com.capstone.scanprospecta.data.response.LoginResponse
import com.capstone.scanprospecta.data.response.RegisterResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class UserRepository (
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {
    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }
    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    fun login(requestBody:Map<String, String>) = liveData {
        emit(ResultState.loading)
        try {
            val successResponse = apiService.login(requestBody)
            emit(ResultState.success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
            emit(errorResponse.message?.let { ResultState.error(it)})
        }

    }

    fun register(requestBody: Map<String, String>) = liveData {
        emit(ResultState.loading)
        try {
            val successResponse = apiService.register(requestBody)
            emit(ResultState.success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
            emit(errorResponse.message.let { ResultState.error(it) })
        }

    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository( userPreference,apiService)
            }.also { instance = it }
    }
}