package com.tugasakhir.learningsmk.ui.course

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tugasakhir.learningsmk.R
import com.tugasakhir.learningsmk.databinding.AdapterCategoryBinding

class CategoryAdapter (
        var categories: ArrayList<CategoryData>,
        var listener: AdapterListener?
): RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private val texts = arrayListOf<TextView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AdapterCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        texts.add( holder.binding.textCategory )
        holder.binding.textCategory.text = category.name
        holder.itemView.setOnClickListener {
            setColor( holder.binding.textCategory )
            listener?.onClick( category )
        }
    }

    class ViewHolder(val binding: AdapterCategoryBinding): RecyclerView.ViewHolder(binding.root)

    fun addList(list: List<CategoryData>) {
        categories.clear()
        categories.add( CategoryData(0, "Semua") )
        categories.addAll(list)
        notifyDataSetChanged()
    }

    fun setColor (textView: TextView) {
        texts.forEach {
            it.setBackgroundResource(R.color.white)
            if (it.text.toString().contains( textView.text.toString() )) it.setBackgroundResource(R.color.blue_200)
        }
    }

    interface AdapterListener {
        fun onClick(category: CategoryData)
    }

}