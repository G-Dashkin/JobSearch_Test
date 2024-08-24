package com.example.jobsearch_test.home.api

import androidx.fragment.app.Fragment
import com.example.jobsearch_test.api.HomeFeatureApi
import com.example.jobsearch_test.home.presentation.HomeFragment
import javax.inject.Inject

class HomeFeatureImpl @Inject constructor() : HomeFeatureApi {
    override fun open(): Fragment = HomeFragment.getInstance()
}