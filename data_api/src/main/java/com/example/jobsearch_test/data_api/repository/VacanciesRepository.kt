package com.example.jobsearch_test.data_api.repository

import com.example.jobsearch_test.models.Offer
import com.example.jobsearch_test.models.Vacancy

interface VacanciesRepository {
    suspend fun getVacancies(): List<Vacancy>
    suspend fun getVacancy(vacancyId: String): Vacancy
    suspend fun selectFavoriteVacancies(vacancyId: String)
    suspend fun getFavoritesVacancies(): List<Vacancy>
    suspend fun getOffers(): List<Offer>
}