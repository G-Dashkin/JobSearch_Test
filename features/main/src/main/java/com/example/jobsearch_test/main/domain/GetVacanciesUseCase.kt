package com.example.jobsearch_test.main.domain

import com.example.jobsearch_test.core.contracts.UseCaseWithoutParams
import com.example.jobsearch_test.data_api.repository.VacanciesRepository
import com.example.jobsearch_test.models.Vacancy
import javax.inject.Inject

class GetVacanciesUseCase @Inject constructor(
    private val repository: VacanciesRepository
):UseCaseWithoutParams<List<Vacancy>>{
    override suspend fun execute(): List<Vacancy> {
        return repository.getAllVacancies()
    }
}