package com.example.jobsearch_test.main.di

import com.example.jobsearch_test.api.MainFeatureApi
import com.example.jobsearch_test.core.navigation.Router

interface MainFeatureDeps {
    val mainFeatureApi: MainFeatureApi
    val router: Router
}