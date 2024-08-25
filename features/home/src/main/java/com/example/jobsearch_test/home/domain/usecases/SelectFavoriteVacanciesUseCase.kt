package com.example.jobsearch_test.home.domain.usecases

import com.example.jobsearch_test.core.contracts.UseCaseWithParams
import com.example.jobsearch_test.data_api.repository.VacanciesRepository
import javax.inject.Inject

class SelectFavoriteVacanciesUseCase @Inject constructor(
    private val repository: VacanciesRepository
): UseCaseWithParams<Unit, String> {
    override suspend fun execute(favoriteId: String) {
        repository.selectFavoriteVacancies(vacancyId = favoriteId)
    }
}