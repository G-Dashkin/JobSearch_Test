package com.example.jobsearch_test.presentation.navigation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.jobsearch_test.R
import com.example.jobsearch_test.databinding.FragmentBlankScreenBinding

class BlankScreenFragment: Fragment(R.layout.fragment_blank_screen) {

    private lateinit var binding: FragmentBlankScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBlankScreenBinding.bind(view)

    }
}