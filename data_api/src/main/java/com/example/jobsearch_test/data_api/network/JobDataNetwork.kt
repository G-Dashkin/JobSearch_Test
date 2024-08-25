package com.example.jobsearch_test.data_api.network

import com.example.jobsearch_test.models.JobData

interface JobDataNetwork {
    suspend fun getStats(): JobData
}