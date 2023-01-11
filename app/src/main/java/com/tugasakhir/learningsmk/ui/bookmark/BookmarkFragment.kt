package com.tugasakhir.learningsmk.ui.bookmark

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.tugasakhir.learningsmk.databinding.FragmentBookmarkBinding
import com.tugasakhir.learningsmk.persistence.Course
import com.tugasakhir.learningsmk.persistence.DatabaseClient
import com.tugasakhir.learningsmk.preferences.PrefManager
import com.tugasakhir.learningsmk.ui.module.ModuleActivity

class BookmarkFragment : Fragment(), BookmarkView {

    private lateinit var binding: FragmentBookmarkBinding
    private lateinit var presenter: BookmarkPresenter
    private lateinit var adapter: BookmarkAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        presenter = BookmarkPresenter(
                this,
            DatabaseClient.getService(requireContext()).courseDao(),
            PrefManager(requireActivity()),
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener() // initialize here cause there is observe
    }

    override fun setupListener() {
        adapter = BookmarkAdapter(arrayListOf(), object : BookmarkAdapter.AdapterListener {
            override fun onDetail(course: Course) {
                startActivity(
                        Intent(requireContext(), ModuleActivity::class.java)
                                .putExtra("id", course.id)
                )
            }
            override fun onRemove(course: Course) {
                presenter.remove( course )
            }
        })
        binding.listCourse.adapter = adapter
        presenter.listBookmark.observe(viewLifecycleOwner, {
            adapter.addList(it)
            if (it.isEmpty()) Toast.makeText(requireContext(), "Tidak ada kelas yang kamu ikuti", Toast.LENGTH_SHORT).show()
        })
    }
}