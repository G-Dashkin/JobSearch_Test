package com.example.jobsearch_test.data.repository

import android.content.Context
import android.util.Log
import com.example.jobsearch_test.data_api.repository.VacanciesRepository
import com.example.jobsearch_test.models.JobData
import com.example.jobsearch_test.models.Vacancy
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class VacanciesRepositoryImpl @Inject constructor(
    private val context: Context,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): VacanciesRepository {

    override suspend fun getAllVacancies(): List<Vacancy> {
        val jsonString = context.assets.open("mock_data.json").bufferedReader().use { it.readText() }
        val data = parseJSON(jsonString)
        return data.vacancies
    }

    private fun parseJSON(jsonString: String):JobData {
        val gson = Gson()
        return gson.fromJson(jsonString, JobData::class.java)
    }
}

