package com.example.jobsearch_test.vacancy.domain.usecases

import com.example.jobsearch_test.core.contracts.UseCaseWithParams
import com.example.jobsearch_test.core.contracts.UseCaseWithoutParams
import com.example.jobsearch_test.data_api.repository.VacanciesRepository
import com.example.jobsearch_test.models.Offer
import com.example.jobsearch_test.models.Vacancy
import javax.inject.Inject


class GetVacancyUseCase @Inject constructor(
    private val repository: VacanciesRepository
): UseCaseWithParams<Vacancy, String> {
    override suspend fun execute(vacancyId: String): Vacancy {
        return repository.getVacancy(vacancyId = vacancyId
        )
    }
}