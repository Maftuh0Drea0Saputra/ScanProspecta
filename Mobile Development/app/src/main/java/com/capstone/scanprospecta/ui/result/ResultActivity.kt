package com.capstone.scanprospecta.ui.result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.scanprospecta.R
import com.capstone.scanprospecta.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
    }
}