package com.tugasakhir.learningsmk.network

import com.tugasakhir.learningsmk.ui.course.CategoryResponse
import com.tugasakhir.learningsmk.ui.course.CourseResponse
import com.tugasakhir.learningsmk.ui.home.HomeResponse
import com.tugasakhir.learningsmk.ui.login.LoginResponse
import com.tugasakhir.learningsmk.ui.module.DetailResponse
import com.tugasakhir.learningsmk.ui.module.ModuleResponse
import com.tugasakhir.learningsmk.ui.profile.AvatarResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("api/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Response<LoginResponse>

    @GET("api/course")
    suspend fun course(
            @Query("keyword") keyword: String,
    ) : Response<CourseResponse>

    @GET("api/category")
    suspend fun category() : Response<CategoryResponse>

    @POST("api/course-by-category")
    suspend fun courseByCategory(
            @Query("category_id") category_id: Int
    ) : Response<CourseResponse>

    @POST("api/course-by-id")
    suspend fun courseById(
        @Query("id") id: Int
    ) : Response<ModuleResponse>

    @POST("api/module-by-id")
    suspend fun moduleById(
        @Query("id") id: Int
    ) : Response<DetailResponse>

    @GET("api/home")
    suspend fun home() : Response<HomeResponse>

    @Multipart
    @POST("api/avatar")
    suspend fun avatar(
            @Part avatar: MultipartBody.Part,
            @Query("id") id: Int
    ) : Response<AvatarResponse>
}