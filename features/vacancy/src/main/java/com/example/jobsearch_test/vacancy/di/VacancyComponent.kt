package com.example.jobsearch_test.vacancy.di

import com.example.jobsearch_test.vacancy.presentation.VacancyFragment
import dagger.Component

@Component(dependencies = [VacancyFeatureDeps::class])
@VacancyScope
interface VacancyComponent {
    fun inject(newFragment: VacancyFragment)

    @Component.Builder
    interface Builder {
        fun addDeps(deps: VacancyFeatureDeps): Builder
        fun build(): VacancyComponent
    }
}