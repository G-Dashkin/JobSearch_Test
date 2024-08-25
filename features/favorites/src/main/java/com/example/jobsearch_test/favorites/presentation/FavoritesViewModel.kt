package com.example.jobsearch_test.favorites.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.jobsearch_test.favorites.domain.usecases.GetFavoritesVacanciesUseCase
import com.example.jobsearch_test.favorites.domain.usecases.SelectFavoriteVacanciesUseCase
import com.example.jobsearch_test.models.Vacancy
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class FavoritesScreenState {
    data object Loading : FavoritesScreenState()
    data class Loaded(val vacancies: List<Vacancy>) : FavoritesScreenState()
}

class FavoritesViewModel(
    private val getFavoritesVacanciesUseCase: GetFavoritesVacanciesUseCase,
    private val selectFavoriteVacanciesUseCase: SelectFavoriteVacanciesUseCase
): ViewModel() {

    private val _state = MutableLiveData<FavoritesScreenState>(FavoritesScreenState.Loading)
    val state: LiveData<FavoritesScreenState> = _state

    init {
        loadVacanciesList()
    }

    fun selectFavoriteIcon(vacancyId: String){
        viewModelScope.launch {
            selectFavoriteVacanciesUseCase.execute(vacancyId)
            loadVacanciesList()
        }
    }

    private fun loadVacanciesList() {
        viewModelScope.launch {
            _state.value = FavoritesScreenState.Loaded(
                vacancies = getFavoritesVacanciesUseCase.execute()
            )
        }
    }

}

class FavoritesViewModelFactory @Inject constructor(
    private val getFavoritesVacanciesUseCase: GetFavoritesVacanciesUseCase,
    private val selectFavoriteVacanciesUseCase: SelectFavoriteVacanciesUseCase
):  ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras,
    ): T {
        return FavoritesViewModel(
            getFavoritesVacanciesUseCase = getFavoritesVacanciesUseCase,
            selectFavoriteVacanciesUseCase = selectFavoriteVacanciesUseCase
        ) as T
    }
}