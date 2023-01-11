package com.tugasakhir.learningsmk.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.tugasakhir.learningsmk.databinding.FragmentHomeBinding
import com.tugasakhir.learningsmk.preferences.PrefManager
import com.tugasakhir.learningsmk.network.ApiClient
import com.tugasakhir.learningsmk.ui.course.*
import com.tugasakhir.learningsmk.ui.login.LoginData
import com.tugasakhir.learningsmk.ui.module.ModuleActivity

class HomeFragment : Fragment(), HomeView {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var presenter: HomePresenter
    private lateinit var popularAdapter: PopularAdapter
    private lateinit var latestAdapter: CourseAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        presenter = HomePresenter(
                this,
                PrefManager(requireContext()),
                ApiClient.getService()
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.editSearch.setOnClickListener {
            startActivity(Intent(requireContext(), CourseActivity::class.java))
        }
    }

    override fun setupListener() {
        popularAdapter = PopularAdapter(arrayListOf(), object: PopularAdapter.AdapterListener {
            override fun onClick(course: CourseData) {
                startActivity(
                    Intent(requireContext(), ModuleActivity::class.java)
                        .putExtra("id", course.id)
                )
            }
        })
        binding.listPopular.adapter = popularAdapter
        latestAdapter = CourseAdapter(arrayListOf(), object: CourseAdapter.AdapterListener {
            override fun onClick(course: CourseData) {
                startActivity(
                        Intent(requireContext(), ModuleActivity::class.java)
                                .putExtra("id", course.id)
                )
            }
        })
        binding.listLatest.adapter = latestAdapter
        binding.swipe.setOnRefreshListener {
            presenter.fetchHome()
        }
    }

    override fun user(user: LoginData) {
        binding.textTitle.text = "Halo, ${user.name}"
    }

    override fun homeLoading(loading: Boolean) {
        binding.swipe.isRefreshing = loading
    }

    override fun homeResponse(response: HomeResponse) {
        popularAdapter.addList( response.data.popular )
        latestAdapter.addList( response.data.latest )
    }

    override fun homeError(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }
}