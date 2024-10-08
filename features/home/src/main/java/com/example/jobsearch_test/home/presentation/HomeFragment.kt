package com.example.jobsearch_test.home.presentation

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.home.R
import com.example.home.databinding.FragmentHomeBinding
import com.example.jobsearch_test.api.VacancyFeatureApi
import com.example.jobsearch_test.core.contracts.CALL_BOTTOM_MENU_LISTENER
import com.example.jobsearch_test.core.navigation.Router
import com.example.jobsearch_test.home.di.DaggerHomeComponent
import com.example.jobsearch_test.home.di.HomeFeatureDepsProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeFragment: Fragment(R.layout.fragment_home) {

    companion object {
        fun getInstance(): HomeFragment = HomeFragment()
    }

    private lateinit var offersAdapter: OffersAdapter
    private lateinit var vacanciesAdapter: VacanciesAdapter
    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var vmFactory: HomeViewModelFactory

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var vacancyFeatureApi: VacancyFeatureApi

    private val homeViewModel by viewModels<HomeViewModel> {
        vmFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val startComponent = DaggerHomeComponent
            .builder()
            .addDeps(HomeFeatureDepsProvider.deps)
            .build()
        startComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        binding.moreVacanciesButton.setOnClickListener { homeViewModel.moreVacanciesButtonClicked() }

        setState()
        setOffersAdapter()
        setVacanciesAdapter()
    }

    private fun setOffersAdapter() {
        homeViewModel.offerList.observe(viewLifecycleOwner) {
            offersAdapter = OffersAdapter(it)
            binding.offersRecyclerView.setHasFixedSize(true)
            binding.offersRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            binding.offersRecyclerView.adapter = offersAdapter
        }
    }

    private fun setVacanciesAdapter() {
        vacanciesAdapter = VacanciesAdapter(
            itemVacancyClick = { itemVacancy ->
                homeViewModel.itemVacancyClicked(itemVacancy)
            },
            itemFavoriteClick = { itemFavorite ->
                homeViewModel.selectFavoriteIcon(vacancyId = itemFavorite)
                updateBottomNavBar()
            },
            context = requireContext()
        )
        binding.vacanciesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.vacanciesRecyclerView.adapter = vacanciesAdapter
        homeViewModel.vacancyList.observe(viewLifecycleOwner) {
            binding.moreVacanciesButton.text = "${resources.getString(com.example.ui.R.string.more)} " +
                    "${it.size.minus(3)} ${resources.getString(com.example.ui.R.string.vacancies)}"
            vacanciesAdapter.submitList(it.take(3))
        }
    }

    private fun setState() {
        homeViewModel.state.observe(viewLifecycleOwner) {
            when(it) {
                HomeScreenState.VacanciesLoading -> showMainLoadingIndicator()
                HomeScreenState.VacanciesLoaded -> showVacanciesData()
                HomeScreenState.OffersLoading -> showTopLoadingIndicator()
                HomeScreenState.OfferLoaded ->  showOffersData()
                HomeScreenState.LoadedAllVacancies -> showMoreVacancies()
                is HomeScreenState.VacancyDetails -> showVacancyDetails(vacancyId = it.vacancyId)
            }
        }
    }

    private fun showMainLoadingIndicator() {
        binding.circularMainProgressIndicator.visibility = View.VISIBLE
        binding.offersRecyclerView.visibility = View.GONE
        binding.moreVacanciesButton.visibility = View.GONE
        binding.vacanciesText.visibility = View.GONE

    }

    private fun showTopLoadingIndicator() {
        binding.circularTopProgressIndicator.visibility = View.VISIBLE
    }

    private fun showOffersData(){
        binding.circularTopProgressIndicator.visibility = View.GONE
        binding.offersRecyclerView.visibility = View.VISIBLE
    }

    private fun showVacanciesData(){
        binding.circularMainProgressIndicator.visibility = View.GONE
        binding.vacanciesText.visibility = View.VISIBLE
        binding.searchTextField.setCompoundDrawablesRelativeWithIntrinsicBounds(
            com.example.ui.R.drawable.ic_search, 0, 0, 0)
        binding.moreVacanciesButton.visibility = View.VISIBLE
        binding.vacanciesText.textSize = 20.toFloat()
        binding.sortBlock.visibility = View.GONE
        homeViewModel.vacancyList.observe(viewLifecycleOwner) {
            binding.moreVacanciesButton.text = "${resources.getString(com.example.ui.R.string.more)} " +
                                               "${it.size.minus(3)} " +
                                               "${resources.getString(com.example.ui.R.string.vacancies)}"
            binding.vacanciesText.text = "${resources.getString(com.example.ui.R.string.vacancies_for_you)}"
            vacanciesAdapter.submitList(it.take(3))
        }
    }

    private fun showMoreVacancies() {
        binding.moreVacanciesButton.visibility = View.GONE
        binding.offersRecyclerView.visibility = View.GONE
        binding.vacanciesText.textSize = 14.toFloat()
        binding.sortBlock.visibility = View.VISIBLE
        homeViewModel.vacancyList.observe(viewLifecycleOwner) {
            binding.vacanciesText.text = "${it.size} ${resources.getString(com.example.ui.R.string.vacancies_)}"
            vacanciesAdapter.submitList(it)
        }
        setArrowBottom()
    }


    private fun setArrowBottom() {
        binding.searchTextField.setCompoundDrawablesRelativeWithIntrinsicBounds(
            com.example.ui.R.drawable.ic_back_arrow,
            0,
            0,
            0
        )

        binding.searchTextField.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> {

                    try {
                        val iconWidth = binding.searchTextField.getCompoundDrawables()[0].bounds.width()*1.7
                        val clickSpace = binding.searchTextField.left + event.x.toInt()
                        if (clickSpace <= iconWidth) homeViewModel.backArrowClicked()
                    } catch (e: Exception) {}
                    true
                }
                else -> false
            }
        }
    }


    private fun showVacancyDetails(vacancyId: String) {
        router.navigateTo(
            fragment = vacancyFeatureApi.open(vacancyId = vacancyId),
            addToBackStack = true
        )
    }


    private fun updateBottomNavBar() {
        lifecycleScope.launch {
            delay(100)
            parentFragmentManager.setFragmentResult(CALL_BOTTOM_MENU_LISTENER, bundleOf())
        }
    }

}