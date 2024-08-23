package com.example.jobsearch_test.main.di

import android.app.Application
import android.content.Context
import com.example.jobsearch_test.api.MainFeatureApi
import com.example.jobsearch_test.core.navigation.Router
import com.example.jobsearch_test.data_api.repository.VacanciesRepository

interface MainFeatureDeps {
    val application: Application
    val vacanciesRepository: VacanciesRepository
    val mainFeatureApi: MainFeatureApi
    val router: Router
}