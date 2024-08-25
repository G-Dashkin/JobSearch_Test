package com.example.jobsearch_test.presentation.navigation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.example.jobsearch_test.R
import com.example.jobsearch_test.api.EntranceFeatureApi
import com.example.jobsearch_test.api.FavoritesFeatureApi
import com.example.jobsearch_test.api.HomeFeatureApi
import com.example.jobsearch_test.core.contracts.CALL_BOTTOM_MENU_LISTENER
import com.example.jobsearch_test.core.navigation.Router
import com.example.jobsearch_test.databinding.FragmentNavigatorBinding
import com.example.jobsearch_test.di.DaggerProvider
import com.example.jobsearch_test.domain.usecases.GetFavoritesCountUseCase
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

    @Inject
    lateinit var getFavoritesCountUseCase: GetFavoritesCountUseCase

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
        setBackPressed()
        setBottomNavigation()
        updateBottomNavigation()
    }

    override fun onDestroy() {
        navigatorLifecycle.onDestroy()
        super.onDestroy()
    }

    override fun manager(): FragmentManager = childFragmentManager

    override fun context(): Context = requireActivity()

    private fun setBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (childFragmentManager.backStackEntryCount > 0) childFragmentManager.popBackStack()
                else requireActivity().finish()
            }
        })
    }

    private fun setBottomNavigation() {
        lifecycleScope.launch {
            binding.bottomNavigation.getOrCreateBadge(R.id.nav_favorites).apply {
                this.backgroundColor = ContextCompat.getColor(requireContext(), com.example.ui.R.color.red)
                this.badgeTextColor = ContextCompat.getColor(requireContext(), com.example.ui.R.color.white)
                this.maxCharacterCount = 3
                this.verticalOffset = 25
                this.horizontalOffset = 15

                setBadgeCount(getFavoritesCountUseCase.execute())
            }
            binding.bottomNavigation.setOnItemSelectedListener { item->
                when(item.itemId) {
                    R.id.nav_search -> router.navigateTo(fragment = homeFeatureApi.open())
                    R.id.nav_favorites -> router.navigateTo(fragment = favoritesFeatureApi.open())
                    R.id.nav_responses -> showBlankScreen()
                    R.id.nav_messages -> showBlankScreen()
                    R.id.nav_profile -> showBlankScreen()
                }
                true
            }
        }
    }

    private fun updateBottomNavigation() {
        childFragmentManager.setFragmentResultListener(CALL_BOTTOM_MENU_LISTENER, viewLifecycleOwner){ _, _ ->
            setBottomNavigation()
        }
    }

    private fun setBadgeCount(count: Int) {
        val menuItemId = R.id.nav_favorites
        val badge = binding.bottomNavigation.getBadge(menuItemId)
        if (badge != null) {
            if (count > 0) {
                badge.isVisible = true
                badge.number = count
            } else {
                badge.isVisible = false
            }
        }
    }

    private fun showBlankScreen() {
        childFragmentManager.commit { replace(R.id.main_fragment_container, BlankScreenFragment()) }
    }
}