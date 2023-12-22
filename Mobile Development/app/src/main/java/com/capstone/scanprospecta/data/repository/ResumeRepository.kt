package com.capstone.scanprospecta.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.scanprospecta.data.ResultState
import com.capstone.scanprospecta.data.api.ApiService
import com.capstone.scanprospecta.data.response.DataItem


class ResumeRepository(
    private val apiService: ApiService
) {

    fun getResumeExample(token: String): LiveData<ResultState<List<DataItem>>> {
        val resumeExample = MutableLiveData<ResultState<List<DataItem>>>()
        resumeExample.value = ResultState.loading
        try {
            val response = apiService.getResumeExample(token)
            ResultState.success(response)
        } catch (e: Exception) {
            ResultState.error(e.message.toString())
        }
        return resumeExample
    }

    companion object {
        @Volatile
        private var instance: ResumeRepository? = null
        fun getInstance(
            apiService: ApiService
        ): ResumeRepository =
            instance ?: synchronized(this) {
                instance ?: ResumeRepository(apiService)
            }.also { instance = it }
    }
}
