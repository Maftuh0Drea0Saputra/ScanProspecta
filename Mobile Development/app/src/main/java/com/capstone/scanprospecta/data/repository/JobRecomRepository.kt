package com.capstone.scanprospecta.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.scanprospecta.data.ResultState
import com.capstone.scanprospecta.data.api.ApiService
import com.capstone.scanprospecta.data.response.JobRecomResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class JobRecomRepository (
    private val apiService: ApiService
) {

    private val _jobRecomResult = MutableLiveData<ResultState<JobRecomResponse>>()
    val jobRecomResult: LiveData<ResultState<JobRecomResponse>> = _jobRecomResult

    suspend fun postJobRecom(imageFile: File, data: List<String>) {
        _jobRecomResult.postValue(ResultState.loading)

        val textPlainMediaType = "text/plain".toMediaType()
        val imageMediaType = "image/jpeg".toMediaTypeOrNull()
        val imageMultiPart: MultipartBody.Part =
            MultipartBody.Part.createFormData(
                "file",
                imageFile.name,
                imageFile.asRequestBody(imageMediaType)
            )
        val dataString = data.joinToString("\n")
        val dataRequestBody = dataString.toRequestBody(textPlainMediaType)

        try {
            val response = apiService.postJobRecom(
                imageMultiPart, dataRequestBody
            )
            ResultState.success(response)
        } catch (e: Exception) {
            ResultState.error(e.message.toString())
        }
    }

    companion object {
        @Volatile
        private var instance: JobRecomRepository? = null
        fun getInstance(
            apiService: ApiService
        ): JobRecomRepository =
            instance ?: synchronized(this) {
                instance ?: JobRecomRepository(apiService)
            }.also { instance = it }
    }
}