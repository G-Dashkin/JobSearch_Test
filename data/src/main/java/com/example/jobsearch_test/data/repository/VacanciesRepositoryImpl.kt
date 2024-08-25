package com.example.jobsearch_test.data.repository

import android.content.Context
import android.util.Log
import com.example.jobsearch_test.data_api.repository.VacanciesRepository
import com.example.jobsearch_test.models.JobData
import com.example.jobsearch_test.models.Offer
import com.example.jobsearch_test.models.Vacancy
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class VacanciesRepositoryImpl @Inject constructor(
    private val context: Context
): VacanciesRepository {

    override suspend fun getAllVacancies(): List<Vacancy> = getData().vacancies

    override suspend fun getAllOffers(): List<Offer> = getData().offers

    override suspend fun getVacancy(vacancyId: String): Vacancy = getData().vacancies.filter {
        it.id == vacancyId
    }.first()

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

