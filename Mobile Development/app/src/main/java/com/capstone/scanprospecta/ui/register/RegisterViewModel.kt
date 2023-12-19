package com.capstone.scanprospecta.ui.register

import androidx.lifecycle.ViewModel
import com.capstone.scanprospecta.data.UserRepository

class RegisterViewModel (private val repository: UserRepository): ViewModel() {
    fun register(name: String,email: String, password: String) = repository.signup(name,email,password)

}