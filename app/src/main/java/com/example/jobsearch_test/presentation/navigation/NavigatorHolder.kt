package com.example.jobsearch_test.presentation.navigation

import android.content.Context
import androidx.fragment.app.FragmentManager

interface NavigatorHolder {
    fun manager(): FragmentManager
    fun context(): Context
}