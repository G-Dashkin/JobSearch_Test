package com.example.jobsearch_test.data_api.storage

interface VacanciesStorage {
    suspend fun selectFavorites(favoriteId: String)
    suspend fun getFavorites(): List<String>
}