package com.example.jobsearch_test.presentation.navigation

import android.content.Context
import android.os.Bundle
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
import com.example.jobsearch_test.core.navigation.Router
import com.example.jobsearch_test.databinding.FragmentNavigatorBinding
import com.example.jobsearch_test.di.DaggerProvider
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentNavigatorBinding.inflate(layoutInflater)
        DaggerProvider.appComponent.inject(this)
        navigatorLifecycle.onCreate(this)
        router.navigateTo(fragment = entranceFeatureApi.openEntrance1())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()


        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (childFragmentManager.backStackEntryCount > 0) {
                    childFragmentManager.popBackStack()
                } else {
                    requireActivity().finish()
                }
            }
        }
        )
    }

    private fun setToolbar(){
        binding.toolbar.tooltipText = "Вход в личный кабинет"
//        lifecycleScope.launch {
//            binding.materialToolbar.visibility = View.GONE
//            binding.materialToolbar.visibility = View.VISIBLE
////            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
////            var currentFragmentName = childFragmentManager.getFragmentName()
//            if (currentFragmentName !in START_SCREENS){
//                binding.materialToolbar.visibility = View.VISIBLE
//                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
//            } else {
//                while (currentFragmentName in START_SCREENS) {
//                    currentFragmentName = childFragmentManager.getFragmentName()
//                    if (currentFragmentName !in START_SCREENS) {
//                        binding.materialToolbar.visibility = View.VISIBLE
//                        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
//                    }
//                    delay(100)
//                }
//            }
//        }
    }

    override fun onDestroy() {
        navigatorLifecycle.onDestroy()
        super.onDestroy()
    }

    override fun manager(): FragmentManager = childFragmentManager

    override fun context(): Context = requireActivity()
}