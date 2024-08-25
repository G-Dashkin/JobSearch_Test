package com.example.jobsearch_test.api

import androidx.fragment.app.Fragment

interface VacancyFeatureApi {
    fun open(vacancyId: String): Fragment
}