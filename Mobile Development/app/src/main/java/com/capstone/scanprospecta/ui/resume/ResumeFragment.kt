package com.capstone.scanprospecta.ui.resume

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone.scanprospecta.data.ResultState
import com.capstone.scanprospecta.data.preference.UserPreference
import com.capstone.scanprospecta.data.preference.dataStore
import com.capstone.scanprospecta.databinding.FragmentResumeBinding
import com.capstone.scanprospecta.ui.login.LoginActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")
class ResumeFragment : Fragment() {

    private lateinit var binding: FragmentResumeBinding
    private val viewModel by viewModels<ResumeViewModel> {
        ResumeViewModel.ResumeViewModelFactory.getInstance(requireContext(), UserPreference.getInstance(requireContext().dataStore))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentResumeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        checkSessionValid()
    }

    private fun checkSessionValid() {
        viewModel.checkTokenAvailable().observe(this) {
            if (it == "null") {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                setupView("Bearer $it")
            }
        }
    }

    private fun setupView(token: String) {
        viewModel.getResumeExample(token).observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is ResultState.success -> {
                    showLoading(false)
                    val resumeResponse = result.data
                    binding.rvResume.apply {
                        adapter = ResumeAdapter(requireActivity(), resumeResponse)
                        layoutManager = GridLayoutManager(requireActivity(), 2)
                    }
                }
                is ResultState.error -> {
                    val error = result.error
                    showToast(error)
                    showLoading(false)
                }
                is ResultState.loading -> {
                    showLoading(true)
                }
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
    binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}