package com.example.jobsearch_test.data_api.storage

import com.example.jobsearch_test.models.Offer
import com.example.jobsearch_test.models.Vacancy

interface VacanciesStorage {
    suspend fun selectFavorites(favoriteId: String)
    suspend fun getFavorites(): List<String>
}