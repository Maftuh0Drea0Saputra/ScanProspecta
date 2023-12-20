package com.capstone.scanprospecta.ui.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.scanprospecta.data.preference.UserModel
import com.capstone.scanprospecta.data.preference.UserPreference
import com.capstone.scanprospecta.data.repository.UserRepository
import com.capstone.scanprospecta.di.Injection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class LoginViewModel(
    private val userRepository: UserRepository
): ViewModel() {

    fun login(email: String, password: String) = userRepository.login(email, password)

    fun register(requestBody: Map<String, String>) = userRepository.register(requestBody)

    fun saveSession(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.saveSession(token)
        }
    }

    fun checkIfFirstTime(): LiveData<Boolean> {
        return userRepository.isFirstTime().asLiveData()
    }

}