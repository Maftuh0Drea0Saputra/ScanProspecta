package com.capstone.scanprospecta.ui.resume

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.capstone.scanprospecta.databinding.FragmentResumeBinding

class ResumeFragment : Fragment() {

    private lateinit var binding: FragmentResumeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val resumeViewModel =
            ViewModelProvider(this).get(ResumeViewModel::class.java)

        binding = FragmentResumeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}