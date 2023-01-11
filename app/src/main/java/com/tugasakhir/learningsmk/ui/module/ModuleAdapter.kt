package com.tugasakhir.learningsmk.ui.module

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tugasakhir.learningsmk.databinding.AdapterModuleBinding

class ModuleAdapter (
    var details: ArrayList<DetailData>,
    var listener: AdapterListener
): RecyclerView.Adapter<ModuleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AdapterModuleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = details.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val detail = details[position]
        holder.binding.textTitle.text = detail.title
        if (detail.file_type != null && detail.file_type == "pdf")
            holder.binding.imageDownload.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    listener.onClick(detail)
                }
            }
        else
            holder.binding.fabPlay.apply {
                visibility = View.VISIBLE
                setOnClickListener {
                    listener.onClick(detail)
                }
            }
    }

    class ViewHolder(val binding: AdapterModuleBinding): RecyclerView.ViewHolder(binding.root)

    fun addList(list: List<DetailData>) {
        details.clear()
        details.addAll(list)
        notifyDataSetChanged()
    }

    interface AdapterListener {
        fun onClick(detail: DetailData)
    }

}