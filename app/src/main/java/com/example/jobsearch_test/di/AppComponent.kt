package com.example.jobsearch_test.di

import com.example.jobsearch_test.entrance.di.EntranceFeatureDeps
import com.example.jobsearch_test.home.di.HomeFeatureDeps
import com.example.jobsearch_test.presentation.navigation.NavigatorFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, FeaturesModule::class])
interface AppComponent : EntranceFeatureDeps, HomeFeatureDeps {
    fun inject(fragment: NavigatorFragment)
}