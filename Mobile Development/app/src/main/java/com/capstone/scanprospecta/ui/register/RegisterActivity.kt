package com.capstone.scanprospecta.ui.register


import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.capstone.scanprospecta.data.ResultState
import com.capstone.scanprospecta.databinding.ActivityRegisterBinding
import com.capstone.scanprospecta.ui.ViewModelFactory
import com.capstone.scanprospecta.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        usersignup()
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
        AlertDialog.Builder(this@RegisterActivity).apply {
            setTitle("Yeah!")
            setMessage(message)
            setPositiveButton("Lanjut") { _, _ ->
                val intent = Intent(context, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
            create()
            show()
        }
    }

    private fun usersignup(){
        binding.btnRegister.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()

            val requestBody = mapOf(
                "name" to name,
                "email" to email,
                "password" to password,
                "conf_password" to password
            )

            viewModel.register(requestBody).observe(this) { it ->
                if (it!= null) {
                    when(it) {
                        is ResultState.loading -> {
                            showLoading(true)
                        }
                        is ResultState.success -> {
                            val message = it.data.message
                            setupAction(message)
                            showLoading(false)
                        }
                        is ResultState.error -> {
                            val error = it.error
                            showToast(error)
                            showLoading(false)
                        }

                        else -> {}
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