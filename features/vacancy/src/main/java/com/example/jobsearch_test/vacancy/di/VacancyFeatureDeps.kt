package com.example.jobsearch_test.vacancy.di

import android.app.Application
import com.example.jobsearch_test.api.VacancyFeatureApi
import com.example.jobsearch_test.core.navigation.Router
import com.example.jobsearch_test.data_api.repository.VacanciesRepository

interface VacancyFeatureDeps {
    val application: Application
    val vacanciesRepository: VacanciesRepository
    val vacancyFeatureApi: VacancyFeatureApi
    val router: Router
}