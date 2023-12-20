package com.capstone.scanprospecta.di

import android.content.Context
import com.capstone.scanprospecta.data.repository.UserRepository
import com.capstone.scanprospecta.data.api.ApiConfig
import com.capstone.scanprospecta.data.api.apiServiceEndpoint1
import com.capstone.scanprospecta.data.preference.UserPreference
import com.capstone.scanprospecta.data.preference.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val preference = UserPreference.getInstance(context.dataStore)
        val apiService = apiServiceEndpoint1
        return UserRepository.getInstance(preference,apiService)
    }
}