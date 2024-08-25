package com.example.jobsearch_test.domain.usecases

import com.example.jobsearch_test.core.contracts.UseCaseWithoutParams
import com.example.jobsearch_test.data_api.repository.VacanciesRepository
import com.example.jobsearch_test.models.Vacancy
import javax.inject.Inject

class GetFavoritesCountUseCase @Inject constructor(
    private val repository: VacanciesRepository
): UseCaseWithoutParams<Int> {
    override suspend fun execute(): Int {
        return repository.getFavoritesVacancies().size
    }
}