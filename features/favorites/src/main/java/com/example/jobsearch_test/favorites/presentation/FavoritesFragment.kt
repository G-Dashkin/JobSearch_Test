package com.example.jobsearch_test.favorites.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.favorites.R
import com.example.favorites.databinding.FragmentFavoritesBinding
import com.example.jobsearch_test.core.contracts.CALL_BOTTOM_MENU_LISTENER
import com.example.jobsearch_test.core.navigation.Router
import com.example.jobsearch_test.core.utils.countNoun
import com.example.jobsearch_test.favorites.di.DaggerFavoritesComponent
import com.example.jobsearch_test.favorites.di.FavoritesFeatureDepsProvider
import com.example.jobsearch_test.models.Vacancy
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoritesFragment: Fragment(R.layout.fragment_favorites) {

    companion object {
        fun getInstance(): FavoritesFragment = FavoritesFragment()
    }

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var vacanciesAdapter: VacanciesAdapter

    @Inject
    lateinit var vmFactory: FavoritesViewModelFactory

    private val favoritesViewModel by viewModels<FavoritesViewModel> {
        vmFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val startComponent = DaggerFavoritesComponent
            .builder()
            .addDeps(FavoritesFeatureDepsProvider.deps)
            .build()
        startComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoritesBinding.bind(view)

        setState()
    }


    private fun setState() {
        favoritesViewModel.state.observe(viewLifecycleOwner) {
            when(it) {
                FavoritesScreenState.Loading -> showMainLoadingIndicator()
                is FavoritesScreenState.Loaded -> showVacanciesData(it.vacancies)
            }
        }
    }

    private fun setVacanciesAdapter(vacanciesList: List<Vacancy>) {
        vacanciesAdapter = VacanciesAdapter(
            itemFavoriteClick = { itemFavorite ->
                favoritesViewModel.selectFavoriteIcon(vacancyId = itemFavorite)
                updateBottomNavBar()
            },
            context = requireContext()
        )
        binding.favoritesVacanciesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.favoritesVacanciesRecyclerView.adapter = vacanciesAdapter
        favoritesViewModel.state.observe(viewLifecycleOwner) {
            vacanciesAdapter.submitList(vacanciesList)
        }
    }


    private fun showVacanciesData(vacanciesList: List<Vacancy>){
        binding.circularProgressIndicator.visibility = View.GONE
        binding.favoritesText.visibility = View.VISIBLE
        binding.vacanciesCount.visibility = View.VISIBLE
        binding.favoritesVacanciesRecyclerView.visibility = View.VISIBLE
        binding.vacanciesCount.text = "${vacanciesList.size} ${
            resources.getString(countNoun(vacanciesList.size))
        }"
        setVacanciesAdapter(vacanciesList = vacanciesList)
    }

    private fun showMainLoadingIndicator() {
        binding.circularProgressIndicator.visibility = View.VISIBLE
        binding.favoritesText.visibility = View.GONE
        binding.vacanciesCount.visibility = View.GONE
        binding.favoritesVacanciesRecyclerView.visibility = View.GONE
    }

    private fun updateBottomNavBar() {
        // вот это убрать...
        // вот такой рещение
        // можно засунуть в одну функцию
//                    while (projectsViewModel.projectsList.value?.find { it.name == projectName } == null){
//                        delay(100)
//                    }
        lifecycleScope.launch {
            delay(100)
            parentFragmentManager.setFragmentResult(CALL_BOTTOM_MENU_LISTENER, bundleOf())
        }
    }
}