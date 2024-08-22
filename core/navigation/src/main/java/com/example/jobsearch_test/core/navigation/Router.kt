package com.example.jobsearch_test.core.navigation

import androidx.fragment.app.Fragment

interface Router {
    fun navigateTo(fragment: Fragment, addToBackStack: Boolean = false)
    fun back()
}