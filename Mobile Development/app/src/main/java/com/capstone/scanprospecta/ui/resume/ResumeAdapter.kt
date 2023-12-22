package com.capstone.scanprospecta.ui.resume

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.scanprospecta.data.response.DataItem
import com.capstone.scanprospecta.databinding.ItemResultBinding
import com.capstone.scanprospecta.databinding.ItemResumeBinding
import com.capstone.scanprospecta.ui.detail.DetailResumeActivity

class ResumeAdapter(
    private val context: Context,
    private val list: List<DataItem>
): RecyclerView.Adapter<ResumeAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemResumeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val resume = list[position]
        holder.bind(resume)
        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailResumeActivity::class.java)
            intentDetail.putExtra("detail", resume.resumeId)
            holder.itemView.context.startActivity(intentDetail)
        }

    }

    override fun getItemCount() = list.size

    class MyViewHolder(val binding: ItemResumeBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(resume: DataItem) {
            Glide.with(itemView.context)
                .load(resume.image)
                .into(binding.ivResume)
        }
    }

}