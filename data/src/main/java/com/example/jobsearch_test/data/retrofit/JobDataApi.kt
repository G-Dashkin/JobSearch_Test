package com.example.jobsearch_test.data.retrofit

import com.example.jobsearch_test.models.JobData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface JobDataApi {

    @GET("u/0/uc")
    suspend fun getJobData(
        @Query("id") id: String,
        @Query("export") export: String
    ): JobData
}