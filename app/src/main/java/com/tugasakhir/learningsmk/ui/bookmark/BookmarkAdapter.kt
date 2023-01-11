package com.tugasakhir.learningsmk.ui.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tugasakhir.learningsmk.databinding.AdapterBookmarkBinding
import com.tugasakhir.learningsmk.persistence.Course
import com.tugasakhir.learningsmk.util.loadImage

class BookmarkAdapter (
        var courses: ArrayList<Course>,
        var listener: AdapterListener
): RecyclerView.Adapter<BookmarkAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                AdapterBookmarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = courses.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val course = courses[position]
        holder.binding.textTitle.text = course.title
        holder.binding.textMentor.text = course.guru //guru
        loadImage(holder.binding.imageThumbnail, course.thumbnail)
        holder.itemView.setOnClickListener {
            listener.onDetail( course )
        }
        holder.binding.imageDelete.setOnClickListener {
            listener.onRemove( course )
        }
    }

    class ViewHolder(val binding: AdapterBookmarkBinding): RecyclerView.ViewHolder(binding.root)

    fun addList(list: List<Course>) {
        courses.clear()
        courses.addAll(list)
        notifyDataSetChanged()
    }

    interface AdapterListener {
        fun onDetail(course: Course)
        fun onRemove(course: Course)
    }

}