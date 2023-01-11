package com.tugasakhir.learningsmk.ui.module

import com.tugasakhir.learningsmk.network.ApiService
import kotlinx.coroutines.*

class DetailPresenter (
        private val view: DetailView,
        private val api: ApiService
) {

    init {
        view.setupListener()
    }

    fun fetchDetail(id: Int) {
        view.detailLoading(true)
        GlobalScope.launch {
            val response = api.moduleById( id )
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    view.detailResponse( response.body()!! )
                    view.detailLoading(false)
                }
            } else {
                view.detailError("Terjadi kesalahan")
            }
        }
    }

}

interface DetailView {
    fun setupListener()
    fun detailLoading(loading: Boolean)
    fun detailResponse(response: DetailResponse)
    fun detailError(msg: String)
}