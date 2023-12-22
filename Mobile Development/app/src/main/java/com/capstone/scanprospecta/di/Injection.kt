package com.capstone.scanprospecta.di

import android.content.Context
import com.capstone.scanprospecta.data.repository.UserRepository
import com.capstone.scanprospecta.data.api.apiServiceEndpoint1
import com.capstone.scanprospecta.data.api.apiServiceEndpoint2
import com.capstone.scanprospecta.data.preference.UserPreference
import com.capstone.scanprospecta.data.preference.dataStore
import com.capstone.scanprospecta.data.repository.JobRecomRepository
import com.capstone.scanprospecta.data.repository.ResumeRepository

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val preference = UserPreference.getInstance(context.dataStore)
        val apiService = apiServiceEndpoint1
        return UserRepository.getInstance(preference,apiService)
    }

    fun provideJobRecomRepository(): JobRecomRepository {
        val apiService = apiServiceEndpoint2
        return JobRecomRepository.getInstance(apiService)
    }

    fun provideResumeRepository(): ResumeRepository {
        val apiService = apiServiceEndpoint1
        return ResumeRepository.getInstance(apiService)
    }
}