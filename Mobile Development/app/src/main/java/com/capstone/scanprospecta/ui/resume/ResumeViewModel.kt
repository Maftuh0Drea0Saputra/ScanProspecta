package com.capstone.scanprospecta.ui.resume

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.capstone.scanprospecta.data.ResultState
import com.capstone.scanprospecta.data.preference.UserPreference
import com.capstone.scanprospecta.data.repository.ResumeRepository
import com.capstone.scanprospecta.data.response.DataItem
import com.capstone.scanprospecta.di.Injection
import kotlinx.coroutines.flow.map

class ResumeViewModel(
    private val repository: ResumeRepository,
    private val preference: UserPreference
) : ViewModel() {

    fun getResumeExample(token: String): LiveData<ResultState<List<DataItem>>> {
        return repository.getResumeExample(token)
    }

    fun checkTokenAvailable(): LiveData<String> {
        return preference.getSession()
            .map { userModel ->
                userModel.token
            }
            .asLiveData()
    }

    class ResumeViewModelFactory private constructor (
        private val repository: ResumeRepository,
        private val preference: UserPreference
    ):
        ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ResumeViewModel::class.java)) {
                return ResumeViewModel(repository, preference) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }

        companion object {
            @Volatile
            private var INSTANCE: ResumeViewModelFactory? = null
            @JvmStatic
            fun getInstance(
                context: Context,
                preference: UserPreference
            ): ResumeViewModelFactory {
                if (INSTANCE == null) {
                    synchronized(ResumeViewModelFactory::class.java) {
                        INSTANCE = ResumeViewModelFactory(
                            Injection.provideResumeRepository(),
                            preference
                        )
                    }
                }
                return INSTANCE as ResumeViewModelFactory
            }
        }
    }
}