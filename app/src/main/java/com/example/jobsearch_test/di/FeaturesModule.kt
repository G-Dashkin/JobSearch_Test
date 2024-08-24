package com.example.jobsearch_test.di

import com.example.jobsearch_test.api.EntranceFeatureApi
import com.example.jobsearch_test.api.HomeFeatureApi
import com.example.jobsearch_test.entrance.api.EntranceFeatureImpl
import com.example.jobsearch_test.home.api.HomeFeatureImpl
import dagger.Binds
import dagger.Module

@Module
interface FeaturesModule {

    @Binds
    fun bindEntranceFeature(featureApi: EntranceFeatureImpl): EntranceFeatureApi

    @Binds
    fun bindHomeFeature(mainApi: HomeFeatureImpl): HomeFeatureApi

}