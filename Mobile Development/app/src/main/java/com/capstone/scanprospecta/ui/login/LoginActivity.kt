package com.capstone.scanprospecta.ui.login

import android.content.Context
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.capstone.scanprospecta.data.ResultState
import com.capstone.scanprospecta.data.preference.UserModel
import com.capstone.scanprospecta.data.preference.UserPreference
import com.capstone.scanprospecta.data.preference.dataStore
import com.capstone.scanprospecta.databinding.ActivityLoginBinding
import com.capstone.scanprospecta.ui.ViewModelFactory
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.capstone.scanprospecta.data.ResultState
import com.capstone.scanprospecta.data.preference.UserModel
import com.capstone.scanprospecta.databinding.ActivityLoginBinding
import com.capstone.scanprospecta.ui.ViewModelFactory
import com.capstone.scanprospecta.ui.home.HomeFragment
import com.capstone.scanprospecta.ui.main.MainActivity
import com.capstone.scanprospecta.ui.onboarding.OnboardingActivity
import com.capstone.scanprospecta.ui.register.RegisterActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")
class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupView()
    }

    override fun onResume() {
        super.onResume()
        initialCheck()
    }

    private fun initialCheck() {
        viewModel.checkIfFirstTime().observe(this) {
            if (it) {
                val intent = Intent(this, OnboardingActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }
    }

    private fun setupView() {
        binding.btnLogin.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()
            val result = viewModel.login(email, password)
            if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
                result.observe(this) {
                    when (it) {
                        is ResultState.loading -> {
                            showLoading(true)
                        }

                        is ResultState.error -> {
                            val error = it.error
                            showToast(error)
                            showLoading(false)
                        }

                        is ResultState.success -> {
                            val data = it.data
                            viewModel.saveSession(data.accessToken)
                            val message = it.data.message
                            showToast(message)
                            Log.d("LoginActivity", "Token: ${data.accessToken}")
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            showLoading(false)
                        }
                    }
                }
            } else {
                if (email.isNullOrEmpty()) binding.editTextEmail.error = "Email cannot be empty!"
                if (password.isNullOrEmpty()) binding.editTextPassword.error = "Password cannot be empty!"
            }
        setupView()
        userlogin()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction(message: String) {
        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java)) }

        AlertDialog.Builder(this@LoginActivity).apply {
            setTitle("Yeah!")
            setMessage(message)
            setPositiveButton("Lanjut") { _, _ ->
                val intent = Intent(context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
            create()
            show()
        }
    }


    private fun userlogin(){

        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()
        val requestBody = mapOf(

            "email" to email,
            "password" to password,
        )
        binding.btnLogin.setOnClickListener{
            viewModel.login(requestBody).observe(this){
                if (it!=null){
                    when(it){
                        is ResultState.loading -> {
                            showLoading(true)
                        }
                        is ResultState.success -> {
                            val message = it.data.message
                            setupAction(message)

                            val token = it.data?.Token

                            if (token != null) {
                                viewModel.saveSession(UserModel( email, token))
                            }

                            showLoading(false)
                        }
                        is ResultState.error -> {
                            val error = it.error
                            showToast(error)
                            showLoading(false)
                        }
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}