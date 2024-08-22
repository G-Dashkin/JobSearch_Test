package com.example.jobsearch_test.main.api

import androidx.fragment.app.Fragment
import com.example.jobsearch_test.api.MainFeatureApi
import com.example.jobsearch_test.main.presentation.MainFragment
import javax.inject.Inject

class MainFeatureImpl @Inject constructor() : MainFeatureApi {
    override fun open(): Fragment = MainFragment.getInstance()
}