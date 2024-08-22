package com.example.jobsearch_test.entrance.di

import com.example.jobsearch_test.entrance.presentatation.Entrance1Fragment
import com.example.jobsearch_test.entrance.presentatation.Entrance2Fragment
import dagger.Component

@Component(dependencies = [EntranceFeatureDeps::class])
@EntranceScope
interface EntranceComponent {
    fun inject(newFragment: Entrance1Fragment)
    fun inject(newFragment: Entrance2Fragment)

    @Component.Builder
    interface Builder {
        fun addDeps(deps: EntranceFeatureDeps): Builder
        fun build(): EntranceComponent
    }
}