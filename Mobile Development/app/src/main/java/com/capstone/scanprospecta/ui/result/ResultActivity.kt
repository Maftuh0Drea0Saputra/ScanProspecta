package com.capstone.scanprospecta.ui.result

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.scanprospecta.data.ResultState
import com.capstone.scanprospecta.data.response.DetailItem
import com.capstone.scanprospecta.databinding.ActivityResultBinding
import com.capstone.scanprospecta.ui.camera.CameraActivity
import com.capstone.scanprospecta.utils.AppExecutors
import com.capstone.scanprospecta.utils.reduceFileImage
import com.capstone.scanprospecta.utils.rotateBitmap
import com.capstone.scanprospecta.utils.uriToFile
import java.io.File
import java.io.FileOutputStream

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private val viewModel: ResultViewModel by viewModels {
        ResultViewModel.ResultViewModelFactory.getInstance(this)
    }

    val imageUriString = intent?.getStringExtra(CameraActivity.EXTRA_IMAGE_URI)
    val imageUri = imageUriString?.let { Uri.parse(it) }

    private val appExecutor: AppExecutors by lazy {
        AppExecutors()
    }

    private var file: File? = null
    private var isBack: Boolean = true
    private var reducingDone: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindResult()
        setupView()
    }

    private fun setupView() {
        binding.rvJob.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        viewModel.jobRecomResult.observe(this, Observer { it ->
            when (it) {
                is ResultState.loading -> {
                    showLoading(true)
                }
                is ResultState.success -> {
                    showLoading(false)
                    val jobRecom = it.data.detail
                    Log.d("ResultActivity", "JobRecom: $jobRecom")
                    setJobRecom(jobRecom)
                }
                is ResultState.error -> {
                    val error = it.error
                    showToast(error)
                    showLoading(false)
                }
                else -> {}
            }
        })
    }

    private fun bindResult() {
        file = intent.getSerializableExtra(PHOTO_RESULT_EXTRA) as File
        isBack = intent.getBooleanExtra(IS_CAMERA_BACK_EXTRA, true)

        val result = rotateBitmap(BitmapFactory.decodeFile((file as File).path), isBack)
        result.compress(Bitmap.CompressFormat.JPEG, 100, FileOutputStream(file))

        appExecutor.diskIO.execute {
            file = reduceFileImage(file as File)
            reducingDone = true
        }

        binding.ivResult.setImageBitmap(result)
    }

    private fun setJobRecom(data: List<DetailItem?>?) {
        if (imageUri != null) {
            val imageFile = uriToFile(imageUri, this)

            val adapter = ResultAdapter()
            binding.rvJob.adapter = adapter

            val result = viewModel.postJobRecom(imageFile, data)
            return result
        }
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val PHOTO_RESULT_EXTRA = "photo_result_extra"
        const val IS_CAMERA_BACK_EXTRA = "is_camera_back_extra"
    }
}