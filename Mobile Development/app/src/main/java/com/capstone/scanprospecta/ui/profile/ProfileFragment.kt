package com.capstone.scanprospecta.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.capstone.scanprospecta.databinding.FragmentProfileBinding
import com.capstone.scanprospecta.ui.ViewModelFactory
import com.capstone.scanprospecta.ui.login.LoginActivity

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            binding.apply {
                tvName.text = user.name
                tvEmail.text = user.email
            }
        }
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
        }
    }
}