package com.example.jobsearch_test.entrance.presentatation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.jobsearch_test.core.utils.hasNoEmptyValues
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject


sealed class Entrance2Screen {
    data object EmptyFields : Entrance2Screen()
    data object FilledFields : Entrance2Screen()
    data object ConfirmCode : Entrance2Screen()
}

class Entrance2ViewModel(
    val email: String?
): ViewModel() {

    private var _code = MutableLiveData(arrayListOf("","","",""))
    private val code: LiveData<ArrayList<String>> = _code

    private val _state = MutableLiveData<Entrance2Screen>(Entrance2Screen.EmptyFields)
    val state: LiveData<Entrance2Screen> = _state

    fun fillingCodeField(codeNumber:Pair<Int,String>) {
        _code.value!!.set(index = codeNumber.first, element = codeNumber.second)
        if (code.value.hasNoEmptyValues()) _state.value = Entrance2Screen.FilledFields
        else _state.value = Entrance2Screen.EmptyFields
    }

    fun confirmBottomClicked() {
        if (code.value.hasNoEmptyValues()) _state.value = Entrance2Screen.ConfirmCode
    }
}

@AssistedFactory
interface EmailEntrance2ViewModelFactoryAssisted {
    fun  create(email: String?): Entrance2ViewModelFactory
}

class Entrance2ViewModelFactory @AssistedInject constructor(
    @Assisted private val email: String?
):  ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras,
    ): T {
        return Entrance2ViewModel(
            email = email,
        ) as T
    }
}