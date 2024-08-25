package com.example.jobsearch_test.favorites.api

import androidx.fragment.app.Fragment
import com.example.jobsearch_test.api.FavoritesFeatureApi
import com.example.jobsearch_test.favorites.presentation.FavoritesFragment
import javax.inject.Inject

class FavoritesFeatureImpl @Inject constructor() : FavoritesFeatureApi {
    override fun open(): Fragment = FavoritesFragment.getInstance()
}