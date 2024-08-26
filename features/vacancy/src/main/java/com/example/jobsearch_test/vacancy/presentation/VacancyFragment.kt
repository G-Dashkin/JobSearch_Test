package com.example.jobsearch_test.vacancy.presentation

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.jobsearch_test.api.HomeFeatureApi
import com.example.jobsearch_test.core.contracts.CALL_BOTTOM_MENU_LISTENER
import com.example.jobsearch_test.core.navigation.Router
import com.example.jobsearch_test.vacancy.di.DaggerVacancyComponent
import com.example.jobsearch_test.vacancy.di.VacancyFeatureDepsProvider
import com.example.vacancy.R
import com.example.vacancy.databinding.CustomDialogBinding
import com.example.vacancy.databinding.FragmentVacancyBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale
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
    private lateinit var bindingCustomDialog: CustomDialogBinding

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var vmFactoryAssisted: VacancyViewModelFactoryAssisted

    @Inject
    lateinit var homeFeatureApi: HomeFeatureApi

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
        setVacancyData()
    }

    private fun setState() {
        vacancyViewModel.state.observe(viewLifecycleOwner) {
            when(it) {
                VacancyScreen.Loading -> showLoadingIndicator()
                is VacancyScreen.Loaded -> setVacancyData()
                VacancyScreen.Back -> backToHomeFragment()
                is VacancyScreen.Apply -> showApplyDialog(vacancyTitle = it.vacancyTitle)
                is VacancyScreen.ChangeIsFavoriteIcon -> changeIsFavoriteIcon(it.isFavorite)
            }
        }
    }


    private fun setVacancyData() {
        vacancyViewModel.vacancy.observe(viewLifecycleOwner) { vacancy ->
            binding.circularProgressIndicator.visibility = View.GONE
            binding.scrollView.visibility = View.VISIBLE
            binding.title.text = vacancy.title
            binding.salary.text = if (vacancy.salary.short == null) vacancy.salary.full else vacancy.salary.short
            binding.experience.text = "${resources.getString(com.example.ui.R.string.requirementExperience)} ${vacancy.experience.previewText}"
            binding.schedules.text = vacancy.schedules.toString()
                .replace("[","")
                .replace("]","")
                .replaceFirstChar{
                    if (it. isLowerCase()) it. titlecase(Locale.getDefault()) else it. toString()
                }

            binding.appliedNumber.text = "${vacancy.appliedNumber} ${resources.getString(com.example.ui.R.string.appliedPeople)}"
            binding.lookingNumber.text = "${vacancy.lookingNumber} ${resources.getString(com.example.ui.R.string.appliedPeople)}"

            binding.favoriteIcon.setImageResource(
                if (vacancy.isFavorite) com.example.ui.R.drawable.ic_favorites_select
                else com.example.ui.R.drawable.ic_favorite
            )

            binding.company.text = vacancy.company
            binding.companyAddress.text = "${vacancy.address.town}, ${vacancy.address.street}, ${vacancy.address.house}"
            binding.description.text = vacancy.description
            binding.responsibilities.text = vacancy.responsibilities
            binding.favoriteIcon.setOnClickListener { vacancyViewModel.favoriteIconClicked(vacancy.id) }
            binding.applyButton.setOnClickListener { vacancyViewModel.applyButtonClicked(vacancy.title) }
            binding.questionsList.adapter = QuestionsListAdapter(requireContext(), vacancy.questions)
            setListViewSettings()
        }
    }

    private fun showApplyDialog(vacancyTitle: String) {

        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)

        val inflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        bindingCustomDialog = CustomDialogBinding.inflate(inflater)
        dialog.setContentView(bindingCustomDialog.root)
        dialog.show()

        bindingCustomDialog.title.text = vacancyTitle
        bindingCustomDialog.addCoverLetter.setOnClickListener {
            bindingCustomDialog.coverLetterField.visibility = View.VISIBLE
            bindingCustomDialog.addCoverLetter.visibility = View.GONE
        }
        bindingCustomDialog.applyButton.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun changeIsFavoriteIcon(isFavorite: Boolean) {
        if (isFavorite) binding.favoriteIcon.setImageResource(com.example.ui.R.drawable.ic_favorites_select)
        else binding.favoriteIcon.setImageResource(com.example.ui.R.drawable.ic_favorite)
        updateBottomNavBar()
    }
    private fun setListViewSettings(){
        var totalHeight = 0
        for (i in 0 until binding.questionsList.adapter.count) {
            val mView = binding.questionsList.adapter.getView(i, null, binding.questionsList)
            mView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
            totalHeight += mView.measuredHeight
        }
        val params = binding.questionsList.layoutParams
        params.height = totalHeight + (binding.questionsList.dividerHeight * (binding.questionsList.adapter.count - 1))
        binding.questionsList.layoutParams = params
    }

    private fun updateBottomNavBar() {
        lifecycleScope.launch {
            delay(100)
            parentFragmentManager.setFragmentResult(CALL_BOTTOM_MENU_LISTENER, bundleOf())
        }
    }

    private fun backToHomeFragment(){
        router.navigateTo(homeFeatureApi.open())
    }


    private fun showLoadingIndicator() {
        binding.circularProgressIndicator.visibility = View.VISIBLE
    }
}