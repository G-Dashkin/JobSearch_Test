package com.example.jobsearch_test.data_api.repository

import com.example.jobsearch_test.models.Vacancy

interface VacanciesRepository {
    suspend fun getAllVacancies(): List<Vacancy>
}