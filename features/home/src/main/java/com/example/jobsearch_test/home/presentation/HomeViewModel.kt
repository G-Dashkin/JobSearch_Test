package com.example.jobsearch_test.home.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.jobsearch_test.home.domain.usecases.GetOffersUseCase
import com.example.jobsearch_test.home.domain.usecases.GetVacanciesUseCase
import com.example.jobsearch_test.home.domain.usecases.SelectFavoriteVacanciesUseCase
import com.example.jobsearch_test.models.Offer
import com.example.jobsearch_test.models.Vacancy
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class HomeScreenState {
    data object VacanciesLoading : HomeScreenState()
    data object VacanciesLoaded : HomeScreenState()
    data object OffersLoading : HomeScreenState()
    data object OfferLoaded : HomeScreenState()
    data object LoadedAllVacancies : HomeScreenState()
    data class VacancyDetails(val vacancyId: String) : HomeScreenState()
}

class HomeViewModel(
    private val getOffersUseCase: GetOffersUseCase,
    private val getVacanciesUseCase: GetVacanciesUseCase,
    private val selectFavoriteVacanciesUseCase: SelectFavoriteVacanciesUseCase
): ViewModel() {

    private val _vacancyList = MutableLiveData<List<Vacancy>>()
    val vacancyList: LiveData<List<Vacancy>> = _vacancyList

    private val _offerList = MutableLiveData<List<Offer>>()
    val offerList: LiveData<List<Offer>> = _offerList

    private val _state = MutableLiveData<HomeScreenState>(HomeScreenState.VacanciesLoading)
    val state: LiveData<HomeScreenState> = _state

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _vacancyList.postValue(getVacanciesUseCase.execute())
            _state.value = HomeScreenState.VacanciesLoaded
            _state.value = HomeScreenState.OffersLoading
            _offerList.postValue(getOffersUseCase.execute())
            _state.value = HomeScreenState.OfferLoaded
        }
    }


    fun moreVacanciesButtonClicked() {
        _state.value = HomeScreenState.LoadedAllVacancies
    }

    fun itemVacancyClicked(vacancyId: String) {
        viewModelScope.launch {
            _state.value = HomeScreenState.VacancyDetails(vacancyId = vacancyId)
        }
    }

    fun selectFavoriteIcon(vacancyId: String) {
        viewModelScope.launch {
            selectFavoriteVacanciesUseCase.execute(vacancyId)
            _vacancyList.postValue(getVacanciesUseCase.execute())
            _offerList.postValue(getOffersUseCase.execute())
        }
    }

    fun backArrowClicked() {
        _state.value = HomeScreenState.VacanciesLoaded
    }

}

class HomeViewModelFactory @Inject constructor(
    private val getOffersUseCase: GetOffersUseCase,
    private val getVacanciesUseCase: GetVacanciesUseCase,
    private val selectFavoriteVacanciesUseCase: SelectFavoriteVacanciesUseCase
):  ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras,
    ): T {
        return HomeViewModel(
            getOffersUseCase = getOffersUseCase,
            getVacanciesUseCase = getVacanciesUseCase,
            selectFavoriteVacanciesUseCase = selectFavoriteVacanciesUseCase
        ) as T
    }
}