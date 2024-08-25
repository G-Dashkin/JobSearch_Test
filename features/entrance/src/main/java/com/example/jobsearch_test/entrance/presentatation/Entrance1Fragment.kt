package com.example.jobsearch_test.entrance.presentatation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.entrance.R
import com.example.entrance.databinding.FragmentEntrance1Binding
import com.example.jobsearch_test.api.EntranceFeatureApi
import com.example.jobsearch_test.core.navigation.Router
import com.example.jobsearch_test.entrance.di.DaggerEntranceComponent
import com.example.jobsearch_test.entrance.di.EntranceFeatureDepsProvider
import javax.inject.Inject

class Entrance1Fragment: Fragment(R.layout.fragment_entrance_1)  {

    companion object {
        fun getInstance(): Entrance1Fragment = Entrance1Fragment()
    }

    private lateinit var binding: FragmentEntrance1Binding

    @Inject
    lateinit var vmFactory: Entrance1ViewModelFactory

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var entranceFeatureApi: EntranceFeatureApi

    private val entrance1ViewModel by viewModels<Entrance1ViewModel> {
        vmFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val startComponent = DaggerEntranceComponent
            .builder()
            .addDeps(EntranceFeatureDepsProvider.deps)
            .build()
        startComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEntrance1Binding.bind(view)

        binding.continueBottom.setOnClickListener { entrance1ViewModel.continueButtonClicked() }


        setState()
        textInputListener()
    }

    private fun textInputListener() {
        binding.emailTextField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(string: CharSequence?, start: Int, before: Int, count: Int) {
                entrance1ViewModel.emailString(email = string.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        }
        )
    }

    private fun setState() {
        entrance1ViewModel.state.observe(viewLifecycleOwner) {
            when(it) {
                is Entrance1Screen.EmptyField -> emptyField()
                is Entrance1Screen.FillingField -> fillingField()
                is Entrance1Screen.EmailNotCorrect -> emailNotCorrect()
                is Entrance1Screen.CheckEmailCode -> showEntrance2Screen(email = it.email)
            }
        }
    }


    private fun emptyField() {
        binding.emailTextField.text?.clear()
        binding.continueBottom.isEnabled = false
        binding.errorEmailMessage.visibility = View.GONE
        binding.emailTextField.setBackgroundResource(com.example.ui.R.drawable.custom_form_border)
        binding.emailTextField.setCompoundDrawablesRelativeWithIntrinsicBounds(com.example.ui.R.drawable.ic_responses, 0, 0, 0)
    }

    private fun fillingField() {
        binding.continueBottom.isEnabled = true
        binding.errorEmailMessage.visibility = View.GONE
        binding.emailTextField.setBackgroundResource(com.example.ui.R.drawable.custom_form_border)
        binding.emailTextField.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, com.example.ui.R.drawable.ic_close, 0)
        binding.emailTextField.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    try {
                        val iconWidth = binding.emailTextField.getCompoundDrawables()[2].bounds.width()
                        val clickSpace = binding.emailTextField.right - event.rawX.toInt()
                        if (clickSpace <= iconWidth) entrance1ViewModel.clearFieldIconClicked()
                    } catch (e: Exception) {}
                    true
                }
                else -> false
            }
        }
    }

    private fun emailNotCorrect() {
        binding.emailTextField.setBackgroundResource(com.example.ui.R.drawable.custom_form_border_error)
        binding.errorEmailMessage.visibility = View.VISIBLE
    }

    private fun showEntrance2Screen(email: String) {
        router.navigateTo(
            fragment = entranceFeatureApi.openEntrance2(email),
            addToBackStack = true
        )
    }
}



