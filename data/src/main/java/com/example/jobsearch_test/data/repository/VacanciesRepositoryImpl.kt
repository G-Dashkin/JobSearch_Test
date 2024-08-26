package com.example.jobsearch_test.data.repository

import com.example.jobsearch_test.data.mappers.toDomain
import com.example.jobsearch_test.data_api.network.JobDataNetwork
import com.example.jobsearch_test.data_api.repository.VacanciesRepository
import com.example.jobsearch_test.data_api.storage.VacanciesStorage
import com.example.jobsearch_test.models.JobData
import com.example.jobsearch_test.models.Offer
import com.example.jobsearch_test.models.Vacancy
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VacanciesRepositoryImpl @Inject constructor(
    private val jobDataNetwork: JobDataNetwork,
    private val vacanciesStorage: VacanciesStorage,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): VacanciesRepository {

    private val vacanciesBuffer = ArrayList<Vacancy>()
    private val offerBuffer = ArrayList<Offer>()

    override suspend fun getVacancies(): List<Vacancy> {
        val favoritesVacancies = vacanciesStorage.getFavorites()

        if (vacanciesBuffer.isEmpty()) {
            val vacanciesList = getJobData().vacancies.map { vacancy ->
                if (favoritesVacancies.contains(vacancy.id)) vacancy.toDomain(true)
                else vacancy.toDomain()
            }
            vacanciesBuffer.addAll(vacanciesList)
        }

        return vacanciesBuffer.map { vacancy ->
            if (favoritesVacancies.contains(vacancy.id)) vacancy.toDomain(true)
            else vacancy.toDomain()
        }
    }

    override suspend fun selectFavoriteVacancies(vacancyId: String) {
        vacanciesStorage.selectFavorites(vacancyId)
    }

    override suspend fun getFavoritesVacancies(): List<Vacancy> {
        val favoritesVacancies = vacanciesStorage.getFavorites()
        return if (vacanciesBuffer.isEmpty()) {
            getJobData().vacancies.filter { vacancy ->
                favoritesVacancies.contains(vacancy.id)
            }.map { selectedVacancy ->
                selectedVacancy.toDomain(true)
            }
        } else vacanciesBuffer.filter { vacancy ->
            favoritesVacancies.contains(vacancy.id)
        }.map { selectedVacancy ->
            selectedVacancy.toDomain(true)
        }
    }

    override suspend fun getVacancy(vacancyId: String): Vacancy {
        val favoritesVacancies = vacanciesStorage.getFavorites()
        val vacancy = vacanciesBuffer.filter { it.id == vacancyId }.first()
        return vacancy.toDomain(favoritesVacancies.contains(vacancy.id))
    }

    override suspend fun getOffers(): List<Offer> {
        if (offerBuffer.isEmpty()) offerBuffer.addAll(getJobData().offers)
        return offerBuffer
    }

    private suspend fun getJobData(): JobData = withContext(dispatcher) {
        jobDataNetwork.getStats()
    }
}

