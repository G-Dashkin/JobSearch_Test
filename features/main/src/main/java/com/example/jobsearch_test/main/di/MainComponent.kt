package com.example.jobsearch_test.main.di

import com.example.jobsearch_test.main.presentation.MainFragment
import dagger.Component


@Component(dependencies = [MainFeatureDeps::class])
@MainScope
interface MainComponent {
    fun inject(newFragment: MainFragment)

    @Component.Builder
    interface Builder {
        fun addDeps(deps: MainFeatureDeps): Builder
        fun build(): MainComponent
    }
}