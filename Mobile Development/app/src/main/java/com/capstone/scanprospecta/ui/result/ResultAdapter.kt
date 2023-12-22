package com.capstone.scanprospecta.ui.result

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.scanprospecta.databinding.ItemResultBinding

class ResultAdapter(
    private val context: Context,
    private val data: List<String>
): RecyclerView.Adapter<ResultAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val result = data
        holder.bind(result)
    }

    override fun getItemCount() = data.size

    class MyViewHolder(val binding: ItemResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result: List<String>){
            binding.tvJob.text = result.toString()
        }
    }
}