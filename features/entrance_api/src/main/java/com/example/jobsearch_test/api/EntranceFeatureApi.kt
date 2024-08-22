package com.example.jobsearch_test.api

import androidx.fragment.app.Fragment

interface EntranceFeatureApi {
    fun openEntrance1(): Fragment
    fun openEntrance2(email: String): Fragment
}