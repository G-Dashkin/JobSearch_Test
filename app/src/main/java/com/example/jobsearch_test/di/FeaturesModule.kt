package com.example.jobsearch_test.di

import com.example.jobsearch_test.api.EntranceFeatureApi
import com.example.jobsearch_test.api.MainFeatureApi
import com.example.jobsearch_test.entrance.api.EntranceFeatureImpl
import com.example.jobsearch_test.main.api.MainFeatureImpl
import dagger.Binds
import dagger.Module

@Module
interface FeaturesModule {

    @Binds
    fun bindEntranceFeature(featureApi: EntranceFeatureImpl): EntranceFeatureApi

    @Binds
    fun bindMainFeature(mainApi: MainFeatureImpl): MainFeatureApi

}