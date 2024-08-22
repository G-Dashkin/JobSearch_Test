package com.example.jobsearch_test.entrance.presentatation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import javax.inject.Inject


sealed class Entrance1Screen {
    data object EmptyField : Entrance1Screen()
    data object FillingField : Entrance1Screen()
    data object EmailNotCorrect : Entrance1Screen()
    class CheckEmailCode(val email: String) : Entrance1Screen()
}

class Entrance1ViewModel: ViewModel() {

    private val _email = MutableLiveData<String>()
    private val email: LiveData<String> = _email

    private val _state = MutableLiveData<Entrance1Screen>(Entrance1Screen.EmptyField)
    val state: LiveData<Entrance1Screen> = _state

    fun continueButtonClicked() {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$".toRegex()
        if(emailRegex.matches(_email.value.toString())) {
            _state.value = Entrance1Screen.CheckEmailCode(_email.value.toString())
        } else _state.value = Entrance1Screen.EmailNotCorrect
    }

    fun emailString(email: String){
        _email.value = email
        if (_email.value.toString().isEmpty()) _state.value = Entrance1Screen.EmptyField
        else _state.value = Entrance1Screen.FillingField
    }

    fun clearField() {
        _state.value = Entrance1Screen.EmptyField
    }

}

class Entrance1ViewModelFactory @Inject constructor(
):  ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras,
    ): T {
        return Entrance1ViewModel(

        ) as T
    }
}