package com.example.jobsearch_test.data.retrofit

import android.util.Log
import com.example.jobsearch_test.data_api.network.JobDataNetwork
import com.example.jobsearch_test.models.JobData
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://drive.usercontent.google.com/"

class JobDataImpl: JobDataNetwork {
    override suspend fun getStats(): JobData {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        val jobDataApi = retrofit.create(JobDataApi::class.java)
        val jobData = jobDataApi.getJobData("1z4TbeDkbfXkvgpoJprXbN85uCcD7f00r", "download")

        return jobData
    }
}