package com.example.jobsearch_test.favorites.di

import android.app.Application
import com.example.jobsearch_test.api.FavoritesFeatureApi
import com.example.jobsearch_test.core.navigation.Router
import com.example.jobsearch_test.data_api.repository.VacanciesRepository

interface FavoritesFeatureDeps {
    val application: Application
    val vacanciesRepository: VacanciesRepository
    val favoritesFeatureApi: FavoritesFeatureApi
    val router: Router
}