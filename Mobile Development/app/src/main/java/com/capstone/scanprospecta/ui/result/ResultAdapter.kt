package com.capstone.scanprospecta.ui.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.scanprospecta.data.response.DetailItem
import com.capstone.scanprospecta.databinding.ItemResultBinding

class ResultAdapter : ListAdapter<DetailItem, ResultAdapter.MyViewHolder>(DIFF_CALLBACK){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }


        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val result = getItem(position)
        holder.bind(result)
    }

//    override fun getItemCount() = data.size

    class MyViewHolder(val binding: ItemResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(result: DetailItem){
            binding.tvJob.text = result.toString()
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DetailItem>() {
            override fun areItemsTheSame(oldItem: DetailItem, newItem: DetailItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DetailItem, newItem: DetailItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}