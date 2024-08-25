package com.example.jobsearch_test.di

import com.example.jobsearch_test.api.EntranceFeatureApi
import com.example.jobsearch_test.api.FavoritesFeatureApi
import com.example.jobsearch_test.api.HomeFeatureApi
import com.example.jobsearch_test.api.VacancyFeatureApi
import com.example.jobsearch_test.entrance.api.EntranceFeatureImpl
import com.example.jobsearch_test.favorites.api.FavoritesFeatureImpl
import com.example.jobsearch_test.home.api.HomeFeatureImpl
import com.example.jobsearch_test.vacancy.api.VacancyFeatureImpl
import dagger.Binds
import dagger.Module

@Module
interface FeaturesModule {

    @Binds
    fun bindEntranceFeature(featureApi: EntranceFeatureImpl): EntranceFeatureApi

    @Binds
    fun bindHomeFeature(featureApi: HomeFeatureImpl): HomeFeatureApi

    @Binds
    fun bindVacancyFeature(featureApi: VacancyFeatureImpl): VacancyFeatureApi

    @Binds
    fun bindFavoritesFeature(featureApi: FavoritesFeatureImpl): FavoritesFeatureApi

}