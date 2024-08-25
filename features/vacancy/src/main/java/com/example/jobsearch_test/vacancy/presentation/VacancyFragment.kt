package com.example.jobsearch_test.vacancy.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.jobsearch_test.core.navigation.Router
import com.example.jobsearch_test.models.Vacancy
import com.example.jobsearch_test.vacancy.di.DaggerVacancyComponent
import com.example.jobsearch_test.vacancy.di.VacancyFeatureDepsProvider
import com.example.vacancy.R
import com.example.vacancy.databinding.FragmentVacancyBinding
import javax.inject.Inject

private const val VACANCY_ID_EXTRA_PARAM = "VACANCY_ID_EXTRA_PARAM"

class VacancyFragment: Fragment(R.layout.fragment_vacancy) {

    companion object {
        fun getInstance(vacancyId: String): VacancyFragment {
            return  VacancyFragment().apply {
                val bundle = Bundle()
                bundle.putString(VACANCY_ID_EXTRA_PARAM, vacancyId)
                arguments = bundle
            }
        }
    }

    private lateinit var binding: FragmentVacancyBinding

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var vmFactoryAssisted: VacancyViewModelFactoryAssisted

    private val vacancyViewModel by viewModels<VacancyViewModel> {
        vmFactoryAssisted.create(arguments?.getString(VACANCY_ID_EXTRA_PARAM))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val startComponent = DaggerVacancyComponent
            .builder()
            .addDeps(VacancyFeatureDepsProvider.deps)
            .build()
        startComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentVacancyBinding.bind(view)

        binding.arrowBack.setOnClickListener { vacancyViewModel.arrowBackClicked() }
        setState()
    }

    private fun setState() {
        vacancyViewModel.state.observe(viewLifecycleOwner) {
            when(it) {
                VacancyScreen.Loading -> {}
                is VacancyScreen.Loaded -> setVacancyData(vacancy = it.vacancy)
                VacancyScreen.Empty -> {}
                VacancyScreen.Error -> {}
                VacancyScreen.Back -> showBackFragment()
            }
        }
    }


    private fun setVacancyData(vacancy: Vacancy) {

        binding.title.text = vacancy.title
        binding.salary.text = if (vacancy.salary.short == null) vacancy.salary.full else vacancy.salary.short
        binding.experience.text = "Требуемый опыт ${vacancy.experience.previewText}"
        binding.schedules.text = vacancy.schedules.toString()

        binding.company.text = vacancy.company
        binding.companyAddress.text = "${vacancy.address.town}, ${vacancy.address.street}, ${vacancy.address.house}"
        binding.description.text = vacancy.description
        binding.responsibilities.text = vacancy.responsibilities
//        binding.questions.adapter = QuestionsListAdapter(requireContext(), vacancy.questions)
    }

    private fun showBackFragment(){
        router.back()
    }
}