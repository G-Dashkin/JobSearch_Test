package com.example.jobsearch_test.favorites.di

import com.example.jobsearch_test.favorites.presentation.FavoritesFragment
import dagger.Component

@Component(dependencies = [FavoritesFeatureDeps::class])
@FavoritesScope
interface FavoritesComponent {
    fun inject(newFragment: FavoritesFragment)

    @Component.Builder
    interface Builder {
        fun addDeps(deps: FavoritesFeatureDeps): Builder
        fun build(): FavoritesComponent
    }
}