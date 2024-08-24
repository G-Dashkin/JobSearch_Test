package com.example.jobsearch_test.home.di

import com.example.jobsearch_test.home.presentation.HomeFragment
import dagger.Component

@Component(dependencies = [HomeFeatureDeps::class])
@HomeScope
interface HomeComponent {
    fun inject(newFragment: HomeFragment)

    @Component.Builder
    interface Builder {
        fun addDeps(deps: HomeFeatureDeps): Builder
        fun build(): HomeComponent
    }
}