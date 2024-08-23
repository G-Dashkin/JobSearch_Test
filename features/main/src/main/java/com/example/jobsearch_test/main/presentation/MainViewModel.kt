package com.example.jobsearch_test.main.presentation

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.jobsearch_test.main.domain.GetVacanciesUseCase
import com.example.jobsearch_test.models.Vacancy
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MainScreen {
//    data class Loaded(val repos: List<Repo>) : State
data class Loaded(val repos: List<Vacancy>) : MainScreen()
//data object Loaded : MainScreen()
}

class MainViewModel(
    private val getVacanciesUseCase: GetVacanciesUseCase
): ViewModel() {

    private val _state = MutableLiveData<MainScreen>()
    val state: LiveData<MainScreen> = _state

    init {
        viewModelScope.launch {
            val vacancies = getVacanciesUseCase.execute()
            Log.d("MyLog", vacancies.toString())
        }

    }

}

class MainViewModelFactory @Inject constructor(
    private val getVacanciesUseCase: GetVacanciesUseCase
):  ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras,
    ): T {
        return MainViewModel(
            getVacanciesUseCase = getVacanciesUseCase
        ) as T
    }
}