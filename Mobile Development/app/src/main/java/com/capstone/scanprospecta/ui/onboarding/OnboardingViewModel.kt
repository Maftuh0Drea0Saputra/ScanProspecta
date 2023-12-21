package com.capstone.scanprospecta.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.capstone.scanprospecta.data.preference.UserPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OnboardingViewModel(private val userPreference: UserPreference): ViewModel() {

    class OnboardingViewModelFactory private constructor(private val userPreference: UserPreference) :
            ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(OnboardingViewModel::class.java)) {
                return OnboardingViewModel(userPreference) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }

        companion object {
            @Volatile
            private var instance: OnboardingViewModelFactory? = null

            fun getInstance(userPreference: UserPreference): OnboardingViewModelFactory =
                instance ?: synchronized(this) {
                    instance ?: OnboardingViewModelFactory(userPreference)
                }.also { instance = it }
        }
    }
}