package com.capstone.scanprospecta.di

import android.content.Context
import com.capstone.scanprospecta.data.api.ApiConfig
import com.capstone.scanprospecta.data.repository.UserRepository
import com.capstone.scanprospecta.data.preference.UserPreference
import com.capstone.scanprospecta.data.preference.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val preference = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(preference,apiService)
    }
}