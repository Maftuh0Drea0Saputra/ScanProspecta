package com.capstone.scanprospecta.ui.register

import androidx.lifecycle.ViewModel
import com.capstone.scanprospecta.data.repository.UserRepository

class RegisterViewModel (private val repository: UserRepository): ViewModel() {
    fun register(requestBody: Map<String, String>) = repository.register(requestBody)

}