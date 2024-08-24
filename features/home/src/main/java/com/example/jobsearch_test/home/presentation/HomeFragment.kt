package com.example.jobsearch_test.home.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.home.R
import com.example.home.databinding.FragmentHomeBinding
import com.example.jobsearch_test.home.di.DaggerHomeComponent
import com.example.jobsearch_test.home.di.HomeFeatureDepsProvider
import com.example.jobsearch_test.models.JobData
import com.example.jobsearch_test.models.Offer
import com.example.jobsearch_test.models.Vacancy
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
    }

    private fun setState() {
        homeViewModel.state.observe(viewLifecycleOwner) {
            when(it) {
                HomeScreenState.Loading -> {}
                is HomeScreenState.Loaded -> {
                    binding.moreVacanciesButton.text = "Еще ${it.vacancies.size.minus(3)} вакансии"
                    setOffersAdapter(offersList = it.offers)
                    setVacanciesAdapter(vacanciesList = it.vacancies.take(3))
                }
                is HomeScreenState.LoadedAllVacancies -> showMoreVacancies(vacanciesList = it.vacancies)
                HomeScreenState.Empty -> {}
                HomeScreenState.Error -> {}
            }
        }
    }

    private fun setOffersAdapter(offersList: List<Offer>) {
        offersAdapter = OffersAdapter(offersList)
        binding.offersRecyclerView.setHasFixedSize(true)
        binding.offersRecyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.offersRecyclerView.adapter = offersAdapter
    }

    private fun setVacanciesAdapter(vacanciesList: List<Vacancy>) {
        vacanciesAdapter = VacanciesAdapter()
        binding.vacanciesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.vacanciesRecyclerView.adapter = vacanciesAdapter
        homeViewModel.state.observe(viewLifecycleOwner) {
            vacanciesAdapter.submitList(vacanciesList)
        }
    }

    private fun showMoreVacancies(vacanciesList: List<Vacancy>) {
        binding.moreVacanciesButton.visibility = View.GONE
        binding.offersRecyclerView.visibility = View.GONE
        binding.vacanciesText.text = "${vacanciesList.size} вакансий"
        binding.vacanciesText.textSize = 14.toFloat()
        binding.sortBlock.visibility = View.VISIBLE
        homeViewModel.state.observe(viewLifecycleOwner) {
            vacanciesAdapter.submitList(vacanciesList)
        }
    }

}