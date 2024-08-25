package com.example.jobsearch_test.entrance.presentatation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.entrance.R
import com.example.entrance.databinding.FragmentEntrance1Binding
import com.example.entrance.databinding.FragmentEntrance2Binding
import com.example.jobsearch_test.api.EntranceFeatureApi
import com.example.jobsearch_test.api.HomeFeatureApi
import com.example.jobsearch_test.core.navigation.Router
import com.example.jobsearch_test.entrance.di.DaggerEntranceComponent
import com.example.jobsearch_test.entrance.di.EntranceFeatureDepsProvider
import javax.inject.Inject

private const val EMAIL_EXTRA_PARAM = "EMAIL_EXTRA_PARAM"

class Entrance2Fragment: Fragment(R.layout.fragment_entrance_2) {

    companion object {
        fun getInstance(email:String): Entrance2Fragment {
            return Entrance2Fragment().apply {
                val bundle = Bundle()
                bundle.putString(EMAIL_EXTRA_PARAM, email)
                arguments = bundle
            }
        }
    }

    private lateinit var binding: FragmentEntrance2Binding

    @Inject
    lateinit var vmFactoryAssisted: EmailEntrance2ViewModelFactoryAssisted

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var homeFeatureApi: HomeFeatureApi

    private val entrance2ViewModel by viewModels<Entrance2ViewModel> {
        vmFactoryAssisted.create(arguments?.getString(EMAIL_EXTRA_PARAM))
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
        binding = FragmentEntrance2Binding.bind(view)

        binding.confirmBottom.setOnClickListener { entrance2ViewModel.confirmBottomClicked() }

        setState()
        textInputListener()
        setEmail()
    }

    private fun setState() {
        entrance2ViewModel.state.observe(viewLifecycleOwner) {
            when(it) {
                Entrance2Screen.EmptyFields -> emptyFields()
                Entrance2Screen.FilledFields -> filledFields()
                Entrance2Screen.ConfirmCode -> showMainScreen()
            }
        }
    }

    private fun emptyFields() {
        binding.confirmBottom.isEnabled = false
    }


    private fun filledFields() {
        binding.confirmBottom.isEnabled = true
    }

    private fun setEmail() {
        binding.emailText.text = entrance2ViewModel.email
    }

    private fun textInputListener() {
        binding.apply {
            cell1.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    entrance2ViewModel.fillingCodeField(codeNumber = Pair(0, s.toString()))
                    if (s?.length == 1) {
                        cell2.requestFocus()
                        cell2.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0)
                    } else {
                        cell1.setCompoundDrawablesRelativeWithIntrinsicBounds(com.example.ui.R.drawable.ic_empty_star,0,0,0)
                    }
                }
            })
            cell1.setOnTouchListener { v, event ->
                when(event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        cell1.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0)
                    }
                }
                false
            }
            cell2.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    entrance2ViewModel.fillingCodeField(codeNumber = Pair(1, s.toString()))
                    if (s?.length == 1) {
                        cell3.requestFocus()
                        cell3.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0)
                    } else {
                        cell2.setCompoundDrawablesRelativeWithIntrinsicBounds(com.example.ui.R.drawable.ic_empty_star,0,0,0)
                    }
                }
            })
            cell2.setOnTouchListener { v, event ->
                when(event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        cell2.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0)
                    }
                }
                false
            }
            cell3.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    entrance2ViewModel.fillingCodeField(codeNumber = Pair(2, s.toString()))
                    if (s?.length == 1) {
                        cell4.requestFocus()
                        cell4.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0)
                    } else {
                        cell3.setCompoundDrawablesRelativeWithIntrinsicBounds(com.example.ui.R.drawable.ic_empty_star,0,0,0)
                    }
                }
            })
            cell3.setOnTouchListener { v, event ->
                when(event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        cell3.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0)
                    }
                }
                false
            }
            cell4.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    entrance2ViewModel.fillingCodeField(codeNumber = Pair(3, s.toString()))
                    if (s?.length == 0) {
                        cell4.setCompoundDrawablesRelativeWithIntrinsicBounds(com.example.ui.R.drawable.ic_empty_star,0,0,0)
                    }
                }
            })
            cell4.setOnTouchListener { v, event ->
                when(event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        cell4.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0)
                    }
                }
                false
            }
        }
    }

    private fun showMainScreen(){
        router.navigateTo(fragment = homeFeatureApi.open())
    }
}