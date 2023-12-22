package com.capstone.scanprospecta.ui.consultation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.scanprospecta.R
import com.capstone.scanprospecta.data.response.Message
import com.capstone.scanprospecta.databinding.FragmentConsultationBinding
import com.capstone.scanprospecta.databinding.FragmentHomeBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.Date

class ConsultationFragment : Fragment() {

    private lateinit var binding: FragmentConsultationBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var db: FirebaseDatabase
    private lateinit var adapter: ConsultationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentConsultationBinding.inflate(inflater, container, false)

        auth = Firebase.auth
        val firebaseUser = auth.currentUser

        db = Firebase.database
        val messageRef = db.reference.child(MESSAGES_CHILD)

        binding.btnSend.setOnClickListener {
            val message = Message(
                binding.messageEditText.text.toString(),
                firebaseUser?.displayName.toString(),
                Date().time
            )
            messageRef.push().setValue(message) { error, _ ->
                if (error != null) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.send_error) + error.message,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.send_success),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            binding.messageEditText.setText("")
        }

        val manager = LinearLayoutManager(requireContext())
        manager.stackFromEnd = true
        binding.messageRecyclerView.layoutManager = manager

        val options = FirebaseRecyclerOptions.Builder<Message>()
            .setQuery(messageRef, Message::class.java)
            .build()
        adapter = ConsultationAdapter(options, firebaseUser?.displayName)
        binding.messageRecyclerView.adapter = adapter

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        adapter.startListening()
    }
    override fun onPause() {
        adapter.stopListening()
        super.onPause()
    }

    companion object {
        const val MESSAGES_CHILD = "messages"
    }
}