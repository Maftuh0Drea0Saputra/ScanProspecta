package com.capstone.scanprospecta.ui.result

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.capstone.scanprospecta.databinding.ActivityResultBinding
import com.capstone.scanprospecta.ui.camera.CameraActivity

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUriString = intent.getStringExtra(CameraActivity.EXTRA_IMAGE_URI)
        val imageUri = Uri.parse(imageUriString)

        binding.ivResult.setImageURI(imageUri)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility =
            if (isLoading) View.VISIBLE
            else View.GONE
    }
}