package com.tugasakhir.learningsmk.ui.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.dhaval2404.imagepicker.ImagePicker
import com.tugasakhir.learningsmk.databinding.FragmentProfileBinding
import com.tugasakhir.learningsmk.network.ApiClient
import com.tugasakhir.learningsmk.persistence.DatabaseClient
import com.tugasakhir.learningsmk.preferences.PrefManager
import com.tugasakhir.learningsmk.ui.login.LoginActivity
import com.tugasakhir.learningsmk.ui.login.LoginData
import com.tugasakhir.learningsmk.ui.login.LoginResponse
import com.tugasakhir.learningsmk.util.loadAvatar
import com.tugasakhir.learningsmk.util.loadUri
import java.io.File

class ProfileFragment : Fragment(), ProfileView {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var presenter: ProfilePresenter
    private lateinit var user: LoginData

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        presenter = ProfilePresenter(
                this,
                PrefManager(requireContext()),
                DatabaseClient.getService(requireContext()).courseDao(),
                ApiClient.getService()
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.count()
    }

    override fun setupListener() {
        binding.imageProfile.setOnClickListener {
            ImagePicker.with(this)
                    .galleryOnly()
                    .compress(1024)
                    .maxResultSize(1080, 1080)
                    .start()
        }
        binding.buttonLogout.setOnClickListener {
            presenter.logout()
        }
    }

    override fun user(user: LoginData) {
        this.user = user
        binding.textName.text = user.name
        binding.textNis.text = user.nis
        loadAvatar(binding.imageProfile, user.avatar)
    }

    override fun courseCount(count: Int) {
        binding.textCourses.text = count.toString()
    }

    override fun uploadLoading(loading: Boolean) {
        binding.progress.visibility = if (loading) View.VISIBLE else View.GONE
    }

    override fun uploadResponse(avatar: AvatarResponse) {
        Toast.makeText(requireContext(), avatar.msg, Toast.LENGTH_SHORT).show()
        presenter.reLogin(user.nis)
    }

    override fun uploadError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        binding.progress.visibility = View.GONE
    }

    override fun loginResponse(login: LoginResponse) {
        Log.d("user_login", "" + login)
        loadAvatar(binding.imageProfile, login.data!!.avatar)
        presenter.updateSession( login.data )
    }

    override fun logout() {
        startActivity(Intent(requireActivity(), LoginActivity::class.java))
        requireActivity().finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val fileUri = data?.data
            loadUri(binding.imageProfile, fileUri!!)
            val file: File = ImagePicker.getFile(data)!!
            presenter.uploadAvatar( file, user.id )
//            val filePath:String = ImagePicker.getFilePath(data)!!
        }
    }
}