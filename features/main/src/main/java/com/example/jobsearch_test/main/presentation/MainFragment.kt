package com.example.jobsearch_test.main.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.jobsearch_test.main.di.MainFeatureDepsProvider
import com.example.jobsearch_test.main.di.DaggerMainComponent
import com.example.main.R
import com.example.main.databinding.FragmentMainBinding
import javax.inject.Inject

class MainFragment: Fragment(R.layout.fragment_main) {

    companion object {
        fun getInstance(): MainFragment = MainFragment()
    }

    private lateinit var binding: FragmentMainBinding

    @Inject
    lateinit var vmFactory: MainViewModelFactory

    private val mainViewModel by viewModels<MainViewModel> {
        vmFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val startComponent = DaggerMainComponent
            .builder()
            .addDeps(MainFeatureDepsProvider.deps)
            .build()
        startComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        setState()
    }

    private fun setState() {
        mainViewModel.state.observe(viewLifecycleOwner) {
            when(it) {
                is MainScreen.Loaded -> {}
            }
        }
    }
}