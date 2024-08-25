package com.example.jobsearch_test.data.repository

import android.content.Context
import android.util.Log
import com.example.jobsearch_test.data.mappers.toDomain
import com.example.jobsearch_test.data_api.repository.VacanciesRepository
import com.example.jobsearch_test.data_api.storage.VacanciesStorage
import com.example.jobsearch_test.models.JobData
import com.example.jobsearch_test.models.Offer
import com.example.jobsearch_test.models.Vacancy
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class VacanciesRepositoryImpl @Inject constructor(
    private val context: Context,
    private val vacanciesStorage: VacanciesStorage
): VacanciesRepository {

    override suspend fun getAllVacancies(): List<Vacancy> {
        val favoritesVacancies = vacanciesStorage.getFavorites()
        return getData().vacancies.map { vacancy ->
            if (favoritesVacancies.contains(vacancy.id)) vacancy.toDomain(true)
            else vacancy.toDomain()
        }
    }

    override suspend fun selectFavoriteVacancies(vacancyId: String) {
        vacanciesStorage.selectFavorites(vacancyId)
    }

    override suspend fun getFavoritesVacancies(): List<Vacancy> {
        val favoritesVacancies = vacanciesStorage.getFavorites()
        return getData().vacancies.filter { vacancy ->
            favoritesVacancies.contains(vacancy.id)
        }.map { selectedVacancy ->
            selectedVacancy.toDomain(true)
        }
    }

    override suspend fun getVacancy(vacancyId: String): Vacancy {
        val favoritesVacancies = vacanciesStorage.getFavorites()
        val vacancy = getData().vacancies.filter { it.id == vacancyId }.first()
        return if (favoritesVacancies.contains(vacancy.id)) vacancy.toDomain(true)
        else vacancy
    }

    override suspend fun getAllOffers(): List<Offer> = getData().offers

    private fun parseJSON(jsonString: String):JobData {
        val gson = Gson()
        return gson.fromJson(jsonString, JobData::class.java)
    }
    private fun getData(): JobData {
        val jsonString = context.assets.open("mock_data.json").bufferedReader().use { it.readText() }
        val data = parseJSON(jsonString)
        return JobData(offers = data.offers, vacancies = data.vacancies)
    }
}

