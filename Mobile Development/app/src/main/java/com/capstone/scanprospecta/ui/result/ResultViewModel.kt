package com.capstone.scanprospecta.ui.result

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.scanprospecta.data.repository.JobRecomRepository
import com.capstone.scanprospecta.data.response.DetailItem
import com.capstone.scanprospecta.di.Injection
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

class ResultViewModel(private val repository: JobRecomRepository): ViewModel() {

    private val _jobRecomResult = repository.jobRecomResult
    val jobRecomResult get() = _jobRecomResult

    @OptIn(DelicateCoroutinesApi::class)
    fun postJobRecom(imageFile: File, data: List<DetailItem?>?) {
        GlobalScope.launch {
            repository.postJobRecom(imageFile, data)
        }
    }

    class ResultViewModelFactory private constructor (private val repository: JobRecomRepository):
        ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ResultViewModel::class.java)) {
                return ResultViewModel(repository) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }

        companion object {
            @Volatile
            private var INSTANCE: ResultViewModelFactory? = null
            @JvmStatic
            fun getInstance(context: Context): ResultViewModelFactory {
                if (INSTANCE == null) {
                    synchronized(ResultViewModelFactory::class.java) {
                        INSTANCE = ResultViewModelFactory(Injection.provideJobRecomRepository())
                    }
                }
                return INSTANCE as ResultViewModelFactory
            }
        }
    }
}