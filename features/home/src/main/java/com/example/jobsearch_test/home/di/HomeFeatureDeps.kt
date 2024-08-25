package com.example.jobsearch_test.home.di

import android.app.Application
import com.example.jobsearch_test.api.HomeFeatureApi
import com.example.jobsearch_test.api.VacancyFeatureApi
import com.example.jobsearch_test.core.navigation.Router
import com.example.jobsearch_test.data_api.repository.VacanciesRepository

interface HomeFeatureDeps {
    val application: Application
    val vacanciesRepository: VacanciesRepository
    val homeFeatureApi: HomeFeatureApi
    val vacancyFeatureApi: VacancyFeatureApi
    val router: Router
}