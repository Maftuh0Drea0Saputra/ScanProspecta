package com.capstone.scanprospecta.ui.consultation

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.scanprospecta.R
import com.capstone.scanprospecta.data.response.Message
import com.capstone.scanprospecta.databinding.ItemMessageBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class ConsultationAdapter(
    options: FirebaseRecyclerOptions<Message>,
    private val currentName: String?
) : FirebaseRecyclerAdapter<Message, ConsultationAdapter.ConsultationViewHolder>(options) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConsultationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_message, parent, false)
        val binding = ItemMessageBinding.bind(view)
        return ConsultationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConsultationViewHolder, position: Int, model: Message) {
        holder.bind(model)
    }

    inner class ConsultationViewHolder(private val binding: ItemMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Message) {
            binding.tvMessage.text = item.text
            setTextColor(item.name, binding.tvMessage)
            binding.tvMessenger.text = item.name
            if (item.timestamp != null) {
                binding.tvTimestamp.text = DateUtils.getRelativeTimeSpanString(item.timestamp)
            }
        }

        private fun setTextColor(userName: String?, textView: TextView) {
            if (currentName == userName && userName != null) {
                textView.setBackgroundResource(R.drawable.rounded_message_blue)
            } else {
                textView.setBackgroundResource(R.drawable.rounded_message_gray)
            }
        }
    }
}