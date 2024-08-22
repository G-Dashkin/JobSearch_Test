package com.example.jobsearch_test.entrance.api

import androidx.fragment.app.Fragment
import com.example.jobsearch_test.api.EntranceFeatureApi
import com.example.jobsearch_test.entrance.presentatation.Entrance1Fragment
import com.example.jobsearch_test.entrance.presentatation.Entrance2Fragment
import javax.inject.Inject

class EntranceFeatureImpl @Inject constructor() : EntranceFeatureApi {
    override fun openEntrance1(): Fragment = Entrance1Fragment.getInstance()
    override fun openEntrance2(email: String): Fragment = Entrance2Fragment.getInstance(email)
}