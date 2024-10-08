package com.example.jobsearch_test.vacancy.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.jobsearch_test.models.Vacancy
import com.example.jobsearch_test.vacancy.domain.usecases.GetVacancyUseCase
import com.example.jobsearch_test.vacancy.domain.usecases.SelectFavoriteVacanciesUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

sealed class VacancyScreen {
    data object Loading : VacancyScreen()
    data object Loaded : VacancyScreen()
    data class Apply(val vacancyTitle: String) : VacancyScreen()
    data class ChangeIsFavoriteIcon(val isFavorite: Boolean) : VacancyScreen()
    data object Back : VacancyScreen()
}

class VacancyViewModel(
    val vacancyId: String,
    private val getVacancyUseCase: GetVacancyUseCase,
    private val selectFavoriteVacanciesUseCase: SelectFavoriteVacanciesUseCase
): ViewModel() {

    private val _vacancy = MutableLiveData<Vacancy>()
    val vacancy: LiveData<Vacancy> = _vacancy

    private val _state = MutableLiveData<VacancyScreen>()
    val state: LiveData<VacancyScreen> = _state


    init {
        loadVacancy()
    }

    private fun loadVacancy() {
        viewModelScope.launch {
            _vacancy.postValue(getVacancyUseCase.execute(vacancyId))
            if (vacancy.value?.title.isNullOrEmpty()) _state.value = VacancyScreen.Loading
            else _state.value = VacancyScreen.Loaded
        }
    }

    fun applyButtonClicked(vacancyTitle: String) {
        viewModelScope.launch {
            _state.value = VacancyScreen.Apply(vacancyTitle)
        }
    }

    fun arrowBackClicked() {
        _state.value = VacancyScreen.Back
    }

    fun favoriteIconClicked(vacancyId: String) {
        viewModelScope.launch {
            selectFavoriteVacanciesUseCase.execute(vacancyId)
            val isFavorite = getVacancyUseCase.execute(vacancyId).isFavorite
            _state.value = VacancyScreen.ChangeIsFavoriteIcon(isFavorite)
        }
    }

}

@AssistedFactory
interface VacancyViewModelFactoryAssisted {
    fun  create(vacancyId: String?): VacancyViewModelFactory
}

class VacancyViewModelFactory @AssistedInject constructor(
    @Assisted private val vacancyId: String,
    private val getVacancyUseCase: GetVacancyUseCase,
    private val selectFavoriteVacanciesUseCase: SelectFavoriteVacanciesUseCase
):  ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras,
    ): T {
        return VacancyViewModel(
            vacancyId = vacancyId,
            getVacancyUseCase = getVacancyUseCase,
            selectFavoriteVacanciesUseCase = selectFavoriteVacanciesUseCase
        ) as T
    }
}