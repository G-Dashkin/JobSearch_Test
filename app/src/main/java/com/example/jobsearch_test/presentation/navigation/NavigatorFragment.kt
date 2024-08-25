package com.example.jobsearch_test.presentation.navigation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.example.jobsearch_test.R
import com.example.jobsearch_test.api.EntranceFeatureApi
import com.example.jobsearch_test.api.FavoritesFeatureApi
import com.example.jobsearch_test.api.HomeFeatureApi
import com.example.jobsearch_test.core.navigation.Router
import com.example.jobsearch_test.databinding.FragmentNavigatorBinding
import com.example.jobsearch_test.di.DaggerProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.internal.NavigationMenu
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class NavigatorFragment : Fragment(R.layout.fragment_navigator), NavigatorHolder {

    private lateinit var binding: FragmentNavigatorBinding

    @Inject
    lateinit var navigatorLifecycle: NavigatorLifecycle

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var entranceFeatureApi: EntranceFeatureApi

    @Inject
    lateinit var homeFeatureApi: HomeFeatureApi

    @Inject
    lateinit var favoritesFeatureApi: FavoritesFeatureApi


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentNavigatorBinding.inflate(layoutInflater)
        DaggerProvider.appComponent.inject(this)
        navigatorLifecycle.onCreate(this)
//        router.navigateTo(fragment = entranceFeatureApi.openEntrance1())
        router.navigateTo(fragment = homeFeatureApi.open())

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNavigatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (childFragmentManager.backStackEntryCount > 0) childFragmentManager.popBackStack()
                else requireActivity().finish()
            }
        })

        binding.bottomNavigation.setOnItemSelectedListener { item->
            Log.d("MyLog", "dddd")
            when(item.itemId) {
                R.id.nav_search -> router.navigateTo(fragment = homeFeatureApi.open())
                R.id.nav_favorites -> router.navigateTo(fragment = favoritesFeatureApi.open())
                R.id.nav_responses -> {}
                R.id.nav_messages -> {}
                R.id.nav_profile -> {}
            }
            true
        }


    }

    override fun onDestroy() {
        navigatorLifecycle.onDestroy()
        super.onDestroy()
    }

    override fun manager(): FragmentManager = childFragmentManager

    override fun context(): Context = requireActivity()
}