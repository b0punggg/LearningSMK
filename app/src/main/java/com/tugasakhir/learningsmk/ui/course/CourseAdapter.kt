package com.tugasakhir.learningsmk.ui.course

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tugasakhir.learningsmk.databinding.AdapterCourseBinding
import com.tugasakhir.learningsmk.network.ApiClient
import com.tugasakhir.learningsmk.util.loadImage

class CourseAdapter (
        var courses: ArrayList<CourseData>,
        var listener: AdapterListener
): RecyclerView.Adapter<CourseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AdapterCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = courses.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val course = courses[position]
        holder.binding.textTitle.text = course.title
        holder.binding.textMentor.text = course.guru //guru
        loadImage(holder.binding.imageThumbnail, course.thumbnail)
        holder.itemView.setOnClickListener {
            listener.onClick( course )
        }
    }

    class ViewHolder(val binding: AdapterCourseBinding): RecyclerView.ViewHolder(binding.root)

    fun addList(list: List<CourseData>) {
        courses.clear()
        courses.addAll(list)
        notifyDataSetChanged()
    }

    interface AdapterListener {
        fun onClick(course: CourseData)
    }

}