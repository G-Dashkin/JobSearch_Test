package com.example.jobsearch_test.entrance.di

import com.example.jobsearch_test.api.EntranceFeatureApi
import com.example.jobsearch_test.api.MainFeatureApi
import com.example.jobsearch_test.core.navigation.Router

interface EntranceFeatureDeps {
    val entranceFeatureApi: EntranceFeatureApi
    val mainFeatureApi: MainFeatureApi
    val router: Router
}