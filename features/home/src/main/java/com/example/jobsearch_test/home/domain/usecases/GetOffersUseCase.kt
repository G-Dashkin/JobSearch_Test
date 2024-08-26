package com.example.jobsearch_test.home.domain.usecases

import com.example.jobsearch_test.core.contracts.UseCaseWithoutParams
import com.example.jobsearch_test.data_api.repository.VacanciesRepository
import com.example.jobsearch_test.models.Offer
import com.example.jobsearch_test.models.Vacancy
import javax.inject.Inject

class GetOffersUseCase @Inject constructor(
    private val repository: VacanciesRepository
): UseCaseWithoutParams<List<Offer>> {
    override suspend fun execute(): List<Offer> {
        return repository.getOffers()
    }
}