package com.example.jobsearch_test.presentation.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.jobsearch_test.R
import com.example.jobsearch_test.core.navigation.Router

class RouterImpl: Router, NavigatorLifecycle {

    private var navigatorHolder: NavigatorHolder? = null

    override fun onCreate(holder: NavigatorHolder) {
        navigatorHolder = holder
    }

    override fun onDestroy() {
        navigatorHolder = null
    }

    override fun navigateTo(fragment: Fragment, addToBackStack: Boolean) {
        val manager = navigatorHolder?.manager() ?: throw IllegalArgumentException("NavigationHolder is null")
        when {
            addToBackStack -> manager.commit {
                addToBackStack(null)
                replace(R.id.main_fragment_container, fragment)
            }
            else -> manager.commit {
                replace(R.id.main_fragment_container, fragment)
            }
        }
    }

    override fun back() {
        val manager = navigatorHolder?.manager() ?: throw IllegalArgumentException("NavigationHolder is null")
        manager.popBackStack()
    }
}