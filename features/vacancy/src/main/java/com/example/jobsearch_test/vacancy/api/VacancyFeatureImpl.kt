package com.example.jobsearch_test.vacancy.api

import androidx.fragment.app.Fragment
import com.example.jobsearch_test.api.VacancyFeatureApi
import com.example.jobsearch_test.vacancy.presentation.VacancyFragment
import javax.inject.Inject

class VacancyFeatureImpl @Inject constructor() : VacancyFeatureApi {
        override fun open(vacancyId: String): Fragment = VacancyFragment.getInstance(vacancyId)
}